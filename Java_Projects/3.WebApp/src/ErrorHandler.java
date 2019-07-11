//$Id$
package com.webapp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdk.internal.org.objectweb.asm.tree.analysis.Value;

public class ErrorHandler extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException{
		PrintWriter out = response.getWriter();
		String str = "Common error page. Some unknown error occured.";
		
		response.setContentType("text/html");
		out.println("<link rel='stylesheet' href='styles.css'>");
		out.println("<header align = 'right'><a class = 'headerlink' href = 'frontpage'>&laquo; Frontpage</a></header>");
		
		out.println("<br><p class = 'center'>");
		out.println(str);
		out.println("<br><br><form action='frontpage' class = 'center'><input type='submit' Value ='Go To Home Page' /></form>");
		out.println("</p>");
	}
}
