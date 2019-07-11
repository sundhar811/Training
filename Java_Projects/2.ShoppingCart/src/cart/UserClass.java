package cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

class UserClass {
    BufferedReader br;
    int user_id;
    UserBuilderPlan user;

    UserClass(){
        br = new BufferedReader(new InputStreamReader(System.in));
        user_id = -1;
    }

    void createUserObject(int user_id) throws SQLException, ClassNotFoundException{
        String uname = null;
        String pwd = null;
        String name = null;
        String email = null;
        ResultSet rs;

        user = new UserBuilder();

        DatabaseClass.createConnection();

        rs = DatabaseClass.fetchUserCredentials(user_id);

        if(rs.isBeforeFirst()){
            rs.next();

            uname = rs.getString(1);
            pwd = rs.getString(2);
        }
        else{
            System.out.println("Error fetching user credentials from DB");
        }

        rs.close();

        rs = DatabaseClass.fetchUserInfo(user_id);

        if(rs.isBeforeFirst()){
            rs.next();

            name = rs.getString(1);
            email = rs.getString(2);
        }
        else{
            System.out.println("Error fetching user details from DB");
        }

        rs.close();

        user.buildUserId(user_id);
        user.buildUserName(uname);
        user.buildPassWord(pwd);
        user.buildName(name);
        user.buildEmail(email);

//        UserPlan check_user = user.getUser();
//        System.out.println(check_user.toString());

        DatabaseClass.terminateConnection();
    }

    boolean signInPage() throws IOException, SQLException, ClassNotFoundException{

        String un = InputClass.getLoginNameFromConsole();
        String pw = InputClass.getPasswordFromConsole();

        UserBuilderPlan login_user = new UserBuilder();
        login_user.buildUserName(un);
        login_user.buildPassWord(pw);

        //user_id = checkCredentials(un, pw);
        user_id = DatabaseClass.checkUserCredentials(login_user);

        if (user_id>0){
            System.out.println("Login Successful");
            return true;
        }
        else{
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
            email_redundancy_flag = DatabaseClass.checkUserEmailRedundancy(mail);

        }while (email_redundancy_flag == true);

        String un;
        do{
            un = InputClass.getLoginNameFromConsole();
            user_redundancy_flag = DatabaseClass.checkUserLoginNameRedundancy(un);

        }while (user_redundancy_flag == true);

        String pw = InputClass.getPasswordFromConsole();

        UserBuilderPlan new_user = new UserBuilder();

        new_user.buildUserName(un);
        new_user.buildPassWord(pw);
        new_user.buildName(name);
        new_user.buildEmail(mail);

        DatabaseClass.createConnection();

        int status1 = 0, status2 = 0;

        try {
            DatabaseClass.connection.setAutoCommit(false);

            status1 = DatabaseClass.insertUserInfoIntoDb(new_user.getUser());
            status2 = DatabaseClass.insertUserCredentialsIntoDb(new_user.getUser());

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
                    CartClass.displayCart(user);
                    CartClass.getInputToAddOrModifyCart(user, true);
                    break;
                case "2":
                    CartClass.displayCart(user);
                    CartClass.getInputToAddOrModifyCart(user, false);
                    break;
                case "3":
                    CartClass.displayCart(user);
                    CartClass.removeProductFromCart(user);
                    break;
                case "4":
                    CartClass.displayCart(user);
                    CartClass.finalizePurchase(user);
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Enter a valid choice");
            }

        }while(!(choice.equals("4")||choice.equals("5")));

    }

    void addUserDetails() throws ClassNotFoundException, SQLException, IOException{
        String choice;
        ArrayList<UserDetailsPlan> list_of_address = new ArrayList<>();

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.displayUserAddress(user_id);

        while (rs.next()){
            UserDetailsBuilderPlan user_address = new UserDetailsBuilder();
            user_address.buildUserPhoneNumber(rs.getLong(1));
            user_address.buildUserAddress(rs.getString(2));
            user_address.buildUserPinCode(rs.getInt(3));

            list_of_address.add(user_address.getUserDetails());
        }

        rs.close();

        if(list_of_address.size()>0){
            DisplayClass.displayUserDetails(list_of_address, list_of_address.size());
        }
        else {
            System.out.println("No Address Found.");
        }

        choice = InputClass.getYesOrNoFromConsole();

        while (choice.equals("y")){
            long phone_no = InputClass.getPhoneNoFromConsole();
            String address = InputClass.getAddressFromConsole();
            int pincode = InputClass.getPinCodeFromConsole();

            UserDetailsBuilderPlan new_user_details = new UserDetailsBuilder();
            new_user_details.buildUserId(user_id);
            new_user_details.buildUserPhoneNumber(phone_no);
            new_user_details.buildUserAddress(address);
            new_user_details.buildUserPinCode(pincode);

            int status = DatabaseClass.insertUserDetails(new_user_details.getUserDetails());

            if(status>0){
                System.out.println("USER DETAILS INSERTION IS SUCCESSFUL");
            }

            else{
                System.out.println("ERROR DURING INSERTION");
            }

            choice = InputClass.getYesOrNoFromConsole();
        }


        DatabaseClass.terminateConnection();
    }

    void showOrderHistory() throws SQLException, ClassNotFoundException{
        ArrayList<BillPlan> bills = new ArrayList<>();
        ArrayList<UserDetailsPlan> user_details = new ArrayList<>();
        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.fetchOrderHistory(user.getUser());

        if(rs.isBeforeFirst()){
            while (rs.next()){
                BillBuilderPlan bill = new BillBuilder();
                bill.buildBillId(rs.getInt(1));
                bill.buildTotalPrice(rs.getFloat(5));
                bill.buildMOP(rs.getString(6));
                bill.buildTimeStamp(rs.getTimestamp(7));

                bills.add(bill.getBill());

                UserDetailsBuilderPlan user_detail = new UserDetailsBuilder();
                user_detail.buildUserAddress(rs.getString(2));
                user_detail.buildUserPinCode(rs.getInt(3));
                user_detail.buildUserPhoneNumber(rs.getLong(4));

                user_details.add(user_detail.getUserDetails());
            }
            DisplayClass.displayOrderHistory(bills, user_details, bills.size());
        }
        else{
            System.out.println("No purchase history.");
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
                    WishListClass.viewWishList(user);
                    break;
                case "2":
                    WishListClass.addProduct(user);
                    break;
                case "3":
                    WishListClass.deleteProduct(user);
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
                    CartClass.displayCart(user);
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
                    user = null;
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
                    createUserObject(user_id);
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
