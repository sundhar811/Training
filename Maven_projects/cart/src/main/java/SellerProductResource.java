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

@Path("/sellers/{id}/products")
public class SellerProductResource {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getSellerProducts(@PathParam("id") int id){
		return SellerProductRepository.displayCurrentSellerProduct(id);
	}
	
	@Path("/old")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getSellerOldProducts(@PathParam("id") int id){
		return SellerProductRepository.displayOldSellerProduct(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Product addProducts(@PathParam("id") int id, Product product){
		if((product.getProductName()!=null)&&(product.getProductPrice()>=0)&&(product.getProductStock()>0)){
			product.setSellerId(id);
			int status = SellerProductRepository.addNewProducts(product);
			if(status>0){
				return product;
			}
		}
		return new Product();
	}
	
	@Path("/{pid}/stock")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Product modifyProductStock(@PathParam("id") int id, @PathParam("pid") int pid, Product product){
		int status = 0;
		
		if(product.getProductStock()>0){
			product.setProductId(pid);
			product.setSellerId(id);
			
			try {
				DataBaseClass.createConnection();
				status = SellerProductRepository.updateProductStockForSeller(product);
				DataBaseClass.terminateConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(status>0){
				return product;
			}
		}
		return new Product();
	}
	
	@Path("/{pid}/name")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Product modifyProductName(@PathParam("id") int id, @PathParam("pid") int pid, Product product){
		int status = 0;
		
		if(product.getProductName() != null){
			product.setProductId(pid);
			product.setSellerId(id);
			
			try {
				DataBaseClass.createConnection();
				status = SellerProductRepository.updateProductName(product);
				DataBaseClass.terminateConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(status>0){
				return product;
			}
		}
		return new Product();
	}
	
	@Path("/{pid}/price")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Product modifyProductPrice(@PathParam("id") int id, @PathParam("pid") int pid, Product product){
		
		if(product.getProductPrice()>=0){
			product.setProductId(pid);
			product.setSellerId(id);
			
			try {
				DataBaseClass.createConnection();
				product = SellerProductRepository.updateProductPrice(product);
				DataBaseClass.terminateConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return product;
	}
	
	@Path("/{pid}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteProduct(@PathParam("id") int id, @PathParam("pid") int pid){
		boolean status = false;
		
		try {
			status = SellerProductRepository.deleteProduct(id, pid);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return status;
	}
}
