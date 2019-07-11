//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserAddressRepository {

	public static List<UserAddress> displayUserAddress(int user_id){
		List<UserAddress> addresses = new ArrayList<UserAddress>();
        String query = "SELECT p_no, addr, pincode, auid FROM user_details WHERE uid = "+user_id;

        try {
			DataBaseClass.createConnection();
			
			Statement stmt = DataBaseClass.connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
				UserAddress address = new UserAddress();
				address.setUserId(user_id);
				address.setPhoneNumber(rs.getLong(1));
				address.setUserAddress(rs.getString(2));
				address.setUserPinCode(rs.getInt(3));
				address.setUserAddressId(rs.getInt(4));
				
				addresses.add(address);
			}
	        
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return addresses;
    }
	
	public static boolean insertUserAddress(UserAddress address) {
        int rows_affected = 0;
        String query = "INSERT INTO user_details(uid, p_no, addr, pincode) VALUES (?, ?, ?, ?)";

        try {
			DataBaseClass.createConnection();

	        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
	        ps.setInt(1, address.getUserId());
	        ps.setLong(2, address.getPhoneNumber());
	        ps.setString(3, address.getUserAddress());
	        ps.setInt(4, address.getUserPinCode());
	        
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
	
	public static UserAddress getUserAddress(int user_id, int auid){
        String query = "SELECT p_no, addr, pincode, auid FROM user_details WHERE uid = "+user_id+" AND auid = "+auid;

        try {
			DataBaseClass.createConnection();
			
			Statement stmt = DataBaseClass.connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        if (rs.next()) {
				UserAddress address = new UserAddress();
				address.setUserId(user_id);
				address.setPhoneNumber(rs.getLong(1));
				address.setUserAddress(rs.getString(2));
				address.setUserPinCode(rs.getInt(3));
				address.setUserAddressId(rs.getInt(4));
				
				return address;
			}
	        
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return new UserAddress();
    }
	
	public static boolean updateUserAddress(UserAddress address) {
        int rows_affected = 0;
        String query = "UPDATE user_details SET p_no = ?, addr = ?, pincode = ? WHERE uid = ? AND auid = ?";

        try {
			DataBaseClass.createConnection();

	        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
	        ps.setLong(1, address.getPhoneNumber());
	        ps.setString(2, address.getUserAddress());
	        ps.setInt(3, address.getUserPinCode());
	        ps.setInt(4, address.getUserId());
	        ps.setInt(5, address.getUserAddressId());
	        
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
	
	public static boolean deleteUserAddress(int id, int aid) {
        int rows_affected = 0;
        String query = "DELETE FROM user_details WHERE uid = ? AND auid = ?";

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
