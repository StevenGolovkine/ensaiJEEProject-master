package com.undertow;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import business.RProgram;
import jpa.EntityManagerHelper;

/**
 * Class which manages the LoadSpecificR servlet.
 * @author steven
 *
 */
public class LoadSpecificR extends HttpServlet {

	private static final long serialVersionUID = -7865495864134699489L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RProgram prog =  EntityManagerHelper.getEntityManager().
				find(RProgram.class, Long.parseLong(request.getPathInfo().replaceAll("/", "")));
						
		PrintWriter out = response.getWriter();
		
		out.println("<HTML>" + 
						"<HEAD>" + 
							"<meta charset=\"utf-8\" />" +
							"<title>" + prog.getName() + "</title>" + 
							"<link rel=\"stylesheet\" href=\"../bower_components/codemirror/lib/codemirror.css\">" + 
							"<script src=\"../bower_components/codemirror/lib/codemirror.js\"></script>" + 
							"<script src=\"../bower_components/codemirror/mode/r/r.js\"></script>" + 
						"</HEAD>" + 
						"<BODY>" +
							"<FORM Method=\"POST\" Action=\"/myapp/rservlet\">" +
								"Author Name : <INPUT type=text size=20 name=\"author\" value=\"" + prog.getAuthor().getName() + "\" READONLY><BR>" +
								"R Program Name : <INPUT type=text size=20 name=\"name\" value=\"" + prog.getName()  + "\" READONLY><BR>" +
								"Program : <textarea id=\"myCode\" name=\"program\" cols=\"40\" rows=\"5\">" + prog.getProgram() +  "</textarea>" +	"<BR>" + 
								
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