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

@Path("/sellers/{id}/infos")
public class SellerAddressResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<SellerAddress> getAddresses(@PathParam("id") int id){
		return SellerAddressRepository.displaySellerAddress(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public SellerAddress insertAddress(@PathParam("id") int id, SellerAddress address){
		boolean status = false;
		address.setSellerId(id);
		
		status = SellerAddressRepository.insertSellerAddress(address);
		
		if(status){
			return address;
		}
		
		return new SellerAddress();
	}
	
	@Path("/{aid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SellerAddress getAddress(@PathParam("id") int id, @PathParam("aid") int aid){
		
		return SellerAddressRepository.getSellerAddress(id, aid);
	}
	
	
	@Path("/{aid}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public SellerAddress updateAddress(@PathParam("id") int id, @PathParam("aid") int aid, SellerAddress address){
		boolean status = false;
		SellerAddress retrieved_address = SellerAddressRepository.getSellerAddress(id, aid);
		
		if(address.getPhoneNumber() != 0){
			retrieved_address.setPhoneNumber(address.getPhoneNumber());
		}
		
		if(address.getSellerPinCode() != 0){
			retrieved_address.setSellerPinCode(address.getSellerPinCode());
		}
		
		if(address.getSellerAddress() != null){
			retrieved_address.setSellerAddress(address.getSellerAddress());
		}
		
		status = SellerAddressRepository.updateSellerAddress(retrieved_address);
		
		if(status){
			return retrieved_address;
		}
		
		return new SellerAddress();
	}
	
	@Path("/{aid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAddress(@PathParam("id") int id, @PathParam("aid") int aid){
		boolean status = false;
		
		status = SellerAddressRepository.deleteSellerAddress(id, aid);
				
		return status;
	}
}
