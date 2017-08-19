package com.undertow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.Author;
import business.RProgram;
import jpa.EntityManagerHelper;

/**
 * Class which manages the LoadProgram servlet.
 * @author steven
 *
 */
public class LoadProgram extends HttpServlet {

	private static final long serialVersionUID = 1L;


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Author a = (Author) EntityManagerHelper.getEntityManager().createQuery("SELECT a FROM Author AS a WHERE a.name = '" + request.getParameter("author") + "'").getSingleResult();
		
		@SuppressWarnings("unchecked")
		List<RProgram> progs = EntityManagerHelper.getEntityManager().createQuery("SELECT e FROM RProgram AS e WHERE e.author =" + a.getId()).getResultList();
		
		PrintWriter out = response.getWriter();

		out.println("<HTML><BODY>");
		
		for (RProgram r : progs){
			out.println( "<a href=\"loadr/" + r.getId() + "\">" + r.getAuthor().getName() + " : " + r.getName() + "</a>" + "<BR>");
		}
		
		out.println("</BODY></HTML>");
		out.close();
	}
	
}