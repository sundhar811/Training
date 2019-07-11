//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WishListRepository {
	public static List<WishList> displayWishList(int user_id){
		List<WishList> wishLists = new ArrayList<>();
		String query = "SELECT w.pid, p.pname FROM wishlist AS w INNER JOIN " +
                "product AS p WHERE w.pid=p.pid AND w.uid = "+user_id;

        try {
			DataBaseClass.createConnection();
			
			Statement stmt = DataBaseClass.connection.createStatement();
	        ResultSet rs = stmt.executeQuery(query);
	        
	        while (rs.next()) {
				WishList wlist = new WishList();
				wlist.setUserId(user_id);
				wlist.setProductId(rs.getInt(1));
				wlist.setProductName(rs.getString(2));
				
				wishLists.add(wlist);
			}
	        
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
        return wishLists;
    }
	
	public static boolean checkProductId(int product_id) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM product WHERE pid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setInt(1, product_id);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            //product exists.
            return true;
        }

        System.out.println("Product doesn't exists.");
        return false;
    }
	
	public static boolean checkProductDuplicate(WishList wishlist, boolean flag) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM wishlist WHERE uid = ? AND pid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
        ps.setInt(1, wishlist.getUserId());
        ps.setInt(2, wishlist.getProductId());

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            if(flag){
                System.out.println("Product already exists in wish list.");
            }
            rs.close();
            return true;
        }

        if(!flag){
            System.out.println("Product doesn't exists in wish list.");
        }

        rs.close();
        return false;
    }
	
	public static boolean insertItems(WishList wishList) {
        int rows_affected = 0;
        String query = "INSERT INTO wishlist(uid, pid) VALUES(?, ?)";

        try {
			DataBaseClass.createConnection();
			
			if(checkProductId(wishList.getProductId()) &&(!checkProductDuplicate(wishList, true))){
				PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
				ps.setInt(1, wishList.getUserId());
		        ps.setInt(2, wishList.getProductId());
		        
		        rows_affected = ps.executeUpdate();
			}
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

	public static boolean deleteItem(WishList wishList) {
		int rows_affected = 0;
		String query = "DELETE FROM wishlist WHERE uid=? AND pid=?";

        try {
			DataBaseClass.createConnection();
			
			if(checkProductDuplicate(wishList, false)){
				PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);
				ps.setInt(1, wishList.getUserId());
		        ps.setInt(2, wishList.getProductId());
		        
		        rows_affected = ps.executeUpdate();
			}
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
