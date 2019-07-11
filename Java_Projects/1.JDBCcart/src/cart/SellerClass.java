package cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class SellerClass {
    BufferedReader br;
    int seller_id;

    SellerClass(){
        br = new BufferedReader(new InputStreamReader(System.in));
        seller_id = -1;
    }

    void checkCredentials(String un, String pw) throws ClassNotFoundException, SQLException {
        String query = "SELECT sid FROM seller_credentials WHERE loginname = ? AND password = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setString(1, un);
        ps.setString(2, pw);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            seller_id = rs.getInt("sid");
        }

        DatabaseClass.terminateConnection();

        //return seller_id;

    }

    boolean signInPage() throws IOException, SQLException, ClassNotFoundException{
        System.out.print("Enter username: ");
        String un = br.readLine();
        System.out.print("Enter password: ");
        String pw = br.readLine();

        //seller_id = checkCredentials(un, pw);
        checkCredentials(un, pw);

        if (seller_id>0){
            System.out.println("Login Successful");
            return true;
        }
        else{
            System.out.println("Incorrect username/password.");
            return false;
        }
    }

    boolean checkSellerNameRedundancy(String un) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM seller_credentials WHERE loginname = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setString(1, un);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            System.out.println("Username already exists.");
            DatabaseClass.terminateConnection();
            return true;
        }

        DatabaseClass.terminateConnection();
        return false;

    }

    boolean checkSellerEmailRedundancy(String smail) throws SQLException, ClassNotFoundException{
        String query = "SELECT * FROM sellers WHERE smail = ?";
        DatabaseClass.createConnection();

        PreparedStatement ps = DatabaseClass.connection.prepareStatement(query);
        ps.setString(1, smail);

        ResultSet rs = ps.executeQuery();

        if(rs.next()){
            System.out.println("e-mail already exists.");
            DatabaseClass.terminateConnection();
            return true;
        }

        DatabaseClass.terminateConnection();
        return false;

    }

    void signUpPage() throws IOException, ClassNotFoundException, SQLException{
        boolean seller_redundancy_flag, email_redundancy_flag;
        System.out.println("=================================REGISTRATION=================================");

        System.out.print("Enter name: ");
        String name = br.readLine();

        String mail;
        do{
            System.out.print("Enter email: ");
            mail = br.readLine();
            email_redundancy_flag = checkSellerEmailRedundancy(mail);

        }while (email_redundancy_flag == true);

        String un;
        do{
            System.out.print("Enter username: ");
            un = br.readLine();
            seller_redundancy_flag = checkSellerNameRedundancy(un);

        }while (seller_redundancy_flag == true);

        System.out.print("Enter password: ");
        String pw = br.readLine();

        String query1 = "INSERT INTO sellers(sname, smail) VALUES(?, ?)";
        String query2 = "INSERT INTO seller_credentials(loginname, password) VALUES(?, ?)";

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

    void addSellerDetails() throws ClassNotFoundException, SQLException, IOException{
        String query;
        String choice;
        PreparedStatement ps;
        int counter = 1;

        DatabaseClass.createConnection();

        query = "SELECT p_no, addr, pincode FROM seller_details WHERE sid = "+seller_id;
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

            query = "INSERT INTO seller_details(sid, p_no, addr, pincode) VALUES (?, ?, ?, ?)";
            ps = DatabaseClass.connection.prepareStatement(query);

            ps.setInt(1, seller_id);
            ps.setLong(2, ph);
            ps.setString(3, ad);
            ps.setInt(4, pc);

            ps.executeUpdate();

            System.out.println("Add new details: y/n");
            choice = br.readLine();

        }

        DatabaseClass.terminateConnection();
    }


    void sellerProductPage() throws ClassNotFoundException, SQLException, IOException{
        String choice;

        do{
            ProductClass.displayNewProductForSeller(seller_id);
            ProductClass.displayOldProductForSeller(seller_id);
            System.out.println("================================PRODUCT PAGE==================================");
            System.out.println("Select the function");
            System.out.println("1.Add New Products.\n2.Modify Existing Products." +
                    "\n3.Remove Products\n4.Go Back.");
            System.out.print("Enter your choice: ");

            switch (choice = br.readLine()) {
                case "1":
                    ProductClass.addNewProducts(seller_id);
                    break;
                case "2":
                    ProductClass.modifyProducts(seller_id);
                    break;
                case "3":
                    ProductClass.removeProducts(seller_id);
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Enter a valid choice");
            }

        }while(!choice.equals("4"));

    }

    void sellerFrontPage() throws IOException, ClassNotFoundException, SQLException{
        String choice;

        do{
            System.out.println("=================================SELLER PAGE==================================");
            System.out.println("Select the function");
            System.out.println("1.Show available products.\n2.Product manager.\n3.Show my products.\n" +
                    "4.View/Add seller details.\n5.Logout.");
            System.out.print("Enter your choice: ");

            switch(choice = br.readLine()){
                case "1":
                    ProductClass.productFrontPage();
                    break;
                case "2":
                    sellerProductPage();
                    break;
                case "3":
                    ProductClass.displayNewProductForSeller(seller_id);
                    ProductClass.displayOldProductForSeller(seller_id);
                    break;
                case "4":
                    addSellerDetails();
                    break;
                case "5":
                    seller_id = -1;
                    System.out.println("Returning to the main page.");
                    break;
                default:
                    System.out.println("Enter a valid choice.");
            }
        }while(!(choice.equals("5")));
    }

    void frontPage() throws IOException, ClassNotFoundException, SQLException {
        boolean login_flag;
        String choice;

        do {
            System.out.println("===================================WELCOME====================================");
            System.out.println("Seller page - Select the function");
            System.out.println("1.Sign In\n2.Sign Up");
            System.out.print("Enter your choice: ");

            switch (choice = br.readLine()) {
                case "1":
                    do {
                        System.out.println("====================================LOGIN=====================================");
                        login_flag = signInPage();
                    } while (login_flag == false);
                    sellerFrontPage();
                    break;
                case "2":
                    signUpPage();
                    break;
                default:
                    System.out.println("Enter a valid choice");
            }

        } while (!(choice.equals("1") || choice.equals("2")));

    }
}
