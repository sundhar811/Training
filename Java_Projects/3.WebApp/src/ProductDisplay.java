//$Id$
package com.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ProductDisplay extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		PrintWriter out = response.getWriter();
		String type = request.getParameter("display");
		ResultSet rs = null;
		
//		out.println("Type: "+request.getParameter("display"));
        
        response.setContentType("text/html");
		out.println("<link rel='stylesheet' href='styles.css'>");
		out.println("<header align = 'right'>");
		out.println("<a class = 'headerlink' href = 'product'>&laquo; Back</a>");
		out.println("<a class = 'headerlink' href = 'frontpage'>&laquo; Frontpage</a>");
		out.println("</header>");
		
		out.println("<br>");
		out.println("<table class = 'center'>");
		
		out.println("<tr>");
		out.println("<th class = 'product'>Product Id</th>");
		out.println("<th class = 'product'>Product Name</th>");
		out.println("<th class = 'product'>Stock</th>");
		out.println("<th class = 'product'>Price</th>");
		out.println("<th class = 'product'>Avg. Rating</th>");
		out.println("<th class = 'product'>Seller Name</th>");
		out.println("</tr>");
		
		
        try {
			DatabaseClass.createConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		if(type.equals("id")){
			
			try {
				 rs = DatabaseClass.displayAllProductsById();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		else if(type.equals("name")){
			
			try {
				 rs = DatabaseClass.displayAllProductsByName();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		else if(type.equals("price")){
			try {
				 rs = DatabaseClass.displayAllProductsByPrice();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else{
			response.sendRedirect("errorpage");
		}
		
		try {
			while(rs.next()){
				out.println("<tr>");
				out.println("<td class = 'product'>"+rs.getInt(1)+"</td>");
				out.println("<td class = 'product'>"+rs.getString(2)+"</td>");
				out.println("<td class = 'product'>"+rs.getInt(3)+"</td>");
				out.println("<td class = 'product'>"+rs.getFloat(4)+"</td>");
				out.println("<td class = 'product'>"+rs.getFloat(5)+"</td>");
				out.println("<td class = 'product'>"+rs.getString(6)+"</td>");
				out.println("</tr>");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		out.println("</table>");
		
		out.println("<br><br>");
		out.println("<form action='product' class = 'center'><input type='submit' Value ='Back to Product Page' /></form>");
		out.println("<form action='frontpage' class = 'center'><input type='submit' Value ='Go To Home Page' /></form>");
		
		try {
			rs.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			DatabaseClass.terminateConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
