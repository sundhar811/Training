//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SellerAddressRepository {
	public static List<SellerAddress> displaySellerAddress(int seller_id){
		List<SellerAddress> addresses = new ArrayList<SellerAddress>();
        String query = "SELECT p_no, addr, pincode, asid FROM seller_details WHERE sid = "+seller_id;

        try {
			DataBaseClass.createConnection();
			
			Statement stmt = DataBaseClass.connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
	        	SellerAddress address = new SellerAddress();
				address.setSellerId(seller_id);
				address.setPhoneNumber(rs.getLong(1));
				address.setSellerAddress(rs.getString(2));
				address.setSellerPinCode(rs.getInt(3));
				address.setSellerAddressId(rs.getInt(4));
				
				addresses.add(address);
			}
	        
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return addresses;
    }
	
	public static boolean insertSellerAddress(SellerAddress address) {
        int rows_affected = 0;
        String query = "INSERT INTO seller_details(sid, p_no, addr, pincode) VALUES (?, ?, ?, ?)";

        try {
			DataBaseClass.createConnection();

	        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
	        ps.setInt(1, address.getSellerId());
	        ps.setLong(2, address.getPhoneNumber());
	        ps.setString(3, address.getSellerAddress());
	        ps.setInt(4, address.getSellerPinCode());
	        
	        rows_affected = ps.executeUpdate();
	        
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
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
	
	public static SellerAddress getSellerAddress(int seller_id, int asid){
        String query = "SELECT p_no, addr, pincode, asid FROM seller_details WHERE sid = "+seller_id+" AND asid = "+asid;

        try {
			DataBaseClass.createConnection();
			
			Statement stmt = DataBaseClass.connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        if (rs.next()) {
	        	SellerAddress address = new SellerAddress();
				address.setSellerId(seller_id);
				address.setPhoneNumber(rs.getLong(1));
				address.setSellerAddress(rs.getString(2));
				address.setSellerPinCode(rs.getInt(3));
				address.setSellerAddressId(rs.getInt(4));
				
				return address;
			}
	        
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return new SellerAddress();
    }
	
	public static boolean updateSellerAddress(SellerAddress address) {
        int rows_affected = 0;
        String query = "UPDATE seller_details SET p_no = ?, addr = ?, pincode = ? WHERE sid = ? AND asid = ?";

        try {
			DataBaseClass.createConnection();

	        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
	        ps.setLong(1, address.getPhoneNumber());
	        ps.setString(2, address.getSellerAddress());
	        ps.setInt(3, address.getSellerPinCode());
	        ps.setInt(4, address.getSellerId());
	        ps.setInt(5, address.getSellerAddressId());
	        
	        rows_affected = ps.executeUpdate();
	        
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
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
	
	public static boolean deleteSellerAddress(int id, int aid) {
        int rows_affected = 0;
        String query = "DELETE FROM seller_details WHERE sid = ? AND asid = ?";

        try {
			DataBaseClass.createConnection();

	        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
	        ps.setInt(1, id);
	        ps.setInt(2, aid);
	        
	        rows_affected = ps.executeUpdate();
	        
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
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
