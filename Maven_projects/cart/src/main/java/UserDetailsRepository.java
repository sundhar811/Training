//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDetailsRepository {
	
	//-----------------------------GET User Details-----------------------------
	
	public static ResultSet fetchUserCredentials(int userId) throws SQLException{
        String query = "SELECT loginname, password FROM user_credentials WHERE uid = "+userId;

        PreparedStatement ps =  DataBaseClass.connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static ResultSet fetchUserInfo(int userId) throws SQLException{
        String query = "SELECT uname, umail FROM users WHERE uid = "+userId;

        PreparedStatement ps =  DataBaseClass.connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        return rs;
    }
	
	public static UserDetail getUserDetails(int user_id) {
        String uname = null;
        String pwd = null;
        String name = null;
        String email = null;
        ResultSet rs;

        UserDetail user = new UserDetail();

        try {
        	
			DataBaseClass.createConnection();
			rs = fetchUserCredentials(user_id);

	        if(rs.isBeforeFirst()){
	            rs.next();

	            uname = rs.getString(1);
	            pwd = rs.getString(2);
	        }
	        else{
	            System.out.println("Error fetching user credentials from DB");
	        }

	        rs.close();

	        rs = fetchUserInfo(user_id);

	        if(rs.isBeforeFirst()){
	            rs.next();

	            name = rs.getString(1);
	            email = rs.getString(2);
	        }
	        else{
	            System.out.println("Error fetching user details from DB");
	        }

	        rs.close();

	        user.setId(user_id);
	        user.setUsername(uname);
	        user.setPassword(pwd);
	        user.setName(name);
	        user.setEmail(email);

//	        UserPlan check_user = user.getUser();
//	        System.out.println(check_user.toString());

	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return user;
    }

	//-----------------------------UPDATE user details---------------------------
	
	public static int updateUserCredentialsIntoDb(UserDetail user) throws SQLException{
        int rows_affected;
        String query = "UPDATE user_credentials SET password = ? WHERE uid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setString(1, user.getPassword());
        ps.setInt(2, user.getId());

        rows_affected = ps.executeUpdate();

//        System.out.println("USER PASSWORD UPDATE IS SUCCESSFUL");

        return rows_affected;
    }

    public static int updateUserInfoIntoDb(UserDetail user) throws SQLException{
        int rows_affected;
        String query = "UPDATE users SET uname = ? WHERE uid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setString(1, user.getName());
        ps.setInt(2, user.getId());

        rows_affected = ps.executeUpdate();

//        System.out.println("USER NAME UPDATE IS SUCCESSFUL");

        return rows_affected;
    }
	
	public static boolean userDetailsUpdate(UserDetail user){
        int status1 = 0, status2 = 0;
      	
    	try {
    		
			DataBaseClass.createConnection();

            if(user.getName() != null){
            	status1 = updateUserInfoIntoDb(user);
            }
            if(user.getPassword() != null){
            	status2 = updateUserCredentialsIntoDb(user);
            }
            DataBaseClass.terminateConnection();
        }
        catch (SQLException | ClassNotFoundException s){
//                System.out.println("Unexpected error occurred. Please try later.");
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
