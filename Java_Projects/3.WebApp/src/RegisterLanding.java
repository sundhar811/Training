//$Id$
package com.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterLanding extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		PrintWriter out = response.getWriter();
		String type, name, email, un, pw;	
		String errMsg = "";
		
		type = request.getParameter("acc_type");
		name = request.getParameter("name");
		email = request.getParameter("email");
		un = request.getParameter("username");
		pw = request.getParameter("password");
		
		if(type.equals("user")){
			boolean user_redundancy_flag = true, email_redundancy_flag = true;
			
			try {
				email_redundancy_flag = DatabaseClass.checkUserEmailRedundancy(email);
				
				user_redundancy_flag = DatabaseClass.checkUserLoginNameRedundancy(un);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(email_redundancy_flag){
				errMsg+="User email is not unique.<br>";
			}
			if(user_redundancy_flag){
				errMsg+="Username is not unique.<br>";
			}
			
			if(user_redundancy_flag || email_redundancy_flag){
				request.setAttribute("errMessage", errMsg);
	        	request.getRequestDispatcher("register").forward(request, response);
			}
			else{
				UserBuilderPlan new_user = new UserBuilder();	

		        new_user.buildUserName(un);
		        new_user.buildPassWord(pw);
		        new_user.buildName(name);
		        new_user.buildEmail(email);
		        
//		        out.print(new_user.getUser().toString());
		        
		        try {
					DatabaseClass.createConnection();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		        int status1 = 0, status2 = 0;

		        try {
		            DatabaseClass.connection.setAutoCommit(false);

		            status1 = DatabaseClass.insertUserInfoIntoDb(new_user.getUser());
		            status2 = DatabaseClass.insertUserCredentialsIntoDb(new_user.getUser());

		            DatabaseClass.connection.commit();
		            DatabaseClass.connection.setAutoCommit(true);
		        }
		        catch (SQLException s){
//		            System.out.println("Unexpected error occurred. Please try later.");
		            try {
						DatabaseClass.connection.rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            s.printStackTrace();
		        }

		        try {
					DatabaseClass.terminateConnection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        if((status1>0)&&(status2>0)){
//		        	request.getRequestDispatcher("regsuccess").forward(request, response);
		        	response.sendRedirect("regsuccess");
		        }

		        else{
		        	response.sendRedirect("errorpage");
		        }
			    
			}
  
		}
		
		else if(type.equals("seller")) {
boolean user_redundancy_flag = true, email_redundancy_flag = true;
			
			try {
				email_redundancy_flag = DatabaseClass.checkSellerEmailRedundancy(email);
				
				user_redundancy_flag = DatabaseClass.checkSellerLoginNameRedundancy(un);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(email_redundancy_flag){
				errMsg+="Seller email is not unique.<br>";
			}
			if(user_redundancy_flag){
				errMsg+="Seller username is not unique.<br>";
			}
			
			if(user_redundancy_flag || email_redundancy_flag){
				request.setAttribute("errMessage", errMsg);
	        	request.getRequestDispatcher("register").forward(request, response);
			}
			else{
				SellerBuilderPlan new_seller = new SellerBuilder();

		        new_seller.buildSellerName(un);
		        new_seller.buildPassWord(pw);
		        new_seller.buildName(name);
		        new_seller.buildEmail(email);
		        
//		        out.println(new_seller.getSeller().toString());
		        
		        try {
					DatabaseClass.createConnection();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

		        int status1 = 0, status2 = 0;

		        try {
		            DatabaseClass.connection.setAutoCommit(false);

		            status1 = DatabaseClass.insertSellerInfoIntoDb(new_seller.getSeller());
		            status2 = DatabaseClass.insertSellerCredentialsIntoDb(new_seller.getSeller());

		            DatabaseClass.connection.commit();
		            DatabaseClass.connection.setAutoCommit(true);
		        }
		        catch (SQLException s){
//		            System.out.println("Unexpected error occurred. Please try later.");
		            try {
						DatabaseClass.connection.rollback();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            s.printStackTrace();
		        }

		        try {
					DatabaseClass.terminateConnection();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		        if((status1>0)&&(status2>0)){
//		        	request.getRequestDispatcher("regsuccess").forward(request, response);
		        	response.sendRedirect("regsuccess");
		        }

		        else{
		        	response.sendRedirect("errorpage");
		        }
			}
		}
		
		else{
			response.sendRedirect("errorpage");
		}
	}
}
