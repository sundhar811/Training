//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SellerDetailsRepository {
	//-----------------------------GET Seller Details-----------------------------
	
		public static ResultSet fetchSellerCredentials(int sellerId) throws SQLException{
	        String query = "SELECT loginname, password FROM seller_credentials WHERE sid = "+sellerId;

	        PreparedStatement ps =  DataBaseClass.connection.prepareStatement(query);

	        ResultSet rs = ps.executeQuery();

	        return rs;
	    }

	    public static ResultSet fetchSellerInfo(int sellerId) throws SQLException{
	        String query = "SELECT sname, smail FROM sellers WHERE sid = "+sellerId;

	        PreparedStatement ps =  DataBaseClass.connection.prepareStatement(query);

	        ResultSet rs = ps.executeQuery();

	        return rs;
	    }
		
		public static SellerDetail getSellerDetails(int seller_id) {
	        String uname = null;
	        String pwd = null;
	        String name = null;
	        String email = null;
	        ResultSet rs;

	        SellerDetail seller = new SellerDetail();

	        try {
	        	
				DataBaseClass.createConnection();
				rs = fetchSellerCredentials(seller_id);

		        if(rs.isBeforeFirst()){
		            rs.next();

		            uname = rs.getString(1);
		            pwd = rs.getString(2);
		        }
		        else{
		            System.out.println("Error fetching seller credentials from DB");
		        }

		        rs.close();

		        rs = fetchSellerInfo(seller_id);

		        if(rs.isBeforeFirst()){
		            rs.next();

		            name = rs.getString(1);
		            email = rs.getString(2);
		        }
		        else{
		            System.out.println("Error fetching user details from DB");
		        }

		        rs.close();

		        seller.setId(seller_id);
		        seller.setUsername(uname);
		        seller.setPassword(pwd);
		        seller.setName(name);
		        seller.setEmail(email);

		        DataBaseClass.terminateConnection();
		        
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        return seller;
	    }

		//-----------------------------UPDATE seller details---------------------------
		
		public static int updateSellerCredentialsIntoDb(SellerDetail seller) throws SQLException{
	        int rows_affected;
	        String query = "UPDATE seller_credentials SET password = ? WHERE sid = ?";

	        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
	        ps.setString(1, seller.getPassword());
	        ps.setInt(2, seller.getId());

	        rows_affected = ps.executeUpdate();

//	        System.out.println("Seller PASSWORD UPDATE IS SUCCESSFUL");

	        return rows_affected;
	    }

	    public static int updateSellerInfoIntoDb(SellerDetail seller) throws SQLException{
	        int rows_affected;
	        String query = "UPDATE sellers SET sname = ? WHERE sid = ?";

	        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
	        ps.setString(1, seller.getName());
	        ps.setInt(2, seller.getId());

	        rows_affected = ps.executeUpdate();

//	        System.out.println("USER NAME UPDATE IS SUCCESSFUL");

	        return rows_affected;
	    }
		
		public static boolean sellerDetailsUpdate(SellerDetail seller){
	        int status1 = 0, status2 = 0;
	      	
	    	try {
	    		
				DataBaseClass.createConnection();

	            if(seller.getName() != null){
	            	status1 = updateSellerInfoIntoDb(seller);
	            }
	            if(seller.getPassword() != null){
	            	status2 = updateSellerCredentialsIntoDb(seller);
	            }
	            DataBaseClass.terminateConnection();
	        }
	        catch (SQLException | ClassNotFoundException s){
//	                System.out.println("Unexpected error occurred. Please try later.");
	            s.printStackTrace();
	        }

	        if((status1>0)||(status2>0)){
	            return true;
	        }

	        else{
	            return false;
	        }
	    }
}
