//$Id$
package com.shopping.cart;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users/{id}/order-history")
public class OrderHistoryResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Purchase> getAllPurchases(@PathParam("id") int id){
		return OrderHistoryRepository.showOrderHistory(id);
	}
	
	@Path("/{bid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrderHistory> getPurchaseDetails(@PathParam("id") int id, @PathParam("bid") int bid){
		return OrderHistoryRepository.getPurchaseDetails(id, bid);
	}
	
}
