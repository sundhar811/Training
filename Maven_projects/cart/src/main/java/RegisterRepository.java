//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterRepository {
	
	//-------------------------------USER REGISTRATION--------------------------------------
	
	public static boolean checkUserLoginNameRedundancy(String un) throws ClassNotFoundException{
		PreparedStatement ps;
        String query = "SELECT uid FROM user_credentials WHERE loginname = ?";
        
        try {
        	
			ps = DataBaseClass.connection.prepareStatement(query);
			ps.setString(1, un);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
	            System.out.println("Username already exists.");
	            return true;
	        }
	
	        rs.close();
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        return false;

    }
	
	public static boolean checkUserEmailRedundancy(String email) throws SQLException, ClassNotFoundException{
		PreparedStatement ps;
		String query = "SELECT uid FROM users WHERE umail = ?";
		
		try {
        
			ps = DataBaseClass.connection.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
	            System.out.println("email already exists.");
	            return true;
	        }
	
	        rs.close();
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        return false;
    }
	
	public static int insertUserCredentialsIntoDb(Register reg) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO user_credentials(loginname, password) VALUES(?, ?)";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setString(1, reg.getUsername());
        ps.setString(2, reg.getPassword());

        rows_affected = ps.executeUpdate();

//        System.out.println("USER CREDENTIAL INSERTION IS SUCCESSFUL");

        return rows_affected;
    }

    public static int insertUserInfoIntoDb(Register reg) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO users(uname, umail) VALUES(?, ?)";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setString(1, reg.getName());
        ps.setString(2, reg.getEmail());

        rows_affected = ps.executeUpdate();

//        System.out.println("USER INFORMATION INSERTION IS SUCCESSFUL");

        return rows_affected;
    }
	
	public static boolean userSignUp(Register reg){
        boolean user_redundancy_flag = true, email_redundancy_flag = true;
        int status1 = 0, status2 = 0;
        
        try {
			DataBaseClass.createConnection();
			email_redundancy_flag = checkUserEmailRedundancy(reg.getEmail());
	        user_redundancy_flag = checkUserLoginNameRedundancy(reg.getUsername());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        
        if(!user_redundancy_flag && !email_redundancy_flag){
        	try {
                DataBaseClass.connection.setAutoCommit(false);

                status1 = insertUserInfoIntoDb(reg);
                status2 = insertUserCredentialsIntoDb(reg);

                DataBaseClass.connection.commit();
                DataBaseClass.connection.setAutoCommit(true);
            }
            catch (SQLException s){
//                System.out.println("Unexpected error occurred. Please try later.");
                try {
    				DataBaseClass.connection.rollback();
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                s.printStackTrace();
            }
        }
 
        try {
			DataBaseClass.terminateConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if((status1>0)&&(status2>0)){
            return true;
        }

        else{
            return false;
        }
    }
	
	//-------------------------------SELLER REGISTRATION--------------------------------------
	
	public static boolean checkSellerLoginNameRedundancy(String un) throws ClassNotFoundException{
		PreparedStatement ps;
		String query = "SELECT sid FROM seller_credentials WHERE loginname = ?";
        
        try {
        	
			ps = DataBaseClass.connection.prepareStatement(query);
			ps.setString(1, un);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
	            System.out.println("Username already exists.");
	            return true;
	        }
	
	        rs.close();
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        return false;

    }
	
	public static boolean checkSellerEmailRedundancy(String email) throws SQLException, ClassNotFoundException{
		PreparedStatement ps;
		String query = "SELECT sid FROM sellers WHERE smail = ?";
		
		try {
        
			ps = DataBaseClass.connection.prepareStatement(query);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()){
	            System.out.println("email already exists.");
	            return true;
	        }
	
	        rs.close();
			
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
        
        return false;
    }
	
	public static int insertSellerCredentialsIntoDb(Register reg) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO seller_credentials(loginname, password) VALUES(?, ?)";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setString(1, reg.getUsername());
        ps.setString(2, reg.getPassword());

        rows_affected = ps.executeUpdate();

//        System.out.println("USER CREDENTIAL INSERTION IS SUCCESSFUL");

        return rows_affected;
    }

    public static int insertSellerInfoIntoDb(Register reg) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO sellers(sname, smail) VALUES(?, ?)";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setString(1, reg.getName());
        ps.setString(2, reg.getEmail());

        rows_affected = ps.executeUpdate();

//        System.out.println("USER INFORMATION INSERTION IS SUCCESSFUL");

        return rows_affected;
    }
	
	public static boolean sellerSignUp(Register reg){
        boolean user_redundancy_flag = true, email_redundancy_flag = true;
        int status1 = 0, status2 = 0;
        
        try {
			DataBaseClass.createConnection();
			email_redundancy_flag = checkSellerEmailRedundancy(reg.getEmail());
	        user_redundancy_flag = checkSellerLoginNameRedundancy(reg.getUsername());
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        
        if(!user_redundancy_flag && !email_redundancy_flag){
        	try {
                DataBaseClass.connection.setAutoCommit(false);

                status1 = insertSellerInfoIntoDb(reg);
                status2 = insertSellerCredentialsIntoDb(reg);

                DataBaseClass.connection.commit();
                DataBaseClass.connection.setAutoCommit(true);
            }
            catch (SQLException s){
//                System.out.println("Unexpected error occurred. Please try later.");
                try {
    				DataBaseClass.connection.rollback();
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
                s.printStackTrace();
            }
        }
 
        try {
			DataBaseClass.terminateConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        if((status1>0)&&(status2>0)){
            return true;
        }

        else{
            return false;
        }
    }
	
}
