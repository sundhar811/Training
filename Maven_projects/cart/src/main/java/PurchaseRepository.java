//$Id$
package com.shopping.cart;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PurchaseRepository {
	static boolean checkAvailabilityInFinalCart(int userid) throws SQLException{
		
		String query = "SELECT c.pid, c.qty, p.pname, p.stock FROM current_cart AS c " +
                "INNER JOIN product AS p WHERE c.uid="+userid+" AND c.pid=p.pid AND c.qty>p.stock";

        Statement stmt = DataBaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        if(rs.isBeforeFirst()){
            return false;
        }
        return true;        
    }
	
	public static float computeTotalPriceOfCart(int userid) throws SQLException, ClassNotFoundException{
        float total;

        String query = "SELECT SUM(price) FROM current_cart WHERE uid="+userid;

        Statement stmt = DataBaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        if(rs.next()){
            total = rs.getFloat(1);
        }
        else {
            total = 0;
            //System.out.println("No products available to display price.");
        }
        rs.close();
        return total;
    }
	
	public static int insertIntoBill(Purchase purchase) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO bill(auid, uid, totalprice, mode) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

        ps.setInt(1, purchase.getUserAddressId());
        ps.setInt(2, purchase.getUserId());
        ps.setFloat(3, purchase.getTotalPrice());
        ps.setString(4, purchase.getModeOfPayment());

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }
	
	public static int getLastGeneratedBillId(int id) throws SQLException{
        String query = "SELECT bid FROM bill WHERE uid = "+id+" ORDER BY bid DESC LIMIT 1";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        rs.next();
        int bill_id = rs.getInt(1);
        rs.close();

        return bill_id;
    }
	
	public static int insertIntoOrderHistory(OrderHistory order) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO order_history VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

        ps.setInt(1, order.getBillId());
        ps.setInt(2, order.getUserId());
        ps.setInt(3, order.getProductId());
        ps.setInt(4, order.getProductQuantity());
        ps.setFloat(5, order.getProductPrice());

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }
	
	public static int updateProductStock(int pid, int stock) throws SQLException{
        int rows_affected;
        String query = "UPDATE product SET stock = ? WHERE pid = ?";

        PreparedStatement ps = DataBaseClass.connection.prepareStatement(query);

        ps.setInt(1, stock);
        ps.setInt(2, pid);

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }
	
	public static ResultSet fetchTimeStampOfCurrentBill(Purchase purchase) throws SQLException{
        String query = "SELECT ts FROM bill WHERE uid = "+
                purchase.getUserId()+" AND bid = "+purchase.getBillId();

        Statement stmt = DataBaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }
	
	public static Purchase finalizePurchase(int id, int auid){
//        int auid;
		Purchase purchase = new Purchase();
		boolean availability = true;
        float total_price;
        String mode_of_payment = "Cash";

        try {
			DataBaseClass.createConnection();
			
			availability = checkAvailabilityInFinalCart(id);
			
			if(!availability){
				return new Purchase();
			}
//	        auid = selectAddress(user);
	        total_price = computeTotalPriceOfCart(id);

	        purchase.setUserId(id);
	        purchase.setUserAddressId(auid);
	        purchase.setModeOfPayment(mode_of_payment);
	        purchase.setTotalPrice(total_price);
	        

	        try{
	        	
	            DataBaseClass.connection.setAutoCommit(false);
	
	            int status = insertIntoBill(purchase);
	            if(status<=0){
	                System.out.println("Error while inserting into bill table.");
	            }
	            purchase.setBillId(getLastGeneratedBillId(id));

	            List<Cart> cart_products = CartRepository.displayUserCart(id);
	
	            for(Cart product: cart_products){
	                OrderHistory order = new OrderHistory();
	                order.setProductId(product.getProductId());
	                order.setProductQuantity(product.getProductQuantity());
	                order.setProductPrice(product.getProductPrice());
	                order.setBillId(purchase.getBillId());
	                order.setUserId(id);
	                System.out.println(order);
	                int status1 = insertIntoOrderHistory(order);
	                int status2 = CartRepository.removeProduct(id, product.getProductId());
	                int new_stock = SellerProductRepository.getProductStock(order.getProductId()) - order.getProductQuantity();
	                int status3 = updateProductStock(order.getProductId(), new_stock);
	                
	                System.out.println("1");
	                
	                if((status1<0)||(status2<0)||(status3<0)){
	                    System.out.println("Error while updating the tables.");
	                }
	            }
	
	            DataBaseClass.connection.commit();
	            DataBaseClass.connection.setAutoCommit(true);
	
	        }catch (SQLException s){
	            System.out.println("Unexpected error occurred. Please try later.");
	            DataBaseClass.connection.rollback();
	            s.printStackTrace();
	        }
	
	        ResultSet rs = fetchTimeStampOfCurrentBill(purchase);
	        if(rs.next()){
	            purchase.setTimestamp(rs.getTimestamp(1));
	        }
	        else {
	            System.out.println("No timestamp fetched.");
	        }
	        rs.close();
	
	        DataBaseClass.terminateConnection();
	        
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return purchase;
    }
}
