//$Id$
package com.shopping.cart;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("sellers/{id}")
public class SellerDetailResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public SellerDetail getSellerDetail(@PathParam("id") int id){

		return SellerDetailsRepository.getSellerDetails(id);
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public SellerDetail updateSellerDetail(@PathParam("id") int id, SellerDetail seller){
		boolean status = false;
		seller.setId(id);
		
		status = SellerDetailsRepository.sellerDetailsUpdate(seller);
		
		if(status){
			return seller;
		}
		
		return new SellerDetail();
	}
}
