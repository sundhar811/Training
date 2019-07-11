package cart;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductClass {
    static void displayProduct() throws SQLException, ClassNotFoundException {
        System.out.println("================================PRODUCT LIST==================================");
        String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0";

        DatabaseClass.createConnection();

        Statement stmt = DatabaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        System.out.printf("%5s %-50s %5s %10s %12s %-15s\n", "PR.ID", "PRODUCT_NAME", "STOCK", "PRICE", "AVG. RATING", "SELLER_NAME");

        while(rs.next()){
            System.out.printf("%5s %-50s %5s %10s %12s %-15s\n", rs.getInt(1),
                    rs.getString(2), rs.getInt(3), rs.getDouble(4),
                    rs.getDouble(5), rs.getString(6));
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }

    static void displayProductByName() throws SQLException, ClassNotFoundException {
        System.out.println("================================PRODUCT LIST==================================");
        String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0 ORDER BY p.pname";

        DatabaseClass.createConnection();

        Statement stmt = DatabaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        System.out.printf("%5s %-50s %5s %10s %12s %-15s\n", "PR.ID", "PRODUCT_NAME", "STOCK", "PRICE", "AVG. RATING", "SELLER_NAME");

        while(rs.next()){
            System.out.printf("%5s %-50s %5s %10s %12s %-15s\n", rs.getInt(1),
                    rs.getString(2), rs.getInt(3), rs.getDouble(4),
                    rs.getDouble(5), rs.getString(6));
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }

    static void displayProductByPrice() throws SQLException, ClassNotFoundException {
        System.out.println("================================PRODUCT LIST==================================");
        String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0 ORDER BY p.price";

        DatabaseClass.createConnection();

        Statement stmt = DatabaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        System.out.printf("%5s %-50s %5s %10s %12s %-15s\n", "PR.ID", "PRODUCT_NAME", "STOCK", "PRICE", "AVG. RATING", "SELLER_NAME");

        while(rs.next()){
            System.out.printf("%5s %-50s %5s %10s %12s %-15s\n", rs.getInt(1),
                    rs.getString(2), rs.getInt(3), rs.getDouble(4),
                    rs.getDouble(5), rs.getString(6));
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }

    static void displayNewProductForSeller(int seller_id) throws SQLException, ClassNotFoundException {
        System.out.println("============================CURRENT PRODUCT LIST==============================");
        String query = "SELECT pid, pname, stock, price, avg_rating FROM product WHERE sid = "+seller_id+" AND stock>0";

        DatabaseClass.createConnection();

        Statement stmt = DatabaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        System.out.printf("%5s %-50s %5s %10s %12s\n", "PR.ID", "PRODUCT_NAME", "STOCK", "PRICE", "AVG. RATING");

        while(rs.next()){

            System.out.printf("%5s %-50s %5s %10s %12s\n", rs.getInt(1),
                    rs.getString(2), rs.getInt(3), rs.getDouble(4),
                    rs.getDouble(5));
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }

    static void displayOldProductForSeller(int seller_id) throws SQLException, ClassNotFoundException {
        String query = "SELECT pid, pname, stock, price, avg_rating FROM product WHERE sid = "+seller_id+" AND stock = 0";

        DatabaseClass.createConnection();

        Statement stmt = DatabaseClass.connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        if(rs.isBeforeFirst()){
            System.out.println("============================UNLISTED PRODUCT LIST=============================");
            System.out.printf("%5s %-50s %5s %10s %12s\n", "PR.ID", "PRODUCT_NAME", "STOCK", "PRICE", "AVG. RATING");
        }
        else{
            System.out.println("You don't have any unlisted products.");
        }

        while(rs.next()){
            System.out.printf("%5s %-50s %5s %10s %12s\n", rs.getInt(1),
                    rs.getString(2), rs.getInt(3), rs.getDouble(4),
                    rs.getDouble(5));
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }

    static boolean isProductNameNull(@NotNull String pname){
        if(pname.isEmpty()){
            System.out.println("Product name cannot be null.");
            return true;
        }
        else{
            return false;
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

    static boolean isPricePositive(float number){
        if(number>=0){
            return true;
        }
        else{
            System.out.println("Price cannot be negative");
            return false;
        }
    }

    static String getProductName(int pid) throws SQLException, ClassNotFoundException{
        PreparedStatement ps;
        String query;
        String pname;

        query = "SELECT pname FROM product WHERE pid = "+pid;

        DatabaseClass.createConnection();
        ps = DatabaseClass.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            pname = rs.getString(1);
        }
        else{
            pname = "NO PRODUCTS RETURNED IN DB QUERY";
        }
        rs.close();
        DatabaseClass.terminateConnection();
        return pname;
    }

    static int getProductStock(int pid) throws SQLException, ClassNotFoundException{
        PreparedStatement ps;
        String query;
        int stock;

        query = "SELECT stock FROM product WHERE pid = "+pid;

        DatabaseClass.createConnection();
        ps = DatabaseClass.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            stock = rs.getInt(1);
        }
        else{
            stock = -1;
        }
        rs.close();
        DatabaseClass.terminateConnection();
        return stock;
    }

    static float getProductPrice(int pid) throws SQLException, ClassNotFoundException{
        PreparedStatement ps;
        String query;
        float price;

        query = "SELECT price FROM product WHERE pid = "+pid;

        DatabaseClass.createConnection();
        ps = DatabaseClass.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            price = rs.getFloat(1);
        }
        else{
            price = -1;
        }
        rs.close();
        DatabaseClass.terminateConnection();
        return price;
    }

    static boolean isProductAssociatedWithSeller(int sid, int pid) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM product WHERE sid = ? AND pid = ?";

        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, sid);
        ps.setInt(2, pid);

        ResultSet rs = ps.executeQuery();

        if(rs.isBeforeFirst()){
            rs.close();
            DatabaseClass.terminateConnection();
            return true;
        }

        else {
            System.out.println("The entered product is not associated with you.");
            rs.close();
            DatabaseClass.terminateConnection();
            return false;
        }
    }

    static void addNewProducts(int seller_id) throws SQLException, ClassNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice;
        String query;
        String pname;
        int stock;
        float price;
        PreparedStatement ps;

        DatabaseClass.createConnection();

        query = "INSERT INTO product(sid, pname, stock, price) VALUES(?, ?, ?, ?)";

        do{
            do{
                System.out.print("Enter product name: ");
                pname = br.readLine();
            }while(isProductNameNull(pname));

            do{
                System.out.print("Enter stock: ");
                stock = Integer.parseInt(br.readLine());
            }while(!isStockPositive(stock));

            do{
                System.out.print("Enter price: ");
                price = Float.parseFloat(br.readLine());
            }while (!isPricePositive(price));

            ps = DatabaseClass.connection.prepareStatement(query);

            ps.setInt(1, seller_id);
            ps.setString(2, pname);
            ps.setInt(3, stock);
            ps.setFloat(4, price);

            ps.executeUpdate();

            System.out.println("Add another product?: [y/n] ...");
            choice = br.readLine();

        }while (choice.equals("y"));

        DatabaseClass.terminateConnection();
    }

    static void removeProducts(int seller_id) throws SQLException, ClassNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice;
        String query;
        int pid;
        PreparedStatement ps;

        query = "UPDATE product SET stock = 0 WHERE sid = ? AND pid = ?";

        do{
            do{
                System.out.print("Enter product ID: ");
                pid = Integer.parseInt(br.readLine());
            }while(!isProductAssociatedWithSeller(seller_id, pid));

            DatabaseClass.createConnection();

            ps = DatabaseClass.connection.prepareStatement(query);

            ps.setInt(1, seller_id);
            ps.setInt(2, pid);

            ps.executeUpdate();

            DatabaseClass.terminateConnection();

            System.out.println("Remove another product?: [y/n] ...");
            choice = br.readLine();

        }while (choice.equals("y"));

    }

    static void modifyProductName(int seller_id) throws IOException, SQLException, ClassNotFoundException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int pid;
        String pname;
        String query;

        do{
            System.out.print("Enter product ID: ");
            pid = Integer.parseInt(br.readLine());
        }while(!isProductAssociatedWithSeller(seller_id, pid));

        System.out.println("Current product name: "+getProductName(pid));

        do{
            System.out.print("Enter new product name: ");
            pname = br.readLine();
        }while(isProductNameNull(pname));

        query = "UPDATE product SET pname = ? WHERE pid = ?";

        DatabaseClass.createConnection();
        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setString(1, pname);
        ps.setInt(2, pid);

        ps.executeUpdate();

        System.out.println("Name changed successfully.");

        DatabaseClass.terminateConnection();
    }

    static void modifyProductStock(int seller_id) throws IOException, SQLException, ClassNotFoundException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int pid;
        int stock;
        String query;

        do{
            System.out.print("Enter product ID: ");
            pid = Integer.parseInt(br.readLine());
        }while(!isProductAssociatedWithSeller(seller_id, pid));

        System.out.println("Current product stock: "+getProductStock(pid));

        do{
            System.out.print("Enter new product stock: ");
            stock = Integer.parseInt(br.readLine());
        }while(!isStockPositive(stock));

        query = "UPDATE product SET stock = ? WHERE pid = ?";

        DatabaseClass.createConnection();
        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setInt(1, stock);
        ps.setInt(2, pid);

        ps.executeUpdate();

        System.out.println("Stock changed successfully.");

        DatabaseClass.terminateConnection();
    }

    static void modifyProductPrice(int seller_id)  throws IOException, SQLException, ClassNotFoundException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PreparedStatement ps;
        int pid;
        float price;
        String query;

        do{
            System.out.print("Enter product ID: ");
            pid = Integer.parseInt(br.readLine());
        }while(!isProductAssociatedWithSeller(seller_id, pid));

        System.out.println("Current product price: "+getProductPrice(pid));

        do{
            System.out.print("Enter new product price: ");
            price = Float.parseFloat(br.readLine());
        }while(!isPricePositive(price));

        DatabaseClass.createConnection();

        try{
            DatabaseClass.connection.setAutoCommit(false);

            query = "UPDATE product SET price = ? WHERE pid = ?";

            ps = DatabaseClass.connection.prepareStatement(query);
            ps.setFloat(1, price);
            ps.setInt(2, pid);

            ps.executeUpdate();

            query = "UPDATE current_cart SET price = qty*? WHERE pid = ?";
            ps = DatabaseClass.connection.prepareStatement(query);
            ps.setFloat(1, price);
            ps.setInt(2, pid);

            ps.executeUpdate();

            System.out.println("Price changed successfully.");

            DatabaseClass.connection.commit();
            DatabaseClass.connection.setAutoCommit(true);
        }
        catch (SQLException s){
            System.out.println("Unexpected error occurred. Please try later.");
            DatabaseClass.connection.rollback();
            s.printStackTrace();
        }

        DatabaseClass.terminateConnection();
    }

    static void modifyProducts(int seller_id) throws IOException, SQLException, ClassNotFoundException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice;

        System.out.println("Select a function: ");
        System.out.println("1. Modify Product Name.\n2. Modify Product Stock.\n3.Modify Product Price.");
        System.out.print("Enter your choice: ");

        do{
            switch (choice = br.readLine()){
                case "1":
                    modifyProductName(seller_id);
                    break;
                case "2":
                    modifyProductStock(seller_id);
                    break;
                case "3":
                    modifyProductPrice(seller_id);
                    break;
                default:
                    System.out.println("Enter a valid choice.");
            }
        }while(!(choice.equals("1")||choice.equals("2")||choice.equals("3")));
    }

    static void productFrontPage() throws IOException, SQLException, ClassNotFoundException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice;

        do{
            System.out.println("===============================PRODUCT PAGE===============================");
            System.out.println("Select the function");
            System.out.println("1.View Products by Product ID.\n2.View Products by Product Name." +
                    "\n3.View Products by Product Price.\n4.Go Back.");
            System.out.print("Enter your choice: ");

            switch (choice = br.readLine()) {
                case "1":
                    displayProduct();
                    break;
                case "2":
                    displayProductByName();
                    break;
                case "3":
                    displayProductByPrice();
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Enter a valid choice");
            }
        }while(!choice.equals("4"));
    }
}
