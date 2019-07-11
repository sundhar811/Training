package cart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductClass {

    static void displayProduct() throws SQLException, ClassNotFoundException {
        ArrayList<ProductPlan> list_of_products = new ArrayList<>();
        ArrayList<SellerPlan> seller_names = new ArrayList<>();

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.displayAllProductsById();

        while(rs.next()){
            ProductBuilderPlan product = new ProductBuilder();
            product.buildProductId(rs.getInt(1));
            product.buildProductName(rs.getString(2));
            product.buildProductStock(rs.getInt(3));
            product.buildProductPrice(rs.getFloat(4));
            product.buildAvgRating(rs.getFloat(5));

            list_of_products.add(product.getProduct());

            SellerBuilderPlan seller = new SellerBuilder();
            seller.buildSellerName(rs.getString(6));
            seller_names.add(seller.getSeller());
        }

        rs.close();
        DatabaseClass.terminateConnection();

        if(list_of_products.size()>0){
            DisplayClass.displayProducts(list_of_products, seller_names, list_of_products.size());
        }
        else {
            System.out.println("No Products Found");
        }

    }

    static void displayProductByName() throws SQLException, ClassNotFoundException {
        ArrayList<ProductPlan> list_of_products = new ArrayList<>();
        ArrayList<SellerPlan> seller_names = new ArrayList<>();

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.displayAllProductsByName();

        while(rs.next()){
            ProductBuilderPlan product = new ProductBuilder();
            product.buildProductId(rs.getInt(1));
            product.buildProductName(rs.getString(2));
            product.buildProductStock(rs.getInt(3));
            product.buildProductPrice(rs.getFloat(4));
            product.buildAvgRating(rs.getFloat(5));

            list_of_products.add(product.getProduct());

            SellerBuilderPlan seller = new SellerBuilder();
            seller.buildSellerName(rs.getString(6));
            seller_names.add(seller.getSeller());
        }

        rs.close();
        DatabaseClass.terminateConnection();

        if(list_of_products.size()>0){
            DisplayClass.displayProducts(list_of_products, seller_names, list_of_products.size());
        }
        else {
            System.out.println("No Products Found");
        }
    }

    static void displayProductByPrice() throws SQLException, ClassNotFoundException {
        ArrayList<ProductPlan> list_of_products = new ArrayList<>();
        ArrayList<SellerPlan> seller_names = new ArrayList<>();

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.displayAllProductsByPrice();

        while(rs.next()){
            ProductBuilderPlan product = new ProductBuilder();
            product.buildProductId(rs.getInt(1));
            product.buildProductName(rs.getString(2));
            product.buildProductStock(rs.getInt(3));
            product.buildProductPrice(rs.getFloat(4));
            product.buildAvgRating(rs.getFloat(5));

            list_of_products.add(product.getProduct());

            SellerBuilderPlan seller = new SellerBuilder();
            seller.buildSellerName(rs.getString(6));
            seller_names.add(seller.getSeller());
        }

        rs.close();
        DatabaseClass.terminateConnection();

        if(list_of_products.size()>0){
            DisplayClass.displayProducts(list_of_products, seller_names, list_of_products.size());
        }
        else {
            System.out.println("No Products Found");
        }
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

    static void displayCurrentSellerProduct(SellerBuilderPlan seller) throws SQLException, ClassNotFoundException{
        ArrayList<ProductPlan> list_of_products = new ArrayList<>();

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.displayCurrentSellerProduct(seller.getSeller().getSellerId());

        while(rs.next()){
            ProductBuilderPlan product = new ProductBuilder();
            product.buildProductId(rs.getInt(1));
            product.buildProductName(rs.getString(2));
            product.buildProductStock(rs.getInt(3));
            product.buildProductPrice(rs.getFloat(4));
            product.buildAvgRating(rs.getFloat(5));

            list_of_products.add(product.getProduct());
        }

        rs.close();
        DatabaseClass.terminateConnection();

        if(list_of_products.size()>0){
            DisplayClass.displaySellerProducts(list_of_products, list_of_products.size(), true);
        }
        else {
            System.out.println("No Current Products Found");
        }
    }

    static void displayOldSellerProduct(SellerBuilderPlan seller) throws SQLException, ClassNotFoundException{
        ArrayList<ProductPlan> list_of_products = new ArrayList<>();

        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.displayOldSellerProduct(seller.getSeller().getSellerId());

        while(rs.next()){
            ProductBuilderPlan product = new ProductBuilder();
            product.buildProductId(rs.getInt(1));
            product.buildProductName(rs.getString(2));
            product.buildProductStock(rs.getInt(3));
            product.buildProductPrice(rs.getFloat(4));
            product.buildAvgRating(rs.getFloat(5));

            list_of_products.add(product.getProduct());
        }

        rs.close();
        DatabaseClass.terminateConnection();

        if(list_of_products.size()>0){
            DisplayClass.displaySellerProducts(list_of_products, list_of_products.size(), false);
        }
        else {
            System.out.println("No Old Products Found");
        }

    }

    static void addNewProducts(SellerBuilderPlan seller) throws  IOException, SQLException, ClassNotFoundException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice;
        String pname;
        int stock;
        float price;

        do{
            do{
                pname = InputClass.getProductNameFromConsole();
            }while(InputClass.isInputStringNull(pname));

            do{
                stock = InputClass.getProductStockFromConsole();
            }while(!InputClass.isNumberGreaterThanZero(stock));

            do{
                price = InputClass.getProductPriceFromConsole();
            }while (!InputClass.isNumberPositive(price));

            ProductBuilderPlan new_product = new ProductBuilder();

            new_product.buildProductName(pname);
            new_product.buildProductStock(stock);
            new_product.buildProductPrice(price);

            DatabaseClass.createConnection();

            int status = DatabaseClass.addNewProducts(new_product.getProduct(), seller.getSeller().getSellerId());

            DatabaseClass.terminateConnection();

            if(status>0){
                System.out.println("PRODUCT DETAILS INSERTION IS SUCCESSFUL");
            }

            else{
                System.out.println("ERROR DURING INSERTION");
            }


            System.out.println("Add another product?: [y/n] ...");
            choice = br.readLine();

        }while (choice.equals("y"));

    }

    static void removeProducts(SellerBuilderPlan seller) throws IOException, SQLException, ClassNotFoundException{
        String choice;
        int pid;

        do{
            do{
                pid = InputClass.getIdFromConsole();
            }while(!DatabaseClass.isProductAssociatedWithSeller(seller.getSeller().getSellerId(), pid));

            DatabaseClass.createConnection();

            int status = DatabaseClass.deleteProducts(seller.getSeller().getSellerId(), pid);

            DatabaseClass.terminateConnection();

            if(status>0){
                System.out.println("PRODUCT REMOVAL IS SUCCESSFUL");
            }

            else{
                System.out.println("PRODUCT NOT REMOVED");
            }

            choice = InputClass.getYesOrNoFromConsole();

        }while (choice.equals("y"));

    }

    static void modifyProductName(SellerBuilderPlan seller) throws IOException, SQLException, ClassNotFoundException{
        int pid;
        String pname;

        do{
            pid = InputClass.getIdFromConsole();
        }while(!DatabaseClass.isProductAssociatedWithSeller(seller.getSeller().getSellerId(), pid));

        DatabaseClass.createConnection();

        System.out.println("Current product name: "+DatabaseClass.getProductName(pid));

        do{
            pname = InputClass.getProductNameFromConsole();
        }while(InputClass.isInputStringNull(pname));

        int status = DatabaseClass.updateProductName(pid, pname);

        DatabaseClass.terminateConnection();

        if(status>0){
            System.out.println("PRODUCT NAME MODIFICATION IS SUCCESSFUL");
        }

        else{
            System.out.println("ERROR DURING UPDATE");
        }
    }

    static void modifyProductStock(SellerBuilderPlan seller) throws IOException, SQLException, ClassNotFoundException{
        int pid;
        int stock;

        do{
            pid = InputClass.getIdFromConsole();
        }while(!DatabaseClass.isProductAssociatedWithSeller(seller.getSeller().getSellerId(), pid));

        DatabaseClass.createConnection();

        System.out.println("Current product stock: "+DatabaseClass.getProductStock(pid));

        do{
            stock = InputClass.getProductStockFromConsole();
        }while(!InputClass.isNumberGreaterThanZero(stock));

        int status = DatabaseClass.updateProductStock(pid, stock);

        DatabaseClass.terminateConnection();

        if(status>0){
            System.out.println("PRODUCT STOCK MODIFICATION IS SUCCESSFUL");
        }

        else{
            System.out.println("ERROR DURING UPDATE");
        }
    }

    static void modifyProductPrice(SellerBuilderPlan seller) throws IOException, SQLException, ClassNotFoundException{
        int pid;
        float price;

        do{
            pid = InputClass.getIdFromConsole();
        }while(!DatabaseClass.isProductAssociatedWithSeller(seller.getSeller().getSellerId(), pid));

        DatabaseClass.createConnection();

        System.out.println("Current product price: "+DatabaseClass.getProductPrice(pid));

        do{
            price = InputClass.getProductPriceFromConsole();
        }while(!InputClass.isNumberPositive(price));

        try{
            DatabaseClass.connection.setAutoCommit(false);

            int status1 = DatabaseClass.updateProductPriceInProductList(pid, price);
            int status2 = DatabaseClass.updateProductPriceInUserCart(pid, price);

            if((status1>0)&&(status2>0)){
                System.out.println("Price changed successfully.");
            }

            else{
                System.out.println("Price not changed.");
            }

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

    static void modifyProducts(SellerBuilderPlan seller) throws IOException, SQLException, ClassNotFoundException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String choice;

        System.out.println("Select a function: ");
        System.out.println("1. Modify Product Name.\n2. Modify Product Stock.\n3. Modify Product Price.");
        System.out.print("Enter your choice: ");

        do{
            switch (choice = br.readLine()){
                case "1":
                    modifyProductName(seller);
                    break;
                case "2":
                    modifyProductStock(seller);
                    break;
                case "3":
                    modifyProductPrice(seller);
                    break;
                default:
                    System.out.println("Enter a valid choice.");
            }
        }while(!(choice.equals("1")||choice.equals("2")||choice.equals("3")));
    }
}
