//$Id$
package com.shopping.cart;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users/{id}/wish-list")
public class WishListResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<WishList> getWishList(@PathParam("id") int id){
		return WishListRepository.displayWishList(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public WishList insertItem(@PathParam("id") int id, WishList wList){
		boolean status = false;
		wList.setUserId(id);
		
		status = WishListRepository.insertItems(wList);
		
		if(status){
			return wList;
		}
		
		return new WishList();
	}
	
	@Path("/{pid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteAddress(@PathParam("id") int id, @PathParam("pid") int pid){
		boolean status = false;
		WishList wList = new WishList();
		wList.setProductId(pid);
		wList.setUserId(id);
		status = WishListRepository.deleteItem(wList);
				
		return status;
	}
	
}
