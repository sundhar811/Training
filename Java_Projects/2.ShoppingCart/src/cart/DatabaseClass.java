package cart;

import java.sql.*;

public class DatabaseClass {
    public static Connection connection;

    public static void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/productdb",
                "root", "");

    }

    public static void terminateConnection() throws SQLException{
        connection.close();
    }

    //---------------------------USER LOGIN AND REGISTRATION-------------------------------------

    public static int checkUserCredentials(UserBuilderPlan login_user) throws ClassNotFoundException, SQLException {
        int id = -1;
        String query = "SELECT uid FROM user_credentials WHERE loginname = ? AND password = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, login_user.getUser().getUserName());
        ps.setString(2, login_user.getUser().getPassWord());

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            id = rs.getInt("uid");
        }

        rs.close();
        terminateConnection();

        return id;
    }

    public static ResultSet fetchUserCredentials(int userId) throws SQLException{
        String query = "SELECT loginname, password FROM user_credentials WHERE uid = "+userId;

        PreparedStatement ps = connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static ResultSet fetchUserInfo(int userId) throws SQLException{
        String query = "SELECT uname, umail FROM users WHERE uid = "+userId;

        PreparedStatement ps = connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static boolean checkUserLoginNameRedundancy(String un) throws SQLException, ClassNotFoundException{
        String query = "SELECT uid FROM user_credentials WHERE loginname = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, un);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            System.out.println("Username already exists.");
            terminateConnection();
            return true;
        }

        rs.close();
        terminateConnection();
        return false;

    }

    public static boolean checkUserEmailRedundancy(String umail) throws SQLException, ClassNotFoundException{
        String query = "SELECT uid FROM users WHERE umail = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, umail);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            System.out.println("e-mail already exists.");
            terminateConnection();
            return true;
        }

        rs.close();
        terminateConnection();
        return false;

    }

    public static int insertUserCredentialsIntoDb(UserPlan user) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO user_credentials(loginname, password) VALUES(?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, user.getUserName());
        ps.setString(2, user.getPassWord());

        rows_affected = ps.executeUpdate();

//        System.out.println("USER CREDENTIAL INSERTION IS SUCCESSFUL");

        return rows_affected;
    }

    public static int insertUserInfoIntoDb(UserPlan user) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO users(uname, umail) VALUES(?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());

        rows_affected = ps.executeUpdate();

//        System.out.println("USER INFORMATION INSERTION IS SUCCESSFUL");

        return rows_affected;
    }

    //---------------------------SELLER LOGIN AND REGISTRATION-------------------------------------

    public static int checkSellerCredentials(SellerBuilderPlan login_seller) throws ClassNotFoundException, SQLException {
        int id = -1;
        String query = "SELECT sid FROM seller_credentials WHERE loginname = ? AND password = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, login_seller.getSeller().getSellerName());
        ps.setString(2, login_seller.getSeller().getPassWord());

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            id = rs.getInt("sid");
        }

        rs.close();
        terminateConnection();

        return id;
    }

    public static ResultSet fetchSellerCredentials(int sellerId) throws SQLException{
        String query = "SELECT loginname, password FROM seller_credentials WHERE sid = "+sellerId;

        PreparedStatement ps = connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static ResultSet fetchSellerInfo(int sellerId) throws SQLException{
        String query = "SELECT sname, smail FROM sellers WHERE sid = "+sellerId;

        PreparedStatement ps = connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();

        return rs;
    }

    public static boolean checkSellerLoginNameRedundancy(String un) throws SQLException, ClassNotFoundException{
        String query = "SELECT sid FROM seller_credentials WHERE loginname = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, un);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            System.out.println("Username already exists.");
            terminateConnection();
            return true;
        }

        rs.close();
        terminateConnection();
        return false;

    }

    public static boolean checkSellerEmailRedundancy(String smail) throws SQLException, ClassNotFoundException{
        String query = "SELECT sid FROM sellers WHERE smail = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, smail);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            System.out.println("e-mail already exists.");
            terminateConnection();
            return true;
        }

        rs.close();
        terminateConnection();
        return false;

    }

    public static int insertSellerCredentialsIntoDb(SellerPlan seller) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO seller_credentials(loginname, password) VALUES(?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, seller.getSellerName());
        ps.setString(2, seller.getPassWord());

        rows_affected = ps.executeUpdate();

//        System.out.println("SELLER CREDENTIAL INSERTION IS SUCCESSFUL");

        return rows_affected;
    }

    public static int insertSellerInfoIntoDb(SellerPlan seller) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO sellers(sname, smail) VALUES(?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setString(1, seller.getName());
        ps.setString(2, seller.getEmail());

        rows_affected = ps.executeUpdate();

//        System.out.println("SELLER INFORMATION INSERTION IS SUCCESSFUL");

        return rows_affected;
    }

    //-------------------------------PRODUCT DISPLAY PAGE--------------------------------------------

    public static ResultSet displayAllProductsById() throws SQLException {
        String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    public static ResultSet displayAllProductsByName() throws SQLException {
        String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0 ORDER BY p.pname";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    public static ResultSet displayAllProductsByPrice() throws SQLException{
        String query = "SELECT p.pid, p.pname, p.stock, p.price, p.avg_rating, s.sname " +
                "FROM product as p INNER JOIN sellers as s WHERE p.sid=s.sid AND p.stock>0 ORDER BY p.price";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    //------------------------------------USER DETAILS----------------------------------------------

    public static ResultSet displayUserAddress(int user_id) throws SQLException{
        String query = "SELECT p_no, addr, pincode, auid FROM user_details WHERE uid = "+user_id;

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    public static int insertUserDetails(UserDetailsPlan user_details) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO user_details(uid, p_no, addr, pincode) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, user_details.getUserId());
        ps.setLong(2, user_details.getUserPhoneNumber());
        ps.setString(3, user_details.getUserAddress());
        ps.setInt(4, user_details.getUserPinCode());
        rows_affected = ps.executeUpdate();

//        System.out.println("USER DETAILS INSERTION IS SUCCESSFUL");

        return rows_affected;
    }

    //------------------------------------SELLER DETAILS----------------------------------------------

    public static ResultSet displaySellerAddress(int seller_id) throws SQLException{
        String query = "SELECT p_no, addr, pincode FROM seller_details WHERE sid = "+seller_id;

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    public static int insertSellerDetails(SellerDetailsPlan seller_details) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO seller_details(sid, p_no, addr, pincode) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, seller_details.getSellerId());
        ps.setLong(2, seller_details.getSellerPhoneNumber());
        ps.setString(3, seller_details.getSellerAddress());
        ps.setInt(4, seller_details.getSellerPinCode());
        rows_affected = ps.executeUpdate();

//        System.out.println("USER DETAILS INSERTION IS SUCCESSFUL");

        return rows_affected;
    }

    //------------------------------------SELLER-----------------------------------------------

    public static ResultSet displayCurrentSellerProduct(int seller_id) throws SQLException{
        String query = "SELECT pid, pname, stock, price, avg_rating FROM product WHERE " +
                "sid = "+seller_id+" AND stock>0";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    public static ResultSet displayOldSellerProduct(int seller_id) throws SQLException{
        String query = "SELECT pid, pname, stock, price, avg_rating FROM product" +
                " WHERE sid = "+seller_id+" AND stock = 0";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    //------------------------------------SELLER PRODUCT---------------------------------------

    public static int addNewProducts(ProductPlan product, int seller_id) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO product(sid, pname, stock, price) VALUES(?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, seller_id);
        ps.setString(2, product.getProductName());
        ps.setInt(3, product.getProductStock());
        ps.setFloat(4, product.getProductPrice());
        rows_affected = ps.executeUpdate();

//        System.out.println("PRODUCT DETAILS INSERTION IS SUCCESSFUL");

        return rows_affected;
    }

    public static boolean isProductAssociatedWithSeller(int sid, int pid) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM product WHERE sid = ? AND pid = ?";

        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, sid);
        ps.setInt(2, pid);

        ResultSet rs = ps.executeQuery();

        if(rs.isBeforeFirst()){
            rs.close();
            terminateConnection();
            return true;
        }

        else {
            System.out.println("The entered product is not associated with you.");
            rs.close();
            terminateConnection();
            return false;
        }
    }

    public static int deleteProducts(int seller_id, int pid) throws SQLException{
        int rows_affected;
        String query = "UPDATE product SET stock = 0 WHERE sid = ? AND pid = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, seller_id);
        ps.setInt(2, pid);

        rows_affected = ps.executeUpdate();

//        System.out.println("PRODUCT DETAILS DELETION IS SUCCESSFUL");

        return rows_affected;
    }

    public static String getProductName(int pid) throws SQLException{
        PreparedStatement ps;
        String query;
        String pname;

        query = "SELECT pname FROM product WHERE pid = "+pid;

        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            pname = rs.getString(1);
        }
        else{
            pname = "NO PRODUCTS RETURNED IN DB QUERY";
        }
        rs.close();
        return pname;
    }

    public static int updateProductName(int pid, String pname) throws SQLException{
        int rows_affected;
        String query = "UPDATE product SET pname = ? WHERE pid = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setString(1, pname);
        ps.setInt(2, pid);

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static int getProductStock(int pid) throws SQLException{
        PreparedStatement ps;
        String query;
        int stock;

        query = "SELECT stock FROM product WHERE pid = "+pid;

        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            stock = rs.getInt(1);
        }
        else{
            stock = -1;
        }
        rs.close();
        return stock;
    }

    public static int updateProductStock(int pid, int stock) throws SQLException{
        int rows_affected;
        String query = "UPDATE product SET stock = ? WHERE pid = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, stock);
        ps.setInt(2, pid);

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static float getProductPrice(int pid) throws SQLException{
        PreparedStatement ps;
        String query;
        float price;

        query = "SELECT price FROM product WHERE pid = "+pid;

        ps = connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            price = rs.getFloat(1);
        }
        else{
            price = -1;
        }
        rs.close();
        return price;
    }

    public static int updateProductPriceInProductList(int pid, float price) throws SQLException{
        int rows_affected;
        String query = "UPDATE product SET price = ? WHERE pid = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setFloat(1, price);
        ps.setInt(2, pid);

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static int updateProductPriceInUserCart(int pid, float price) throws SQLException{
        int rows_affected;
        String query = "UPDATE current_cart SET price = qty*? WHERE pid = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setFloat(1, price);
        ps.setInt(2, pid);

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    //----------------------------------------USER CART-----------------------------------------

    public static ResultSet fetchItemsInUserCart(int userid) throws SQLException{
        String query = "SELECT c.pid, p.pname, c.qty, c.price FROM current_cart as c " +
                "INNER JOIN product as p WHERE c.pid=p.pid AND c.uid="+userid;

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    public static float computeTotalPriceOfCart(int userid) throws SQLException, ClassNotFoundException{
        float total;

        String query = "SELECT SUM(price) FROM current_cart WHERE uid="+userid;

        createConnection();

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        if(rs.next()){
            total = rs.getFloat(1);
        }
        else {
            total = 0;
            //System.out.println("No products available to display price.");
        }
        rs.close();
        terminateConnection();
        return total;
    }

    public static boolean checkProductForCart(int product_id, int stock) throws SQLException, ClassNotFoundException{
//        if (stock <= 0) {
//            System.out.println("Required quantity can't be negative or zero.");
//            return false;
//        }

        String query = "SELECT stock FROM product WHERE pid = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
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

    public static float computePriceForProductInCart(int pid, int stock) throws SQLException, ClassNotFoundException{
        float cprice;

        String query = "SELECT price FROM product WHERE pid = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, pid);

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            cprice = rs.getFloat(1);
            rs.close();
            terminateConnection();
            return  cprice*(float)stock;
        }
        else {
            System.out.println("Error while retrieving price. Please try again.");
            terminateConnection();
            return 0;
        }
    }

    public static boolean checkCartDuplicates(int uid, int pid, boolean flag) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM current_cart WHERE uid = ? AND pid = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, uid);
        ps.setInt(2, pid);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            if(!flag){
                System.out.println("Product exists in cart. The entered quantity will" +
                        " be added to the existing quantity.");
            }
            rs.close();
            terminateConnection();
            return true;
        }

        if(flag){
            System.out.println("Product doesn't exists in cart.");
        }

        rs.close();
        terminateConnection();
        return false;

    }

    public static int addProductsToCart(CartPlan cart) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO current_cart(uid, pid, qty, price) VALUES(?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, cart.getUserId());
        ps.setInt(2, cart.getProductId());
        ps.setInt(3, cart.getProductQuantity());
        ps.setFloat(4, cart.getProductPrice());

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static int getCurrentQuantityFromCart(CartPlan cart) throws SQLException, ClassNotFoundException{
        String query = "SELECT qty price FROM current_cart WHERE uid = ? AND pid = ?";

        int current_qty = 0;

        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, cart.getUserId());
        ps.setInt(2, cart.getProductId());
        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            current_qty = rs.getInt(1);
        }
        else{
            System.out.println("Can't resolve quantity of given item.");
        }

        rs.close();
        terminateConnection();
        return current_qty;
    }

    public static int modifyProductInCart(CartPlan cart) throws SQLException{
        int rows_affected;
        String query = "UPDATE current_cart SET uid = ?, pid = ?, qty = ?, price = ? WHERE uid = ? AND pid = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, cart.getUserId());
        ps.setInt(2, cart.getProductId());
        ps.setInt(3, cart.getProductQuantity());
        ps.setFloat(4, cart.getProductPrice());
        ps.setInt(5, cart.getUserId());
        ps.setInt(6, cart.getProductId());
        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static int removeProductFromCart(int user_id, int pr_id) throws SQLException{
        int rows_affected;
        String query = "DELETE FROM current_cart WHERE uid=? AND pid=?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, user_id);
        ps.setInt(2, pr_id);

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static ResultSet checkAvailabilityInFinalCart(int userid) throws SQLException{
        String query = "SELECT c.pid, c.qty, p.pname, p.stock FROM current_cart AS c " +
                "INNER JOIN product AS p WHERE c.uid="+userid+" AND c.pid=p.pid AND c.qty>p.stock";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    public static int modifyProductInFinalCart(CartPlan cart, ProductPlan product) throws SQLException{
        int rows_affected;
        String query = "UPDATE current_cart SET qty = ? WHERE uid = ? AND pid = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, product.getProductStock());
        ps.setInt(2, cart.getUserId());
        ps.setInt(3, cart.getProductId());
        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static int deleteProductInFinalCart(CartPlan cart) throws SQLException{
        int rows_affected;
        String query = "DELETE FROM current_cart WHERE uid = ? AND pid = ?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, cart.getUserId());
        ps.setInt(2, cart.getProductId());

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    //----------------------------------------BILL-------------------------------------------------

    public static int insertIntoBill(BillPlan bill) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO bill(auid, uid, totalprice, mode) VALUES (?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, bill.getUserAddressId());
        ps.setInt(2, bill.getUserId());
        ps.setFloat(3, bill.getTotalPrice());
        ps.setString(4, bill.getModeOfPayment());

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static int getLastGeneratedBillId(UserPlan user) throws SQLException{
        String query = "SELECT bid FROM bill WHERE uid = "+user.getUserId()+" ORDER BY bid DESC LIMIT 1";

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);

        ResultSet rs = ps.executeQuery();
        rs.next();
        int bill_id = rs.getInt(1);
        rs.close();

        return bill_id;
    }

    public static int insertIntoOrderHistory(OrderHistoryPlan order) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO order_history VALUES (?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, order.getBillId());
        ps.setInt(2, order.getUserId());
        ps.setInt(3, order.getProductId());
        ps.setInt(4, order.getProductQuantity());
        ps.setFloat(5, order.getProductPrice());

        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static ResultSet fetchTimeStampOfCurrentBill(BillPlan bill) throws SQLException{
        String query = "SELECT ts FROM bill WHERE uid = "+
                bill.getUserId()+" AND bid = "+bill.getBillId();

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    //-----------------------------------ORDER HISTORY---------------------------------------------

    public static ResultSet fetchOrderHistory(UserPlan user) throws SQLException{
        String query = "SELECT b.bid, u.addr, u.pincode, u.p_no, b.totalprice, b.mode, b.ts FROM" +
                " bill AS b INNER JOIN user_details AS u WHERE b.uid ="+user.getUserId()+" AND b.auid=u.auid;";

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    //----------------------------------WISH LIST--------------------------------------------------

    public static ResultSet fetchWishList(UserPlan user) throws SQLException{
        String query = "SELECT w.pid, p.pname FROM wishlist AS w INNER JOIN " +
                "product AS p WHERE w.pid=p.pid AND w.uid = "+user.getUserId();

        Statement stmt = connection.createStatement();

        ResultSet rs = stmt.executeQuery(query);

        return rs;
    }

    public static boolean checkProductId(int product_id) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM product WHERE pid = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, product_id);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            //product exists.
            terminateConnection();
            return true;
        }

        System.out.println("Product doesn't exists.");
        terminateConnection();
        return false;
    }

    // flag - true: add product; false - remove product
    public static boolean checkProductDuplicate(WishListPlan wishlist, boolean flag) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM wishlist WHERE uid = ? AND pid = ?";
        createConnection();

        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, wishlist.getUserId());
        ps.setInt(2, wishlist.getProductId());

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            if(flag){
                System.out.println("Product already exists in wish list.");
            }
            rs.close();
            terminateConnection();
            return true;
        }

        if(!flag){
            System.out.println("Product doesn't exists in wish list.");
        }

        rs.close();
        terminateConnection();
        return false;
    }

    public static int addProductToWishList(WishListPlan wishlist) throws SQLException{
        int rows_affected;
        String query = "INSERT INTO wishlist(uid, pid) VALUES(?, ?)";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, wishlist.getUserId());
        ps.setInt(2, wishlist.getProductId());
        rows_affected = ps.executeUpdate();

        return rows_affected;
    }

    public static int deleteProductFromWishList(WishListPlan wishlist) throws SQLException{
        int rows_affected;
        String query = "DELETE FROM wishlist WHERE uid=? AND pid=?";

        PreparedStatement ps = connection.prepareStatement(query);

        ps.setInt(1, wishlist.getUserId());
        ps.setInt(2, wishlist.getProductId());
        rows_affected = ps.executeUpdate();

        return rows_affected;
    }
}
