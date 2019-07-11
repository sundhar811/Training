//$Id$
package com.shopping.cart;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/users/{id}/items")
public class CartResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Cart> getUserCart(@PathParam("id") int id){
		List<Cart> carts = null;
		try {
			DataBaseClass.createConnection();
			carts =  CartRepository.displayUserCart(id);
			DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return carts;
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Cart addItem(@PathParam("id") int id, Cart cart){
		if(cart.getProductQuantity()>0){
			cart.setUserId(id);
			cart = CartRepository.addProductsToCart(cart);
		}
		return cart;
	}
	
	@Path("/{pid}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Cart modifyItem(@PathParam("id") int id, @PathParam("pid") int pid, Cart cart){
		if(cart.getProductQuantity()>0){
			cart.setUserId(id);
			cart.setProductId(pid);
			cart = CartRepository.modifyProductInCart(cart, true);
		}
		return cart;
	}
	
	@Path("/{pid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteItem(@PathParam("id") int id, @PathParam("pid") int pid){
		return CartRepository.removeProductFromCart(id, pid);
	}
	
}
