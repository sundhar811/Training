//$Id$
package com.shopping.cart;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users/{id}/infos")
public class UserAddressResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserAddress> getAddresses(@PathParam("id") int id){
		return UserAddressRepository.displayUserAddress(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public UserAddress insertAddress(@PathParam("id") int id, UserAddress address){
		boolean status = false;
		address.setUserId(id);
		
		status = UserAddressRepository.insertUserAddress(address);
		
		if(status){
			return address;
		}
		
		return new UserAddress();
	}
	
	@Path("/{aid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UserAddress getAddress(@PathParam("id") int id, @PathParam("aid") int aid){
		
		return UserAddressRepository.getUserAddress(id, aid);
	}
	
	
	@Path("/{aid}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public UserAddress updateAddress(@PathParam("id") int id, @PathParam("aid") int aid, UserAddress address){
		boolean status = false;
		UserAddress retrieved_address = UserAddressRepository.getUserAddress(id, aid);
		
		if(address.getPhoneNumber() != 0){
			retrieved_address.setPhoneNumber(address.getPhoneNumber());
		}
		
		if(address.getUserPinCode() != 0){
			retrieved_address.setUserPinCode(address.getUserPinCode());
		}
		
		if(address.getUserAddress() != null){
			retrieved_address.setUserAddress(address.getUserAddress());
		}
		
		status = UserAddressRepository.updateUserAddress(retrieved_address);
		
		if(status){
			return retrieved_address;
		}
		
		return new UserAddress();
	}
	
	@Path("/{aid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAddress(@PathParam("id") int id, @PathParam("aid") int aid){
		boolean status = false;
		
		status = UserAddressRepository.deleteUserAddress(id, aid);
				
		return status;
	}
}
