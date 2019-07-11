package cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class UserClass {
    BufferedReader br;
    int user_id;

    UserClass(){
        br = new BufferedReader(new InputStreamReader(System.in));
        user_id = -1;
    }

    void checkCredentials(String un, String pw) throws ClassNotFoundException, SQLException {
        String query = "SELECT uid FROM user_credentials WHERE loginname = ? AND password = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setString(1, un);
        ps.setString(2, pw);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            user_id = rs.getInt("uid");
        }

        rs.close();
        DatabaseClass.terminateConnection();

        //return user_id;

    }

    boolean signInPage() throws IOException, SQLException, ClassNotFoundException{
        System.out.print("Enter username: ");
        String un = br.readLine();
        System.out.print("Enter password: ");
        String pw = br.readLine();

        //user_id = checkCredentials(un, pw);
        checkCredentials(un, pw);

        if (user_id>0){
            System.out.println("Login Successful");
            return true;
        }
        else{
            System.out.println("Incorrect username/password.");
            return false;
        }
    }

    boolean checkUserNameRedundancy(String un) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM user_credentials WHERE loginname = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setString(1, un);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            System.out.println("Username already exists.");
            DatabaseClass.terminateConnection();
            return true;
        }

        rs.close();
        DatabaseClass.terminateConnection();
        return false;

    }

    boolean checkUserEmailRedundancy(String umail) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM users WHERE umail = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setString(1, umail);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            System.out.println("e-mail already exists.");
            DatabaseClass.terminateConnection();
            return true;
        }

        rs.close();
        DatabaseClass.terminateConnection();
        return false;

    }

    void signUpPage() throws IOException, ClassNotFoundException, SQLException{
        boolean user_redundancy_flag, email_redundancy_flag;
        System.out.println("=================================REGISTRATION=================================");

        System.out.print("Enter name: ");
        String name = br.readLine();

        String mail;
        do{
            System.out.print("Enter email: ");
            mail = br.readLine();
            email_redundancy_flag = checkUserEmailRedundancy(mail);

        }while (email_redundancy_flag == true);

        String un;
        do{
            System.out.print("Enter username: ");
            un = br.readLine();
            user_redundancy_flag = checkUserNameRedundancy(un);

        }while (user_redundancy_flag == true);

        System.out.print("Enter password: ");
        String pw = br.readLine();

        String query1 = "INSERT INTO users(uname, umail) VALUES(?, ?)";
        String query2 = "INSERT INTO user_credentials(loginname, password) VALUES(?, ?)";

        DatabaseClass.createConnection();

        PreparedStatement ps1 = DatabaseClass.connection.prepareStatement(query1);
        PreparedStatement ps2 = DatabaseClass.connection.prepareStatement(query2);

        ps1.setString(1, name);
        ps1.setString(2, mail);
        ps1.executeUpdate();

        ps2.setString(1, un);
        ps2.setString(2, pw);
        ps2.executeUpdate();


        System.out.println("REGISTRATION IS SUCCESSFUL");

        DatabaseClass.terminateConnection();

    }

    void userShopPage() throws IOException, SQLException, ClassNotFoundException{
        String choice;

        do{
            System.out.println("==================================SHOP PAGE===================================");
            System.out.println("Select the function");
            System.out.println("1.Add New Products.\n2.Modify Products in Cart." +
                    "\n3.Remove Products from Cart.\n4.Finalize purchase.\n5.Go Back.");
            System.out.print("Enter your choice: ");

            switch (choice = br.readLine()) {
                case "1":
                    CartClass.displayCart(user_id);
                    CartClass.getInput(user_id, true);
                    break;
                case "2":
                    CartClass.displayCart(user_id);
                    CartClass.getInput(user_id, false);
                    break;
                case "3":
                    CartClass.displayCart(user_id);
                    CartClass.removeProductFromCart(user_id);
                    break;
                case "4":
                    CartClass.displayCart(user_id);
                    CartClass.finalizePurchase(user_id);
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Enter a valid choice");
            }

        }while(!(choice.equals("4")||choice.equals("5")));

    }

    void addUserDetails() throws ClassNotFoundException, SQLException, IOException{
        String query;
        String choice;
        PreparedStatement ps;
        int counter = 1;

        DatabaseClass.createConnection();

        query = "SELECT p_no, addr, pincode FROM user_details WHERE uid = "+user_id;
        ps = DatabaseClass.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        System.out.println("=================================YOUR DETAILS=================================");
        System.out.printf("%5s %12s %-35s %7s\n","S. No", "PHONE_NUMBER", "ADDRESS", "PINCODE");

        while (rs.next()){
            System.out.printf("%5s %12s %-35s %7s\n", counter++, rs.getLong(1),
                    rs.getString(2), rs.getInt(3));
        }

        rs.close();

        System.out.println("Add new details: y/n");
        choice = br.readLine();

        while (choice.equals("y")){
            System.out.print("Enter phone number: ");
            long ph = Long.parseLong(br.readLine());
            System.out.print("Enter address: ");
            String ad = br.readLine();
            System.out.print("Enter pincode: ");
            int pc = Integer.parseInt(br.readLine());

            query = "INSERT INTO user_details(uid, p_no, addr, pincode) VALUES (?, ?, ?, ?)";
            ps = DatabaseClass.connection.prepareStatement(query);

            ps.setInt(1, user_id);
            ps.setLong(2, ph);
            ps.setString(3, ad);
            ps.setInt(4, pc);

            ps.executeUpdate();

            System.out.print("Add new details: [y/n] ... ");
            choice = br.readLine();

        }

        DatabaseClass.terminateConnection();
    }

    void showOrderHistory() throws SQLException, ClassNotFoundException{
        DatabaseClass.createConnection();

        String query = "SELECT b.bid, u.addr, u.pincode, u.p_no, b.totalprice, b.mode, b.ts FROM" +
                " bill AS b INNER JOIN user_details AS u WHERE b.uid ="+user_id+" AND b.auid=u.auid;";

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        if(rs.isBeforeFirst()){
            System.out.println("=================================YOUR ORDERS==================================");
            System.out.printf("%7s %-30s %7s %12s %11s %-12s %-20s\n", "BILL_ID", "BILL_ADDRESS", "PINCODE",
                    "PHONE_NUMBER", "TOTAL_PRICE", "PAYMENT_MODE", "TIME");
        }
        else{
            System.out.println("No purchase history.");
        }

        while (rs.next()){
            System.out.printf("%7s %-30s %7s %12s %11s %-12s %-20s\n", rs.getInt(1),
                    rs.getString(2), rs.getInt(3), rs.getLong(4),
                    rs.getFloat(5), rs.getString(6), rs.getTimestamp(7));
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }

    void userWishListPage() throws IOException, SQLException, ClassNotFoundException{
        String choice;

        do{
            System.out.println("==============================WISH LIST PAGE==============================");
            System.out.println("Select the function");
            System.out.println("1.View Wish list.\n2.Add New Products to Wish list." +
                    "\n3.Remove Products from Cart.\n4.Go Back.");
            System.out.print("Enter your choice: ");

            switch (choice = br.readLine()) {
                case "1":
                    WishlistClass.viewWishList(user_id);
                    break;
                case "2":
                    WishlistClass.addProduct(user_id);
                    break;
                case "3":
                    WishlistClass.deleteProduct(user_id);
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Enter a valid choice");
            }
        }while(!choice.equals("4"));
    }

    void userFrontPage() throws IOException, ClassNotFoundException, SQLException{
        String choice;

        do{
            System.out.println("==================================USER PAGE===================================");
            System.out.println("Select the function");
            System.out.println("1.Show available products.\n2.Shop.\n3.Show user cart.\n" +
                    "4.View/Add user details.\n5.Show Order history.\n6.Wish list.\n7.Logout.");
            System.out.print("Enter your choice: ");

            switch(choice = br.readLine()){
                case "1":
                    ProductClass.productFrontPage();
                    break;
                case "2":
                    userShopPage();
                    break;
                case "3":
                    CartClass.displayCart(user_id);
                    System.out.println("Press 1 to purchase the products.\nPress any other number to go back.");
                    String finalize_choice = br.readLine();
                    if(finalize_choice.equals("1")){
                        CartClass.finalizePurchase(user_id);
                    }
                    break;
                case "4":
                    addUserDetails();
                    break;
                case "5":
                    showOrderHistory();
                    break;
                case "6":
                    userWishListPage();
                    break;
                case "7":
                    user_id = -1;
                    System.out.println("Returning to the main page.");
                    break;
                default:
                    System.out.println("Enter a valid choice.");
            }
        }while(!(choice.equals("7")));
    }

    void frontPage() throws IOException, ClassNotFoundException, SQLException {
        boolean login_flag;
        String choice;

        do{
            System.out.println("===================================WELCOME====================================");
            System.out.println("User page - Select the function");
            System.out.println("1.Sign In\n2.Sign Up");
            System.out.print("Enter your choice: ");

            switch (choice = br.readLine()) {
                case "1":
                    do{
                        System.out.println("====================================LOGIN=====================================");
                        login_flag = signInPage();
                    }while(login_flag == false);
                    userFrontPage();
                    break;
                case "2":
                    signUpPage();
                    break;
                default:
                    System.out.println("Enter a valid choice");
            }

        }while(!(choice.equals("1")||choice.equals("2")));

        //CartClass.displayCart(1);
    }
}
