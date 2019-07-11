package cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;

public class CartClass {
    static void displayCart(int userid) throws SQLException, ClassNotFoundException {

        String query = "SELECT c.pid, p.pname, c.qty, c.price FROM current_cart as c " +
                "INNER JOIN product as p WHERE c.pid=p.pid AND c.uid="+userid;

        DatabaseClass.createConnection();

        Statement stmt = DatabaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        System.out.println("==================================USER CART===================================");
        if(rs.isBeforeFirst()){
            System.out.printf("%5s %-50s %10s %10s\n","PR.ID", "PRODUCT NAME", "QUANTITY", "PRICE");
        }
        else{
            System.out.println("No items in the cart.");
        }

        while(rs.next()){
            System.out.printf("%5s %-50s %10s %10s\n", rs.getInt(1), rs.getString(2),
                    rs.getInt(3), rs.getDouble(4));
        }

        System.out.println("CURRENT CART PRICE: "+computeTotalPriceOfCart(userid));

        rs.close();
        DatabaseClass.terminateConnection();
    }

    static float computeTotalPriceOfCart(int userid) throws SQLException, ClassNotFoundException{
        float total;

        String query = "SELECT SUM(price) FROM current_cart WHERE uid="+userid;

        DatabaseClass.createConnection();

        Statement stmt = DatabaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        if(rs.next()){
            total = rs.getFloat(1);
        }
        else {
            total = 0;
            System.out.println("No product in cart for the user.");
        }
        rs.close();
        DatabaseClass.terminateConnection();
        return total;
    }

    static int getCurrentQuantityFromCart(int uid, int pid) throws SQLException, ClassNotFoundException{
        String query = "SELECT qty price FROM current_cart WHERE uid = ? AND pid = ?";

        int current_qty = 0;

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, uid);
        ps.setInt(2, pid);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            current_qty = rs.getInt(1);
        }
        else{
            System.out.println("Can't resolve quantity of given item.");
        }

        rs.close();
        DatabaseClass.terminateConnection();
        return current_qty;
    }

    static boolean checkProduct(int product_id, int stock) throws SQLException, ClassNotFoundException{
        if (stock <= 0) {
            System.out.println("Required quantity can't be negative or zero.");
            return false;
        }

        String query = "SELECT stock FROM product WHERE pid = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, product_id);

        ResultSet rs = ps.executeQuery();

        /*int cur_stock = 0;
        if(!flag){
            cur_stock = getCurrentQuantityFromCart(user_id, product_id);
        }*/

        if(rs.next()){
            int db_stock = rs.getInt(1);

            if(db_stock>=(stock)){
                //product stock available
                rs.close();
                DatabaseClass.terminateConnection();
                return true;
            }

            rs.close();
            System.out.println("Stock not available.");
            DatabaseClass.terminateConnection();
            return false;
        }

        System.out.println("Product doesn't exists.");
        rs.close();
        DatabaseClass.terminateConnection();
        return false;
    }

    static boolean checkCartDuplicates(int uid, int pid, boolean flag) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM current_cart WHERE uid = ? AND pid = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, uid);
        ps.setInt(2, pid);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            if(!flag){
                System.out.println("Product exists in cart.");
            }
            rs.close();
            DatabaseClass.terminateConnection();
            return true;
        }

        if(flag){
            System.out.println("Product doesn't exists in cart.");
        }

        rs.close();
        DatabaseClass.terminateConnection();
        return false;

    }

    static float computePrice(int pid, int stock) throws SQLException, ClassNotFoundException{
        float cprice;

        String query = "SELECT price FROM product WHERE pid = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, pid);

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            cprice = rs.getFloat(1);
            rs.close();
            DatabaseClass.terminateConnection();
            return  cprice*(float)stock;
        }
        else {
            System.out.println("Error while retrieving price. Please try again.");
            DatabaseClass.terminateConnection();
            return 0;
        }
    }

    static boolean isStockPositive(int number){
        if(number>0){
            return true;
        }
        else{
            System.out.println("Stock cannot be zero or negative");
            return false;
        }
    }

    //flag - true: add product to cart; false: modify products in cart
    static void getInput(int user_id, boolean flag) throws IOException, ClassNotFoundException, SQLException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int pr_id, stock;

        do{
            System.out.print("Enter product ID: ");
            pr_id = Integer.parseInt(br.readLine());

            do{
                System.out.print("Enter required number: ");
                stock = Integer.parseInt(br.readLine());
            }while(!isStockPositive(stock));

        }while (!(checkProduct(pr_id, stock)));

        //System.out.println("Input got successfully");

        if(flag){
            //Add product
            addProductsToCart(user_id, pr_id, stock);
        }
        else {
            //modify products
            modifyProductInCart(user_id, pr_id, stock, true);
        }
    }

    static void addProductsToCart(int user_id, int pr_id, int stock) throws ClassNotFoundException, SQLException {
        float cumulative_price = computePrice(pr_id, stock);

        if(checkCartDuplicates(user_id, pr_id, false)){
            modifyProductInCart(user_id, pr_id, stock, false);
            return;
        }

        String query = "INSERT INTO current_cart(uid, pid, qty, price) VALUES(?, ?, ?, ?)";

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, user_id);
        ps.setInt(2, pr_id);
        ps.setInt(3, stock);
        ps.setFloat(4, cumulative_price);
        ps.executeUpdate();

        DatabaseClass.terminateConnection();

        System.out.println("Product added to cart successfully!!");

        displayCart(user_id);
    }

    static void modifyProductInCart(int uid, int pid, int qty, boolean flag) throws ClassNotFoundException, SQLException{
        String query = "UPDATE current_cart SET uid = ?, pid = ?, qty = ?, price = ? WHERE uid = ? AND pid = ?";
        if (!flag){
            qty = qty+getCurrentQuantityFromCart(uid, pid);
        }
        //int total_stock = qty+getCurrentQuantityFromCart(uid, pid);
        float cumulative_price = computePrice(pid, qty);
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, uid);
        ps.setInt(2, pid);
        ps.setInt(3, qty);
        ps.setFloat(4, cumulative_price);
        ps.setInt(5, uid);
        ps.setInt(6, pid);
        ps.executeUpdate();

        DatabaseClass.terminateConnection();

        System.out.println("Product modified in cart successfully!!");

        displayCart(uid);
    }

    static void removeProductFromCart(int user_id) throws ClassNotFoundException, IOException, SQLException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int pr_id;

        do{
            System.out.print("Enter product ID: ");
            pr_id = Integer.parseInt(br.readLine());
        }while (!(checkCartDuplicates(user_id, pr_id, true)));

        String query = "DELETE FROM current_cart WHERE uid=? AND pid=?";

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, user_id);
        ps.setInt(2, pr_id);

        ps.executeUpdate();

        DatabaseClass.terminateConnection();

        System.out.println("Product deleted from cart successfully!!");

        displayCart(user_id);
    }

    static void checkAvailabilityInFinalCart(int user_id) throws SQLException, ClassNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        boolean check_not_availability = false;

        String query = "SELECT c.pid, c.qty, p.pname, p.stock FROM current_cart AS c " +
                "INNER JOIN product AS p WHERE c.uid=51 AND c.pid=p.pid AND c.qty>p.stock";

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        if(rs.isBeforeFirst()){
            check_not_availability = true;
            System.out.println("=============================UNAVAILABLE ITEMS================================");

            System.out.printf("%6s %-50s %13s %15s\n", "PR.ID", "PRODUCT NAME",
                    "USER QUANTITY", "AVAILABLE STOCK");
        }

        if(check_not_availability){
            while(rs.next()){
                int product_id = rs.getInt(1);
                int user_stock = rs.getInt(2);
                int available_stock = rs.getInt(4);
                String product_name = rs.getString(3);
                System.out.printf("%6s %-50s %13s %15s\n", product_id, product_name, user_stock, available_stock);

                System.out.println("This item in your cart is not available for purchase. Please modify your order.\n" +
                        "1.Purchase "+available_stock+" of "+product_name+".\n2.Remove product from cart.");


                String choice;

                do{
                    System.out.println("Enter your choice: ");
                    choice = br.readLine();

                    if(choice.equals("1")){
                        String new_query = "UPDATE current_cart SET qty = ? WHERE uid = ? AND pid = ?";

                        PreparedStatement new_ps = DatabaseClass.connection.prepareStatement(new_query);
                        new_ps.setInt(1, available_stock);
                        new_ps.setInt(2, user_id);
                        new_ps.setInt(3, product_id);

                        new_ps.executeUpdate();
                    }
                    else if(choice.equals("2")){
                        String new_query = "DELETE FROM current_cart WHERE uid = ? AND pid = ?";

                        PreparedStatement new_ps = DatabaseClass.connection.prepareStatement(new_query);
                        new_ps.setInt(1, user_id);
                        new_ps.setInt(2, product_id);

                        new_ps.executeUpdate();
                    }
                    else{
                        System.out.println("Enter a vaild choice.");
                    }

                }while(!(choice.equals("1")||choice.equals("2")));
            }
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }

    static int selectAddress(int user_id) throws SQLException, ClassNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int addr_uid, display_counter, final_auid;
        HashMap<Integer, Integer> hm = new HashMap<>();

        String query = "SELECT * FROM user_details WHERE uid="+user_id;

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        final_auid = -1;
        display_counter=0;

        System.out.println("================================USER ADDRESS==================================");

        System.out.printf("%6s %-30s %8s %12s\n", "Option", "Address",
                "Pincode", "Phone Number");

        while(rs.next()){
            addr_uid = rs.getInt(1);
            System.out.printf("%6s %-30s %8s %12s\n", ++display_counter, rs.getString(4),
                    rs.getInt(5), rs.getLong(3));
            hm.put(display_counter, addr_uid);
        }

        rs.close();
        DatabaseClass.terminateConnection();

        do{
            System.out.print("Choose an address: ");
            int choice = Integer.parseInt(br.readLine());

        /*
        Iterator<Integer> itr = hm.keySet().iterator();
        while(itr.hasNext()){
            temp_key = itr.next();
            if(temp_key==choice){
                final_auid = hm.get(temp_key);
            }
        }*/

            final_auid = hm.get(choice);

            if(final_auid<0){
                System.out.println("Please select a valid address.");
            }
        }while(final_auid<0);


        hm.clear();
        return final_auid;
    }

    static void finalizePurchase(int user_id) throws ClassNotFoundException, SQLException, IOException{
        int auid;
        float total_price;
        String mode_of_payment = "Cash";
        String query;
        String new_query;
        int bill_id = -1;
        PreparedStatement ps;
        PreparedStatement new_ps;
        ResultSet rs;

        checkAvailabilityInFinalCart(user_id);
        auid = selectAddress(user_id);
        total_price = computeTotalPriceOfCart(user_id);

        DatabaseClass.createConnection();

        try{
            DatabaseClass.connection.setAutoCommit(false);

            query = "INSERT INTO bill(auid, uid, totalprice, mode) VALUES (?, ?, ?, ?)";

            ps = DatabaseClass.connection.prepareStatement(query);

            ps.setInt(1, auid);
            ps.setInt(2, user_id);
            ps.setFloat(3, total_price);
            ps.setString(4, mode_of_payment);

            ps.executeUpdate();

            query = "SELECT bid FROM bill WHERE uid = "+user_id+" ORDER BY bid DESC LIMIT 1";

            ps = DatabaseClass.connection.prepareStatement(query);

            rs = ps.executeQuery();
            rs.next();
            bill_id = rs.getInt(1);
            rs.close();

            query = "SELECT pid, qty, price FROM current_cart WHERE uid="+user_id;

            ps = DatabaseClass.connection.prepareStatement(query);

            rs = ps.executeQuery();

            while (rs.next()){
                int pid = rs.getInt(1);
                int qty = rs.getInt(2);
                float price = rs.getFloat(3);

                new_query = "INSERT INTO order_history VALUES (?, ?, ?, ?, ?)";

                new_ps = DatabaseClass.connection.prepareStatement(new_query);
                new_ps.setInt(1, bill_id);
                new_ps.setInt(2, user_id);
                new_ps.setInt(3, pid);
                new_ps.setInt(4, qty);
                new_ps.setFloat(5, price);

                new_ps.executeUpdate();

                new_query = "DELETE FROM current_cart WHERE uid="+user_id+" AND pid="+pid;

                new_ps = DatabaseClass.connection.prepareStatement(new_query);
                new_ps.executeUpdate();

                new_query = "UPDATE product SET stock = stock - "+qty+" WHERE pid = "+pid;

                new_ps = DatabaseClass.connection.prepareStatement(new_query);
                new_ps.executeUpdate();
            }

            rs.close();

            DatabaseClass.connection.commit();
            DatabaseClass.connection.setAutoCommit(true);

        }catch (SQLException s){
            System.out.println("Unexpected error occurred. Please try later.");
            DatabaseClass.connection.rollback();
            s.printStackTrace();
        }

        query = "SELECT auid, totalprice, mode, ts FROM bill WHERE uid = "+user_id+" AND bid = "+bill_id;

        ps = DatabaseClass.connection.prepareStatement(query);
        rs = ps.executeQuery();

        if(rs.next()){
            System.out.println("==================================YOUR BILL===================================");

            System.out.printf("%7s %7s %10s %11s %-12s %-20s\n", "BILL_ID", "USER_ID", "Address_ID",
                    "TOTAL_PRICE", "PAYMENT_MODE", "TIME");

            System.out.printf("%7s %7s %10s %11s %-12s %-20s\n", bill_id, user_id, rs.getInt(1),
                    rs.getFloat(2), rs.getString(3), rs.getTimestamp(4));

        }
        else{
            System.out.println("There is some error.");
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }
}
