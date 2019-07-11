//$Id$
package com.shopping.cart;

import java.sql.SQLException;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sign-in")
public class LoginResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Login authenticate(Login login){
		int id = -1;
		
		if(login.getAcctype().equals("user")){
			try {
				id = LoginRepository.checkUserCredentials(login);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if(login.getAcctype().equals("seller")){
			try {
				id = LoginRepository.checkSellerCredentials(login);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(id != -1){
			login.setId(id);
		
			return login;
		}
		else{
			return new Login();
		}
		
	}
}
