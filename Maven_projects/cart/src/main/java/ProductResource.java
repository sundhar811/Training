//$Id$
package com.shopping.cart;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/products")
public class ProductResource {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getSomeProducts(
			@DefaultValue("0") @QueryParam("offset") int offset,
			@DefaultValue("15") @QueryParam("limit") int limit){
		return ProductRepository.getSomeProducts(offset, limit);		
	}
	
	@Path("/id")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProducts(){
		return ProductRepository.getProducts();
	}
	
	@Path("/name")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsByName(){
		return ProductRepository.getProductsByName();
	}
	
	@Path("/price")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsByPrice(){
		return ProductRepository.getProductsByPrice();
	}
	
	@Path("/{pid}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Product getProduct(@PathParam("pid") int pid){
		return ProductRepository.getProduct(pid);
	}
}
