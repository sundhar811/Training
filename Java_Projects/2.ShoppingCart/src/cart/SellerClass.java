package cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class SellerClass {
    BufferedReader br;
    int seller_id;
    SellerBuilderPlan seller;

    SellerClass() {
        br = new BufferedReader(new InputStreamReader(System.in));
        seller_id = -1;
    }

    void createSellerObject(int seller_id) throws SQLException, ClassNotFoundException{
        String sname = null;
        String pwd = null;
        String name = null;
        String email = null;
        ResultSet rs;

        seller = new SellerBuilder();

        DatabaseClass.createConnection();

        rs = DatabaseClass.fetchSellerCredentials(seller_id);

        if(rs.isBeforeFirst()){
            rs.next();

            sname = rs.getString(1);
            pwd = rs.getString(2);
        }
        else{
            System.out.println("Error fetching user credentials from DB");
        }

        rs.close();

        rs = DatabaseClass.fetchSellerInfo(seller_id);

        if(rs.isBeforeFirst()){
            rs.next();

            name = rs.getString(1);
            email = rs.getString(2);
        }
        else{
            System.out.println("Error fetching user details from DB");
        }

        rs.close();

        seller.buildSellerId(seller_id);
        seller.buildSellerName(sname);
        seller.buildPassWord(pwd);
        seller.buildName(name);
        seller.buildEmail(email);

//        SellerPlan check_seller = seller.getSeller();
//        System.out.println(check_seller.toString());

        DatabaseClass.terminateConnection();
    }

    boolean signInPage() throws IOException, SQLException, ClassNotFoundException {

        String un = InputClass.getLoginNameFromConsole();
        String pw = InputClass.getPasswordFromConsole();

        SellerBuilderPlan login_seller = new SellerBuilder();
        login_seller.buildSellerName(un);
        login_seller.buildPassWord(pw);

        seller_id = DatabaseClass.checkSellerCredentials(login_seller);

        if (seller_id > 0) {
            System.out.println("Login Successful");
            return true;
        } else {
            System.out.println("Incorrect username/password.");
            return false;
        }
    }

    void signUpPage() throws IOException, ClassNotFoundException, SQLException{
        boolean user_redundancy_flag, email_redundancy_flag;
        System.out.println("=================================REGISTRATION=================================");

        String name = InputClass.getNameFromConsole();

        String mail;
        do{
            mail = InputClass.getMailFromConsole();
            email_redundancy_flag = DatabaseClass.checkSellerEmailRedundancy(mail);

        }while (email_redundancy_flag == true);

        String un;
        do{
            un = InputClass.getLoginNameFromConsole();
            user_redundancy_flag = DatabaseClass.checkSellerLoginNameRedundancy(un);

        }while (user_redundancy_flag == true);

        String pw = InputClass.getPasswordFromConsole();

        SellerBuilderPlan new_seller = new SellerBuilder();

        new_seller.buildSellerName(un);
        new_seller.buildPassWord(pw);
        new_seller.buildName(name);
        new_seller.buildEmail(mail);

        DatabaseClass.createConnection();

        int status1 = 0, status2 = 0;

        try {
            DatabaseClass.connection.setAutoCommit(false);

            status1 = DatabaseClass.insertSellerInfoIntoDb(new_seller.getSeller());
            status2 = DatabaseClass.insertSellerCredentialsIntoDb(new_seller.getSeller());

            DatabaseClass.connection.commit();
            DatabaseClass.connection.setAutoCommit(true);
        }
        catch (SQLException s){
            System.out.println("Unexpected error occurred. Please try later.");
            DatabaseClass.connection.rollback();
            s.printStackTrace();
        }

        DatabaseClass.terminateConnection();

        if((status1>0)&&(status2>0)){
            System.out.println("REGISTRATION IS SUCCESSFUL");
        }

        else{
            System.out.println("ERROR DURING REGISTRATION");
        }

    }

    void addSellerDetails() throws ClassNotFoundException, SQLException, IOException{
        String choice;
        ArrayList<SellerDetailsPlan> list_of_address = new ArrayList<>();

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.displaySellerAddress(seller_id);

        while (rs.next()){
            SellerDetailsBuilderPlan seller_address = new SellerDetailsBuilder();
            seller_address.buildSellerPhoneNumber(rs.getLong(1));
            seller_address.buildSellerAddress(rs.getString(2));
            seller_address.buildSellerPinCode(rs.getInt(3));

            list_of_address.add(seller_address.getSellerDetails());
        }

        rs.close();

        if(list_of_address.size()>0){
            DisplayClass.displaySellerDetails(list_of_address, list_of_address.size());
        }
        else{
            System.out.println("No Address Found.");
        }

        choice = InputClass.getYesOrNoFromConsole();

        while (choice.equals("y")){
            long phone_no = InputClass.getPhoneNoFromConsole();
            String address = InputClass.getAddressFromConsole();
            int pincode = InputClass.getPinCodeFromConsole();

            SellerDetailsBuilderPlan new_seller_details = new SellerDetailsBuilder();
            new_seller_details.buildSellerId(seller_id);
            new_seller_details.buildSellerPhoneNumber(phone_no);
            new_seller_details.buildSellerAddress(address);
            new_seller_details.buildSellerPinCode(pincode);

            int status = DatabaseClass.insertSellerDetails(new_seller_details.getSellerDetails());

            if(status>0){
                System.out.println("SELLER DETAILS INSERTION IS SUCCESSFUL");
            }

            else{
                System.out.println("ERROR DURING INSERTION");
            }

            choice = InputClass.getYesOrNoFromConsole();
        }


        DatabaseClass.terminateConnection();
    }

    void sellerProductPage() throws IOException, SQLException, ClassNotFoundException{
        String choice;

        do{
            ProductClass.displayCurrentSellerProduct(seller);
            ProductClass.displayOldSellerProduct(seller);
            System.out.println("================================PRODUCT PAGE==================================");
            System.out.println("Select the function");
            System.out.println("1.Add New Products.\n2.Modify Existing Products." +
                    "\n3.Remove Products\n4.Go Back.");
            System.out.print("Enter your choice: ");

            switch (choice = br.readLine()) {
                case "1":
                    ProductClass.addNewProducts(seller);
                    break;
                case "2":
                    ProductClass.modifyProducts(seller);
                    break;
                case "3":
                    ProductClass.removeProducts(seller);
                    break;
                case "4":
                    break;
                default:
                    System.out.println("Enter a valid choice");
            }

        }while(!choice.equals("4"));

    }

    void sellerFrontPage() throws IOException, SQLException, ClassNotFoundException {
        String choice;

        do {
            System.out.println("=================================SELLER PAGE==================================");
            System.out.println("Select the function");
            System.out.println("1.Show available products.\n2.Product manager.\n3.Show my products.\n" +
                    "4.View/Add seller details.\n5.Logout.");
            System.out.print("Enter your choice: ");

            switch (choice = br.readLine()) {
                case "1":
                    ProductClass.productFrontPage();
                    break;
                case "2":
                    sellerProductPage();
                    break;
                case "3":
                    ProductClass.displayCurrentSellerProduct(seller);
                    ProductClass.displayOldSellerProduct(seller);
                    break;
                case "4":
                    addSellerDetails();
                    break;
                case "5":
                    seller_id = -1;
                    seller = null;
                    System.out.println("Returning to the main page.");
                    break;
                default:
                    System.out.println("Enter a valid choice.");
            }
        } while (!(choice.equals("5")));
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
                    createSellerObject(seller_id);
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