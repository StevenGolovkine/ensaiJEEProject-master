package com.undertow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.Author;
import business.RProgram;
import jpa.EntityManagerHelper;

/**
 * Class which manages the RInterpreter Servlet.
 * @author steven
 *
 */
@SuppressWarnings("restriction")
public class RInterpreter extends HttpServlet {

	private static final long serialVersionUID = -4788673335945449378L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");

		Author a = new Author();
		a = (Author) EntityManagerHelper.getEntityManager().createQuery("SELECT a FROM Author AS a WHERE a.name = '" + request.getParameter("author") + "'").getSingleResult();

		System.err.println(a);
		
		@SuppressWarnings("unchecked")
		List<RProgram> progs = EntityManagerHelper.getEntityManager().createQuery("SELECT r FROM RProgram as r WHERE r.author='" + a.getId() + "'").getResultList();
		
		boolean flag = false;
		for (RProgram r : progs){
			if(request.getParameter("name").equals(r.getName())){
				flag = true;
			}
		}
		
		PrintWriter out = response.getWriter();

		// Redirect standard output
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		ScriptEngineManager manager = new ScriptEngineManager();

		// create a Renjin engine:
		ScriptEngine engine = manager.getEngineByName("Renjin");

		// check if the engine has loaded correctly:
		if (engine == null) {
			throw new RuntimeException("Renjin Script Engine not found on the classpath.");
		}

		engine.getContext().setWriter(new PrintWriter(buffer));
		
		try {
			engine.eval(request.getParameter("program"));
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
		EntityManager em = EntityManagerHelper.getEntityManager();
		em.getTransaction().begin();
		
		RProgram p = new RProgram();
		
		if(flag){
			p = (RProgram) EntityManagerHelper.getEntityManager().createQuery("SELECT r FROM RProgram AS r WHERE r.name='" + request.getParameter("name") +"' AND r.author='" + a.getId() + "'").getSingleResult();
			p.setProgram(request.getParameter("program"));
			p.setResult(buffer.toString());
		} else {
			p = new RProgram();	
			p.setAuthor(a);
			p.setName(request.getParameter("name"));
			p.setProgram(request.getParameter("program"));
			p.setResult(buffer.toString());
		}
		EntityManagerHelper.getEntityManager().persist(p);
		
		em.getTransaction().commit();	

		out.println("<HTML>\n<BODY>\n" + buffer.toString().replaceAll("\n", "<BR>\n") +
				"<FORM Method=\"POST\" Action=\"/myapp/signin\">" +
				"<INPUT type=hidden name=\"author\" value=\"" + a.getName() + "\">" +
				"<INPUT type=hidden name=\"password\" value=\"" + a.getPassword() + "\">" +
				"<INPUT type=\"submit\" value=\"Back\">" +
				"</FORM>" +
				"</BODY></HTML>");
		
		out.close();
	}
}