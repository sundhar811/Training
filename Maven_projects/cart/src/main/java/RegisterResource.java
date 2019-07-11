//$Id$
package com.shopping.cart;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sign-up")
public class RegisterResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Register registration(Register reg){
		
		boolean status = false;
		
		if(reg.getAcctype().equals("user")){
			status = RegisterRepository.userSignUp(reg);
		}
		
		else if(reg.getAcctype().equals("seller")){
			status = RegisterRepository.sellerSignUp(reg);
		}
		
		if(status){
			return reg;
		}
		
		return new Register();
		
		
	}
}
