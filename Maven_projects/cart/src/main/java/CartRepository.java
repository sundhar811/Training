//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CartRepository {
	
	//-----------------------------------List user cart-------------------------------------------
	public static List<Cart> displayUserCart(int user_id){
		List<Cart> cart_products = new ArrayList<Cart>();
		String query = "SELECT c.pid, p.pname, c.qty, c.price FROM current_cart as c " +
                "INNER JOIN product as p WHERE c.pid=p.pid AND c.uid="+user_id;

        try {
			
			Statement stmt = DataBaseClass.connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
				Cart cart_product = new Cart();
				cart_product.setUserId(user_id);
				cart_product.setProductId(rs.getInt(1));
				cart_product.setProductName(rs.getString(2));
	            cart_product.setProductQuantity(rs.getInt(3));
	            cart_product.setProductPrice(rs.getFloat(4));

	            cart_products.add(cart_product);
			}
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return cart_products;
    }
	
	//------------------------------------General functions------------------------------------------
	
	public static boolean checkCartDuplicates(int uid, int pid, boolean flag) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM current_cart WHERE uid = ? AND pid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setInt(1, uid);
        ps.setInt(2, pid);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            if(!flag){
                System.out.println("Product exists in cart. The entered quantity will" +
                        " be added to the existing quantity.");
            }
            rs.close();
            return true;
        }

        if(flag){
            System.out.println("Product doesn't exists in cart.");
        }

        rs.close();
        return false;

    }
	
	public static int getCurrentQuantityFromCart(Cart cart) throws SQLException, ClassNotFoundException{
        String query = "SELECT qty price FROM current_cart WHERE uid = ? AND pid = ?";

        int current_qty = 0;

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setInt(1, cart.getUserId());
        ps.setInt(2, cart.getProductId());
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            current_qty = rs.getInt(1);
        }
        else{
            System.out.println("Can't resolve quantity of given item.");
        }

        rs.close();
        return current_qty;
    }
	
	public static boolean checkProductForCart(int product_id, int stock) {

		String query = "SELECT stock FROM product WHERE pid = ?";		
		PreparedStatement ps;
		try {
			ps = DataBaseClass.connection.prepareStatement(query);
			ps.setInt(1, product_id);
		
		      ResultSet rs = ps.executeQuery();
		
		      if(rs.next()){
		          int db_stock = rs.getInt(1);
		
		          if(db_stock>=(stock)){
		              //product stock available
		              rs.close();
		              return true;
		          }
		
		          rs.close();
		          System.out.println("Stock not available.");
		          return false;
		      }
		
		      System.out.println("Product doesn't exists.");
		      rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      
	    return false;
	}
	
	public static float computePriceForProductInCart(int pid, int stock) throws SQLException, ClassNotFoundException{
        float cprice;

        String query = "SELECT price FROM product WHERE pid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setInt(1, pid);

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            cprice = rs.getFloat(1);
            rs.close();
            return  cprice*(float)stock;
        }
        else {
            System.out.println("Error while retrieving price. Please try again.");
            return 0;
        }
    }
			
	//-------------------------------------- Add product to cart-------------------------------------

	public static int addProducts(Cart cart){
        int rows_affected = 0;
        String query = "INSERT INTO current_cart(uid, pid, qty, price) VALUES(?, ?, ?, ?)";

        PreparedStatement ps;
		try {
			ps = DataBaseClass.connection.prepareStatement(query);
			ps.setInt(1, cart.getUserId());
	        ps.setInt(2, cart.getProductId());
	        ps.setInt(3, cart.getProductQuantity());
	        ps.setFloat(4, cart.getProductPrice());

	        rows_affected = ps.executeUpdate();
	        
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rows_affected;
    }
	
	public static Cart addProductsToCart(Cart cart) {
		int status = 0;

		try {
			DataBaseClass.createConnection();
			
			if(!checkProductForCart(cart.getProductId(), cart.getProductQuantity())){
				return new Cart();
			}
			
			float cumulative_price = computePriceForProductInCart(cart.getProductId(), cart.getProductQuantity());
			cart.setProductPrice(cumulative_price);
			
	        if(checkCartDuplicates(cart.getUserId(), cart.getProductId(), false)){
	            cart = modifyProductInCart(cart, false);
	            return cart;
	        }
	        status = addProducts(cart);

	        DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if(status>0){
            return cart;
        }

        else{
            return new Cart();
        }
    }
	
	//--------------------------------Modify items in cart----------------------------------------
	
	public static int modifyProduct(Cart cart) throws SQLException{
        int rows_affected;
        String query = "UPDATE current_cart SET uid = ?, pid = ?, qty = ?, price = ? WHERE uid = ? AND pid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

        ps.setInt(1, cart.getUserId());
        ps.setInt(2, cart.getProductId());
        ps.setInt(3, cart.getProductQuantity());
        ps.setFloat(4, cart.getProductPrice());
        ps.setInt(5, cart.getUserId());
        ps.setInt(6, cart.getProductId());
        rows_affected = ps.executeUpdate();

        return rows_affected;
    }
	
	//true - modify existing product; false - add stock to existing product
	public static Cart modifyProductInCart(Cart cart, boolean flag){
        int qty, status = 0;
        
        try {
			DataBaseClass.createConnection();
			
			if (!flag){
	            qty = cart.getProductQuantity()+getCurrentQuantityFromCart(cart);
	        }
	        else {
	            qty = cart.getProductQuantity();
	        }
	        //int total_stock = qty+getCurrentQuantityFromCart(uid, pid);
	        cart.setProductQuantity(qty);
	        
	        if(!checkProductForCart(cart.getProductId(), cart.getProductQuantity())){
				return new Cart();
			}

	        float cumulative_price = computePriceForProductInCart(cart.getProductId(), cart.getProductQuantity());
	        cart.setProductPrice(cumulative_price);

	        status = modifyProduct(cart);

	        DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   
        if(status>0){
            return cart;
        }

        else{
            return new Cart();
        }
    }
	
	//----------------------------------Delete products-------------------------------------
	public static int removeProduct(int user_id, int pr_id) throws SQLException{
        int rows_affected;
        String query = "DELETE FROM current_cart WHERE uid=? AND pid=?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

        ps.setInt(1, user_id);
        ps.setInt(2, pr_id);

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }
	
	public static boolean removeProductFromCart(int uid, int pid){
		int status = 0;
		
        try {
        	DataBaseClass.createConnection();
        	
			if(!checkCartDuplicates(uid, pid, true)){
				return false;
				
			}
	        status = removeProduct(uid, pid);

	        DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        

        if(status>0){
            return true;
        }

        else{
            return false;
        }
    }
	
}
