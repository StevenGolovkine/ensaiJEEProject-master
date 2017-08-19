package com.undertow;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import business.Author;
import jpa.EntityManagerHelper;

/**
 * Class which manages the SignUp Servlet
 * @author steven
 *
 */
public class SignUp extends HttpServlet {

	private static final long serialVersionUID = -3861952816100762681L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		EntityTransaction t = EntityManagerHelper.getEntityManager().getTransaction();
		t.begin();
		Author a = new Author();

		a.setName(request.getParameter("author"));
		a.setPassword(request.getParameter("password"));

		PrintWriter out = response.getWriter();

		@SuppressWarnings("unchecked")
		List<Author> authors = EntityManagerHelper.getEntityManager().createQuery("SELECT a FROM Author AS a").getResultList();

		int flag = 0;
		for (Author author : authors){
			if (author.getName().equals(a.getName())){
				flag = 1;
			}
		}

		if (flag == 1){
			out.println("<HTML>" +
					"<HEAD>" +
					"<title>Error</title>" +
					"</HEAD>" +
					"<BODY>" +
					"This user name is already taken ! Please take an other one.<BR>" +
					"<button onclick=\"window.location.href='/myapp/signup.html'\">Back</button>" +
					"</BODY>" +
					"</HTML>"); 
		} else {

			EntityManagerHelper.getEntityManager().persist(a);
			t.commit();
			out.println("<HTML>" + 
					"<HEAD>" + 
					"<meta charset=\"utf-8\" />" +
					"<title>RInterpreter</title>" + 
					"<link rel=\"stylesheet\" href=\"bower_components/codemirror/lib/codemirror.css\">" + 
					"<script src=\"bower_components/codemirror/lib/codemirror.js\"></script>" + 
					"<script src=\"bower_components/codemirror/mode/r/r.js\"></script>" + 
					"</HEAD>" + 
					"<BODY>" +
					"<FORM Method=\"POST\" Action=\"/myapp/rservlet\">" +
					"Author Name : <INPUT type=text size=20 name=\"author\" value=\"" + a.getName() + "\" READONLY><BR>" +
					"R Program Name : <INPUT type=text size=20 name=\"name\"><BR>" +
					"Program : <textarea id=\"myCode\" name=\"program\" cols=\"40\" rows=\"5\"></textarea><BR>" + 
					"<script type=\"text/javascript\">" +
					"window.onload = function(){" +
					"var myTextArea = document.getElementById(\"myCode\");" +
					"editor = CodeMirror.fromTextArea(myTextArea, {" +
					"lineNumbers: true," +
					"mode: \"r\"" +
					"});" +
					"};" +
					"</script>" +
					"<INPUT type=\"submit\" value=\"Run\">" +
					"</FORM>" +
					"</BODY>" + 
					"</HTML>");
			out.close();
		}
	}
}
