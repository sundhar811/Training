package cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WishlistClass {
    static void viewWishList(int user_id) throws SQLException, ClassNotFoundException {
        String query = "SELECT w.pid, p.pname FROM wishlist AS w INNER JOIN " +
                "product AS p WHERE w.pid=p.pid AND w.uid = "+user_id;

        DatabaseClass.createConnection();

        Statement stmt = DatabaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        System.out.println("==================================WISH LIST===================================");
        if(rs.isBeforeFirst()){
            System.out.printf("%5s %-50s\n","PR.ID", "PRODUCT NAME");
        }
        else{
            System.out.println("No items in wish list.");
        }

        while(rs.next()){
            System.out.printf("%5s %-50s\n", rs.getInt(1), rs.getString(2));
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }

    static boolean checkProductId(int product_id) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM product WHERE pid = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, product_id);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            //product exists.
            DatabaseClass.terminateConnection();
            return true;
        }

        System.out.println("Product doesn't exists.");
        DatabaseClass.terminateConnection();
        return false;
    }

    // flag - true: add product; false - remove product
    static boolean checkProductDuplicate(int uid, int pid, boolean flag) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM wishlist WHERE uid = ? AND pid = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, uid);
        ps.setInt(2, pid);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            if(flag){
                System.out.println("Product already exists in wish list.");
            }
            rs.close();
            DatabaseClass.terminateConnection();
            return true;
        }

        if(!flag){
            System.out.println("Product doesn't exists in wish list.");
        }

        rs.close();
        DatabaseClass.terminateConnection();
        return false;
    }

    static void addProduct(int user_id) throws IOException, SQLException, ClassNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int pr_id;

        do{
            System.out.print("Enter product ID: ");
            pr_id = Integer.parseInt(br.readLine());
        }while(!checkProductId(pr_id));

        if(checkProductDuplicate(user_id, pr_id, true)){
            return;
        }

        String query = "INSERT INTO wishlist(uid, pid) VALUES(?, ?)";

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, user_id);
        ps.setInt(2, pr_id);
        ps.executeUpdate();

        DatabaseClass.terminateConnection();

        System.out.println("Product added to wish list successfully!!");

        viewWishList(user_id);
    }

    static void deleteProduct(int user_id) throws IOException, SQLException, ClassNotFoundException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int pr_id;

        do{
            System.out.print("Enter product ID: ");
            pr_id = Integer.parseInt(br.readLine());
        }while (!(checkProductDuplicate(user_id, pr_id, false)));

        String query = "DELETE FROM wishlist WHERE uid=? AND pid=?";

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, user_id);
        ps.setInt(2, pr_id);

        ps.executeUpdate();

        DatabaseClass.terminateConnection();

        System.out.println("Product deleted from wish list successfully!!");

        viewWishList(user_id);
    }
}
