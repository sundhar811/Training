//$Id$
package com.shopping.cart;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryRepository {
	
	public static List<Purchase> showOrderHistory(int id){
        ArrayList<Purchase> purchases = new ArrayList<>();
        
        try {
			DataBaseClass.createConnection();
			
			String query = "SELECT b.bid, u.addr, u.pincode, u.p_no, b.totalprice, b.mode, b.ts, u.auid FROM" +
	                " bill AS b INNER JOIN user_details AS u WHERE b.uid ="+id+" AND b.auid=u.auid;";

	        Statement stmt = DataBaseClass.connection.createStatement();

	        ResultSet rs = stmt.executeQuery(query);

	        if(rs.isBeforeFirst()){
	            while (rs.next()){
	                Purchase purchase = new Purchase();
	                purchase.setBillId(rs.getInt(1));
	                purchase.setTotalPrice(rs.getFloat(5));
	                purchase.setModeOfPayment(rs.getString(6));
	                purchase.setTimestamp(rs.getTimestamp(7));
	                purchase.setUserId(id);
	                purchase.setUserAddressId(rs.getInt(8));

	                UserAddress userAddress = new UserAddress();
	                userAddress.setUserAddress(rs.getString(2));
	                userAddress.setUserPinCode(rs.getInt(3));
	                userAddress.setPhoneNumber(rs.getLong(4));

	                purchase.setUserAddress(userAddress);
	                
	                purchases.add(purchase);
	            }
	        }
	        else{
	            System.out.println("No purchase history.");
	        }
	        rs.close();
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return purchases;
    }
	
	public static List<OrderHistory> getPurchaseDetails(int id, int bid){
		List<OrderHistory> orders = new ArrayList<>();
		try {
			DataBaseClass.createConnection();
			String query = "SELECT o.bid, o.uid, o.pid, o.qty, o.price, p.pname FROM order_history AS o"
					+ " INNER JOIN product AS p WHERE p.pid = o.pid AND o.uid= "+id+" AND o.bid = "+bid;

	        Statement stmt = DataBaseClass.connection.createStatement();

	        ResultSet rs = stmt.executeQuery(query);
	        
	        if(rs.isBeforeFirst()){
	        	while(rs.next()){
	        		OrderHistory oHistory = new OrderHistory();
		        	oHistory.setBillId(rs.getInt(1));
		        	oHistory.setUserId(rs.getInt(2));
		        	oHistory.setProductId(rs.getInt(3));
		        	oHistory.setProductQuantity(rs.getInt(4));
		        	oHistory.setProductPrice(rs.getFloat(5));
		        	
		        	Product product = new Product();
		        	product.setProductName(rs.getString(6));
		        	oHistory.setProduct(product);
		        	
		        	orders.add(oHistory);
	        	}
	        	rs.close();
	        }
	        DataBaseClass.terminateConnection();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return orders;
	}
}
