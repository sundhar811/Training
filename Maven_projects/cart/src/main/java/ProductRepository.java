//$Id$
package com.shopping.cart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {
	//---------------------------------------List Products------------------------------------------------
	
	public static List<Product> getSomeProducts(int offset, int limit){
		List<Product> list_of_products = new ArrayList<>();
		
		try {
			DataBaseClass.createConnection();
			
			String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname FROM product as p"
					+ " INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0 LIMIT "+offset+", "+limit;

	        Statement stmt = DataBaseClass.connection.createStatement();

	        ResultSet rs = stmt.executeQuery(query);

	        while(rs.next()){
	            Product product = new Product();
	            product.setProductId(rs.getInt(1));
	            product.setProductName(rs.getString(2));
	            product.setProductStock(rs.getInt(3));
	            product.setProductPrice(rs.getFloat(4));
	            product.setAvgRating(rs.getFloat(5));

	            SellerDetail seller = new SellerDetail();
	            seller.setName(rs.getString(6));
	            product.setSellerDetail(seller);
	            
	            list_of_products.add(product);
	        }

	        rs.close();
	        DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return list_of_products;
	}
	
	public static List<Product> getProducts(){
		List<Product> list_of_products = new ArrayList<>();
		
		try {
			DataBaseClass.createConnection();
			
			String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
	                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0";

	        Statement stmt = DataBaseClass.connection.createStatement();

	        ResultSet rs = stmt.executeQuery(query);

	        while(rs.next()){
	            Product product = new Product();
	            product.setProductId(rs.getInt(1));
	            product.setProductName(rs.getString(2));
	            product.setProductStock(rs.getInt(3));
	            product.setProductPrice(rs.getFloat(4));
	            product.setAvgRating(rs.getFloat(5));

	            SellerDetail seller = new SellerDetail();
	            seller.setName(rs.getString(6));
	            product.setSellerDetail(seller);
	            
	            list_of_products.add(product);
	        }

	        rs.close();
	        DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return list_of_products;
	}
	
	public static List<Product> getProductsByName(){
		List<Product> list_of_products = new ArrayList<>();
		
		try {
			DataBaseClass.createConnection();
			
			String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
	                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0 ORDER BY p.pname";

	        Statement stmt = DataBaseClass.connection.createStatement();

	        ResultSet rs = stmt.executeQuery(query);

	        while(rs.next()){
	            Product product = new Product();
	            product.setProductId(rs.getInt(1));
	            product.setProductName(rs.getString(2));
	            product.setProductStock(rs.getInt(3));
	            product.setProductPrice(rs.getFloat(4));
	            product.setAvgRating(rs.getFloat(5));

	            SellerDetail seller = new SellerDetail();
	            seller.setName(rs.getString(6));
	            product.setSellerDetail(seller);
	            
	            list_of_products.add(product);
	        }

	        rs.close();
	        DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return list_of_products;
	}
	
	public static List<Product> getProductsByPrice(){
		List<Product> list_of_products = new ArrayList<>();
		
		try {
			DataBaseClass.createConnection();
			
			String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
	                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0 ORDER BY p.price";

	        Statement stmt = DataBaseClass.connection.createStatement();

	        ResultSet rs = stmt.executeQuery(query);

	        while(rs.next()){
	            Product product = new Product();
	            product.setProductId(rs.getInt(1));
	            product.setProductName(rs.getString(2));
	            product.setProductStock(rs.getInt(3));
	            product.setProductPrice(rs.getFloat(4));
	            product.setAvgRating(rs.getFloat(5));

	            SellerDetail seller = new SellerDetail();
	            seller.setName(rs.getString(6));
	            product.setSellerDetail(seller);
	            
	            list_of_products.add(product);
	        }

	        rs.close();
	        DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return list_of_products;
	}
	
	public static Product getProduct(int pid){
		Product product = new Product();
		
		try {
			DataBaseClass.createConnection();
			
			String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
	                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.pid = "+pid;

	        Statement stmt = DataBaseClass.connection.createStatement();

	        ResultSet rs = stmt.executeQuery(query);

	        while(rs.next()){
	            product.setProductId(rs.getInt(1));
	            product.setProductName(rs.getString(2));
	            product.setProductStock(rs.getInt(3));
	            product.setProductPrice(rs.getFloat(4));
	            product.setAvgRating(rs.getFloat(5));

	            SellerDetail seller = new SellerDetail();
	            seller.setName(rs.getString(6));
	            product.setSellerDetail(seller);
	        }

	        rs.close();
	        DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return product;
	}
}
