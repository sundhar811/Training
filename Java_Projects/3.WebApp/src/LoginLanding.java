//$Id$
package com.webapp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.glass.ui.CommonDialogs.Type;

public class LoginLanding extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		String type, un, pw;
		int id = -1;
		
		type = request.getParameter("acc_type");
		un = request.getParameter("username");
		pw = request.getParameter("password");
		

		
		if(type.equals("user")){
			UserBuilderPlan login_user = new UserBuilder();
	        login_user.buildUserName(un);
	        login_user.buildPassWord(pw);
			
			try {
				id = DatabaseClass.checkUserCredentials(login_user);
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Can't create session");
				e.printStackTrace();
			}
			
			session.setAttribute("account_type", type);
			session.setAttribute("userid", id);
			
			out.println("User id: "+session.getAttribute("userid"));
			
//			response.sendRedirect("userfrontpage");
		}
		
		else if(type.equals("seller")){
			SellerBuilderPlan login_seller = new SellerBuilder();
	        login_seller.buildSellerName(un);
	        login_seller.buildPassWord(pw);
	        
	        try {
				id = DatabaseClass.checkSellerCredentials(login_seller);
			} catch (ClassNotFoundException | SQLException e) {
				System.out.println("Can't create session");
				e.printStackTrace();
			}
			
	        session.setAttribute("account_type", type);
			session.setAttribute("sellerid", id);
			
			out.println("Seller id: "+session.getAttribute("sellerid"));
			
//			response.sendRedirect("sellerfrontpage");
		}
		
		else{
			response.sendRedirect("errorpage");
		}
		
		if(id<0){
			request.setAttribute("errMessage", "Invalid username/password");
        	request.getRequestDispatcher("login").forward(request, response);
		}
		
	}
}
