//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginRepository {

    //---------------------------USER LOGIN-------------------------------------

    public static int checkUserCredentials(Login login_user) throws ClassNotFoundException, SQLException {
    	int id = -1;
        String query = "SELECT uid FROM user_credentials WHERE loginname = ? AND password = ?";
        DataBaseClass.createConnection();

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setString(1, login_user.getUsername());
        ps.setString(2, login_user.getPassword());

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
        	id = rs.getInt("uid");
        }
        
        rs.close();
        DataBaseClass.terminateConnection();
        
        return id;
    }
    
	//---------------------------SELLER LOGIN-------------------------------------

    public static int checkSellerCredentials(Login login_seller) throws ClassNotFoundException, SQLException {
        int id = -1;
        String query = "SELECT sid FROM seller_credentials WHERE loginname = ? AND password = ?";
        DataBaseClass.createConnection();

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setString(1, login_seller.getUsername());
        ps.setString(2, login_seller.getPassword());

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            id = rs.getInt("sid");
        }

        rs.close();
        DataBaseClass.terminateConnection();

        return id;
    }

}
