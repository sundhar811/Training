package cart;

import java.util.ArrayList;

public class DisplayClass {

    static void displayProducts(ArrayList<ProductPlan> products, ArrayList<SellerPlan> sellers, int size){
        System.out.println("================================PRODUCT LIST==================================");
        System.out.printf("%5s %-50s %5s %10s %12s %-15s\n", "PR.ID",
                "PRODUCT_NAME", "STOCK", "PRICE", "AVG. RATING", "SELLER_NAME");

        for(int i=0; i<size; i++){
            System.out.printf("%5s %-50s %5s %10s %12s %-15s\n", products.get(i).getProductId(),
                    products.get(i).getProductName(), products.get(i).getProductStock(),
                    products.get(i).getProductPrice(), products.get(i).getAvgRating(),
                    sellers.get(i).getSellerName());
        }
    }

    static void displayUserDetails(ArrayList<UserDetailsPlan> user_details, int size){
        System.out.println("=================================YOUR DETAILS=================================");
        System.out.printf("%5s %12s %-35s %7s\n","S. No", "PHONE_NUMBER", "ADDRESS", "PINCODE");

        for(int i=0; i<size; i++){
            System.out.printf("%5s %12s %-35s %7s\n", i+1, user_details.get(i).getUserPhoneNumber(),
                    user_details.get(i).getUserAddress(), user_details.get(i).getUserPinCode());
        }
    }

    static void displaySellerDetails(ArrayList<SellerDetailsPlan> seller_details, int size){
        System.out.println("=================================YOUR DETAILS=================================");
        System.out.printf("%5s %12s %-35s %7s\n","S. No", "PHONE_NUMBER", "ADDRESS", "PINCODE");

        for(int i=0; i<size; i++){
            System.out.printf("%5s %12s %-35s %7s\n", i+1, seller_details.get(i).getSellerPhoneNumber(),
                    seller_details.get(i).getSellerAddress(), seller_details.get(i).getSellerPinCode());
        }
    }

    //flag - true: current products; false :past products
    static void displaySellerProducts(ArrayList<ProductPlan> products, int size, boolean flag){
        if(flag){
            System.out.println("============================CURRENT PRODUCT LIST==============================");
        }
        else {
            System.out.println("==============================OLD PRODUCT LIST================================");
        }

        System.out.printf("%5s %-50s %5s %10s %12s\n", "PR.ID",
                "PRODUCT_NAME", "STOCK", "PRICE", "AVG. RATING");

        for(int i=0; i<size; i++){
            System.out.printf("%5s %-50s %5s %10s %12s\n", products.get(i).getProductId(),
                    products.get(i).getProductName(), products.get(i).getProductStock(),
                    products.get(i).getProductPrice(), products.get(i).getAvgRating());
        }
    }

    static void displayCartProducts(ArrayList<CartPlan> cart_products, ArrayList<ProductPlan> products, int size){
        System.out.println("==================================USER CART===================================");
        System.out.printf("%5s %-50s %10s %10s\n","PR.ID", "PRODUCT NAME", "QUANTITY", "PRICE");

        for(int i=0; i<size; i++){
            System.out.printf("%5s %-50s %10s %10s\n", cart_products.get(i).getProductId(),
                    products.get(i).getProductName(), cart_products.get(i).getProductQuantity(),
                    cart_products.get(i).getProductPrice());
        }
    }

    static void displayUnavailableProductsDuringCheckout(CartPlan cart, ProductPlan product){
        System.out.printf("%6s %-50s %13s %15s\n", "PR.ID", "PRODUCT NAME",
                "USER QUANTITY", "AVAILABLE STOCK");

        System.out.printf("%6s %-50s %13s %15s\n", cart.getProductId(), product.getProductName(),
                cart.getProductQuantity(), product.getProductStock());
    }

    static void displayCurrentBill(BillPlan bill){
        System.out.println("==================================YOUR BILL===================================");

        System.out.printf("%7s %7s %10s %11s %-12s %-20s\n", "BILL_ID", "USER_ID", "Address_ID",
                "TOTAL_PRICE", "PAYMENT_MODE", "TIME");

        System.out.printf("%7s %7s %10s %11s %-12s %-20s\n", bill.getBillId(), bill.getUserId(),
                bill.getUserAddressId(), bill.getTotalPrice(), bill.getModeOfPayment(),
                bill.getTimeStamp());
    }

    static void displayOrderHistory(ArrayList<BillPlan> bills, ArrayList<UserDetailsPlan> user_details, int size){
        System.out.println("=================================YOUR ORDERS==================================");
        System.out.printf("%7s %-30s %7s %12s %11s %-12s %-20s\n", "BILL_ID", "BILL_ADDRESS", "PINCODE",
                "PHONE_NUMBER", "TOTAL_PRICE", "PAYMENT_MODE", "TIME");

        for(int i=0; i<size; i++){
            System.out.printf("%7s %-30s %7s %12s %11s %-12s %-20s\n", bills.get(i).getBillId(),
                    user_details.get(i).getUserAddress(), user_details.get(i).getUserPinCode(),
                    user_details.get(i).getUserPhoneNumber(), bills.get(i).getTotalPrice(),
                    bills.get(i).getModeOfPayment(), bills.get(i).getTimeStamp());
        }
    }

    static void displayWishList(ArrayList<WishListPlan> wishlist, int size){
        System.out.println("==================================WISH LIST===================================");
        System.out.printf("%5s %-50s\n","PR.ID", "PRODUCT NAME");

        for(int i=0; i<size; i++){
            System.out.printf("%5s %-50s\n",wishlist.get(i).getProductId(),
                    wishlist.get(i).getProductName());
        }
    }
}
