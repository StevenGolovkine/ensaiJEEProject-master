package com.undertow;

import java.io.IOException;
import java.io.PrintWriter;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.Author;
import jpa.EntityManagerHelper;

/**
 * Class which manages the SignIn servlet.
 * @author steven
 *
 */
public class SignIn extends HttpServlet {

	private static final long serialVersionUID = -3861952816100762681L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");

		PrintWriter out = response.getWriter();
		
		try {
		Author author = (Author)EntityManagerHelper.getEntityManager().
				createQuery("SELECT a FROM Author AS a WHERE a.name='" + request.getParameter("author") + 
						"' AND a.password='" + request.getParameter("password") + "'").getSingleResult();
		
		out.println("<HTML>" + 
						"<BODY>" +
								"Hi " + author.getName() + " ! Welcome on RInterpreter. What do you want to do? <BR>" +
								"<FORM Method=\"POST\" Action=\"/myapp/index\">" +
								"<INPUT type=hidden name=\"author\" value=\"" + author.getName() + "\">" +
								"<INPUT type=\"submit\" value=\"New R Script\"><BR>" +
								"</FORM>" +
								
								"<FORM Method=\"POST\" Action=\"/myapp/load\">" +
								"<INPUT type=hidden name=\"author\" value=\"" + author.getName() + "\">" +
								"<INPUT type=\"submit\" value=\"Load an existing R Script\"><BR>" +
								"</FORM>" +
						"</BODY>" +
					"</HTML>");
		
		} catch (NoResultException e){
		
			out.println("<HTML>" +
							"<BODY>" +
								"Sorry, wrong user name and/or wrong password ! <BR>" +
								"<button onclick=\"window.location.href='/myapp/signin.html'\">Back</button>" +
							"</BODY>" +
						"</HTML>");
		}
	}
}
