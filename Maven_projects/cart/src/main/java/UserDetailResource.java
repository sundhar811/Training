//$Id$
package com.shopping.cart;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("users/{id}")
public class UserDetailResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserDetail getUserDetail(@PathParam("id") int id){

		return UserDetailsRepository.getUserDetails(id);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public UserDetail updateUserDetail(@PathParam("id") int id, UserDetail user){
		boolean status = false;
		user.setId(id);
		
		status = UserDetailsRepository.userDetailsUpdate(user);
		
		if(status){
			return user;
		}
		
		return new UserDetail();
	}
	
}
