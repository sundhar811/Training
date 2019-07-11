//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SellerProductRepository {
	//------------------------------List Products--------------------------------------------
	public static List<Product> displayCurrentSellerProduct(int id){
        List<Product> list_of_products = new ArrayList<>();

        try {
			DataBaseClass.createConnection();
	        String query = "SELECT pid, pname, stock, price, avg_rating FROM product WHERE " +
	                "sid = "+id+" AND stock>0";

	        Statement stmt = DataBaseClass.connection.createStatement();

	        ResultSet rs = stmt.executeQuery(query);

	        while(rs.next()){
	            Product product = new Product();
	            product.setProductId(rs.getInt(1));
	            product.setProductName(rs.getString(2));
	            product.setProductStock(rs.getInt(3));
	            product.setProductPrice(rs.getFloat(4));
	            product.setAvgRating(rs.getFloat(5));

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
	
	public static List<Product> displayOldSellerProduct(int id){
        List<Product> list_of_products = new ArrayList<>();

        try {
			DataBaseClass.createConnection();
			String query = "SELECT pid, pname, stock, price, avg_rating FROM product" +
	                " WHERE sid = "+id+" AND stock = 0";
	        Statement stmt = DataBaseClass.connection.createStatement();

	        ResultSet rs = stmt.executeQuery(query);

	        while(rs.next()){
	            Product product = new Product();
	            product.setProductId(rs.getInt(1));
	            product.setProductName(rs.getString(2));
	            product.setProductStock(rs.getInt(3));
	            product.setProductPrice(rs.getFloat(4));
	            product.setAvgRating(rs.getFloat(5));

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
	
	//-------------------------------------Add Products-----------------------------------------------
	
	public static int addNewProducts(Product product){
		int status = 0;
		
		try {
			DataBaseClass.createConnection();
			
			String query = "INSERT INTO product(sid, pname, stock, price) VALUES(?, ?, ?, ?)";

	        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
	        ps.setInt(1, product.getSellerId());
	        ps.setString(2, product.getProductName());
	        ps.setInt(3, product.getProductStock());
	        ps.setFloat(4, product.getProductPrice());
	        status = ps.executeUpdate();
			
			
			DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return status;
	}
	

	//-------------------------------------------Modify product-------------------------------------------
	public static boolean isProductAssociatedWithSeller(int sid, int pid) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM product WHERE sid = ? AND pid = ?";
        
        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setInt(1, sid);
        ps.setInt(2, pid);

        ResultSet rs = ps.executeQuery();

        if(rs.isBeforeFirst()){
            rs.close();
            return true;
        }

        else {
            System.out.println("The entered product is not associated with you.");
            rs.close();
            return false;
        }
    }
	
	public static int getProductStock(int pid) throws SQLException{
        PreparedStatement ps;
        String query;
        int stock;

        query = "SELECT stock FROM product WHERE pid = "+pid;

        ps = DataBaseClass.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            stock = rs.getInt(1);
        }
        else{
            stock = -1;
        }
        rs.close();
        return stock;
    }

    public static int updateProductStockForSeller(Product product) throws SQLException{
        int rows_affected = 0;
        
		try {
			if(isProductAssociatedWithSeller(product.getSellerId(), product.getProductId())){
				String query = "UPDATE product SET stock = ? WHERE pid = ?";

			    PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

			    ps.setInt(1, product.getProductStock());
			    ps.setInt(2, product.getProductId());

			    rows_affected = ps.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rows_affected;
    }
    
    public static String getProductName(int pid) throws SQLException{
    	PreparedStatement ps;
        String query;
        String pname;

        query = "SELECT pname FROM product WHERE pid = "+pid;

        ps = DataBaseClass.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            pname = rs.getString(1);
        }
        else{
            pname = "NO PRODUCTS RETURNED IN DB QUERY";
        }
        rs.close();
        return pname;
    }

    public static int updateProductName(Product product) throws SQLException{
        int rows_affected = 0;
        
		try {
			if(isProductAssociatedWithSeller(product.getSellerId(), product.getProductId())){
				String query = "UPDATE product SET pname = ? WHERE pid = ?";

			    PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

			    ps.setString(1, product.getProductName());
			    ps.setInt(2, product.getProductId());

			    rows_affected = ps.executeUpdate();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return rows_affected;
    }
    
    public static float getProductPrice(int pid) throws SQLException{
        PreparedStatement ps;
        String query;
        float price;

        query = "SELECT price FROM product WHERE pid = "+pid;

        ps = DataBaseClass.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            price = rs.getFloat(1);
        }
        else{
            price = -1;
        }
        rs.close();
        return price;
    }
    
    public static int updateProductPriceInProductList(Product product) throws SQLException{
        int rows_affected;
        String query = "UPDATE product SET price = ? WHERE pid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

        ps.setFloat(1, product.getProductPrice());
        ps.setInt(2, product.getProductId());

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static int updateProductPriceInUserCart(Product product) throws SQLException{
        int rows_affected;
        String query = "UPDATE current_cart SET price = qty*? WHERE pid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

        ps.setFloat(1, product.getProductPrice());
        ps.setInt(2, product.getProductId());

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }
    
    public static Product updateProductPrice(Product product) throws SQLException{   
    	int status1 = 0, status2 = 0;
    	
		try {
			if(isProductAssociatedWithSeller(product.getSellerId(), product.getProductId())){
				try{
		            DataBaseClass.connection.setAutoCommit(false);

		            status1 = updateProductPriceInProductList(product);
		            status2 = updateProductPriceInUserCart(product);
		            
		            DataBaseClass.connection.commit();
		            DataBaseClass.connection.setAutoCommit(true);
		        }
		        catch (SQLException s){
		            System.out.println("Unexpected error occurred. Please try later.");
		            DataBaseClass.connection.rollback();
		            s.printStackTrace();
		        }
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if((status1>0)&&(status2>0)){
		    return product;
		}
		
		else{
			return new Product();
		}
    }
    
    //----------------------------------delete product-------------------------------------------
    public static boolean deleteProduct(int seller_id, int pid) throws SQLException{
        int rows_affected = 0;
        
		try {
			DataBaseClass.createConnection();
			
			if(isProductAssociatedWithSeller(seller_id, pid)){
				String query = "UPDATE product SET stock = 0 WHERE sid = ? AND pid = ?";

			    PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

			    ps.setInt(1, seller_id);
		        ps.setInt(2, pid);

			    rows_affected = ps.executeUpdate();
			}
			
			DataBaseClass.terminateConnection();
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rows_affected>0){
            return true;
        }

        else{
            return false;
        }
    }
}
