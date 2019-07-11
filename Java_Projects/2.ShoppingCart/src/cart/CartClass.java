package cart;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class CartClass {
    static void displayCart(UserBuilderPlan user) throws SQLException, ClassNotFoundException {
        ArrayList<CartPlan> cart_products = new ArrayList<>();
        ArrayList<ProductPlan> products = new ArrayList<>();

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.fetchItemsInUserCart(user.getUser().getUserId());

        while (rs.next()){
            CartBuilderPlan cart_product = new CartBuilder();
            cart_product.buildProductId(rs.getInt(1));
            cart_product.buildProductQuantity(rs.getInt(3));
            cart_product.buildProductPrice(rs.getFloat(4));

            cart_products.add(cart_product.getCartProduct());

            ProductBuilderPlan product = new ProductBuilder();
            product.buildProductName(rs.getString(2));

            products.add(product.getProduct());
        }

        rs.close();
        DatabaseClass.terminateConnection();

        if(cart_products.size()>0){
            DisplayClass.displayCartProducts(cart_products, products, cart_products.size());
        }

        else{
            System.out.println("No items in the cart.");
        }

        System.out.println("CURRENT CART PRICE: "+DatabaseClass.computeTotalPriceOfCart(user.getUser().getUserId()));

    }

    //flag - true: add product to cart; false: modify products in cart
    static void getInputToAddOrModifyCart(UserBuilderPlan user, boolean flag) throws IOException, SQLException, ClassNotFoundException {
        int pr_id, stock;

        do{
            pr_id = InputClass.getIdFromConsole();

            do{
                stock = InputClass.getProductStockFromConsole();
            }while(!InputClass.isNumberGreaterThanZero(stock));

        }while (!(DatabaseClass.checkProductForCart(pr_id, stock)));

        //System.out.println("Input got successfully");

        CartBuilderPlan cart = new CartBuilder();
        cart.buildProductId(pr_id);
        cart.buildProductQuantity(stock);

        if(flag){
            //Add product
            addProductsToCart(user, cart);
        }
        else {
            //modify products
            modifyProductInCart(user, cart, true);
        }
    }

    static void addProductsToCart(UserBuilderPlan user, CartBuilderPlan cart) throws ClassNotFoundException, SQLException {
        float cumulative_price = DatabaseClass.computePriceForProductInCart(cart.getCartProduct().getProductId(), cart.getCartProduct().getProductQuantity());

        if(DatabaseClass.checkCartDuplicates(user.getUser().getUserId(), cart.getCartProduct().getProductId(), false)){
            modifyProductInCart(user, cart, false);
            return;
        }

        cart.buildProductPrice(cumulative_price);
        cart.buildUserId(user.getUser().getUserId());

        DatabaseClass.createConnection();

        int status = DatabaseClass.addProductsToCart(cart.getCartProduct());

        DatabaseClass.terminateConnection();

        if(status>0){
            System.out.println("Product added to cart successfully.");
        }

        else{
            System.out.println("Can't add product to cart.");
        }

        displayCart(user);
    }

    static void modifyProductInCart(UserBuilderPlan user, CartBuilderPlan cart, boolean flag) throws ClassNotFoundException, SQLException{
        int qty;
        cart.buildUserId(user.getUser().getUserId());

        if (!flag){
            qty = cart.getCartProduct().getProductQuantity()+DatabaseClass.getCurrentQuantityFromCart(cart.getCartProduct());
        }
        else {
            qty = cart.getCartProduct().getProductQuantity();
        }
        //int total_stock = qty+getCurrentQuantityFromCart(uid, pid);
        cart.buildProductQuantity(qty);

        float cumulative_price = DatabaseClass.computePriceForProductInCart(cart.getCartProduct().getProductId(), cart.getCartProduct().getProductQuantity());
        cart.buildProductPrice(cumulative_price);


        DatabaseClass.createConnection();

        int status = DatabaseClass.modifyProductInCart(cart.getCartProduct());

        DatabaseClass.terminateConnection();

        if(status>0){
            System.out.println("Product modified in cart successfully.");
        }

        else{
            System.out.println("Can't modify product.");
        }

        displayCart(user);
    }

    static void removeProductFromCart(UserBuilderPlan user) throws ClassNotFoundException, IOException, SQLException {

        int pr_id;

        do{
            pr_id = InputClass.getIdFromConsole();
        }while (!(DatabaseClass.checkCartDuplicates(user.getUser().getUserId(), pr_id, true)));

        DatabaseClass.createConnection();

        int status = DatabaseClass.removeProductFromCart(user.getUser().getUserId(), pr_id);

        DatabaseClass.terminateConnection();

        if(status>0){
            System.out.println("Product deleted from cart successfully!!");
        }

        else{
            System.out.println("Can't delete product.");
        }

        displayCart(user);
    }

    static void checkAvailabilityInFinalCart(UserBuilderPlan user) throws SQLException, ClassNotFoundException, IOException{
        boolean check_not_availability = false;

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.checkAvailabilityInFinalCart(user.getUser().getUserId());

        if(rs.isBeforeFirst()){
            check_not_availability = true;
            System.out.println("=============================UNAVAILABLE ITEMS================================");
        }

        if(check_not_availability){
            while (rs.next()){
                CartBuilderPlan new_cart = new CartBuilder();
                new_cart.buildUserId(user.getUser().getUserId());
                new_cart.buildProductId(rs.getInt(1));
                new_cart.buildProductQuantity(rs.getInt(2));

                ProductBuilder new_product = new ProductBuilder();
                new_product.buildProductName(rs.getString(3));
                new_product.buildProductStock(rs.getInt(4));

                DisplayClass.displayUnavailableProductsDuringCheckout(new_cart.getCartProduct(), new_product.getProduct());

                System.out.println("This item in your cart is not available for purchase. " +
                        "Please modify your order.\n" +
                        "1.Purchase "+new_product.getProduct().getProductStock()+
                        " of "+new_product.getProduct().getProductName()+
                        ".\n2.Remove product from cart.");

                String choice;
                do{
                    choice = InputClass.getChoiceFromConsole();

                    if(choice.equals("1")){
                        int status = DatabaseClass.modifyProductInFinalCart(new_cart.getCartProduct(), new_product.getProduct());

                        if(status>0){
                            System.out.println("Product modified successfully!!");
                        }

                        else{
                            System.out.println("Can't modify product.");
                        }

                    }
                    else if(choice.equals("2")){
                        int status = DatabaseClass.deleteProductInFinalCart(new_cart.getCartProduct());

                        if(status>0){
                            System.out.println("Product deleted successfully!!");
                        }

                        else{
                            System.out.println("Can't delete product.");
                        }
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

    static int selectAddress(UserBuilderPlan user) throws IOException, SQLException, ClassNotFoundException{
        int auid = -1;
        int display_counter = 0;
        ArrayList<UserDetailsPlan> list_of_address = new ArrayList<>();
        HashMap<Integer, Integer> hm = new HashMap<>();

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.displayUserAddress(user.getUser().getUserId());

        while (rs.next()){
            UserDetailsBuilderPlan user_address = new UserDetailsBuilder();
            user_address.buildUserPhoneNumber(rs.getLong(1));
            user_address.buildUserAddress(rs.getString(2));
            user_address.buildUserPinCode(rs.getInt(3));

            list_of_address.add(user_address.getUserDetails());
            hm.put(++display_counter, rs.getInt(4));
        }

        rs.close();
        DatabaseClass.terminateConnection();

        DisplayClass.displayUserDetails(list_of_address, list_of_address.size());

        do{
            String choice = InputClass.getChoiceFromConsole();

            auid = hm.get(Integer.parseInt(choice));

            if(auid<0){
                System.out.println("Please select a valid address.");
            }
        }while(auid<0);


        hm.clear();
        return auid;
    }

    static void finalizePurchase(UserBuilderPlan user) throws IOException, ClassNotFoundException, SQLException{
        int auid;
        float total_price;
        String mode_of_payment = "Cash";

        checkAvailabilityInFinalCart(user);
        auid = selectAddress(user);
        total_price = DatabaseClass.computeTotalPriceOfCart(user.getUser().getUserId());

        BillBuilderPlan bill = new BillBuilder();

        bill.buildUserId(user.getUser().getUserId());
        bill.buildUserAddressId(auid);
        bill.buildTotalPrice(total_price);
        bill.buildMOP(mode_of_payment);

        DatabaseClass.createConnection();

        try{
            DatabaseClass.connection.setAutoCommit(false);

            int status = DatabaseClass.insertIntoBill(bill.getBill());
            if(!(status>0)){
                System.out.println("Error while inserting into bill table.");
            }
            bill.buildBillId(DatabaseClass.getLastGeneratedBillId(user.getUser()));
            ResultSet rs = DatabaseClass.fetchItemsInUserCart(user.getUser().getUserId());

            while (rs.next()){
                OrderHistoryBuilderPlan order = new OrderHistoryBuilder();
                order.buildProductId(rs.getInt(1));
                order.buildProductQuantity(rs.getInt(3));
                order.buildProductPrice(rs.getFloat(4));
                order.buildBillId(bill.getBill().getBillId());
                order.buildUserId(bill.getBill().getUserId());

                int status1 = DatabaseClass.insertIntoOrderHistory(order.getOH());

                int status2 = DatabaseClass.removeProductFromCart(order.getOH().getUserId(), order.getOH().getProductId());

                int new_stock = DatabaseClass.getProductStock(order.getOH().getProductId()) - order.getOH().getProductQuantity();
                int status3 = DatabaseClass.updateProductStock(order.getOH().getProductId(), new_stock);

                if((status1<0)||(status2<0)||(status3<0)){
                    System.out.println("Error while updating the tables.");
                }
            }

            rs.close();

            DatabaseClass.connection.commit();
            DatabaseClass.connection.setAutoCommit(true);

        }catch (SQLException s){
            System.out.println("Unexpected error occurred. Please try later.");
            DatabaseClass.connection.rollback();
            s.printStackTrace();
        }

        ResultSet rs = DatabaseClass.fetchTimeStampOfCurrentBill(bill.getBill());
        if(rs.next()){
            bill.buildTimeStamp(rs.getTimestamp(1));
        }
        else {
            System.out.println("No timestamp fetched.");
        }

        DisplayClass.displayCurrentBill(bill.getBill());

        DatabaseClass.terminateConnection();
    }

}
