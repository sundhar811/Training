package cart;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class WishListClass {

    static void viewWishList(UserBuilderPlan user) throws SQLException, ClassNotFoundException {
        ArrayList<WishListPlan> wishlist = new ArrayList<>();
        DatabaseClass.createConnection();

        ResultSet rs = DatabaseClass.fetchWishList(user.getUser());

        if(rs.isBeforeFirst()){
            while(rs.next()){
                WishListBuilderPlan wishlist_product = new WishListBuilder();
                wishlist_product.buildProductId(rs.getInt(1));
                wishlist_product.buildProductName(rs.getString(2));

                wishlist.add(wishlist_product.getWishList());
            }
            DisplayClass.displayWishList(wishlist, wishlist.size());
        }
        else{
            System.out.println("No items in wish list.");
        }

        rs.close();
        DatabaseClass.terminateConnection();
    }

    static void addProduct(UserBuilderPlan user) throws IOException, SQLException, ClassNotFoundException {
        int pr_id;

        do{
            pr_id = InputClass.getIdFromConsole();
        }while(!DatabaseClass.checkProductId(pr_id));

        WishListBuilderPlan wishlist = new WishListBuilder();
        wishlist.buildProductId(pr_id);
        wishlist.buildUserId(user.getUser().getUserId());

        if(DatabaseClass.checkProductDuplicate(wishlist.getWishList(), true)){
            return;
        }

        DatabaseClass.createConnection();

        int status = DatabaseClass.addProductToWishList(wishlist.getWishList());

        DatabaseClass.terminateConnection();

        if(status>0){
            System.out.println("Product added to wish list successfully!!");
        }
        else {
            System.out.println("Error while adding product to wishlist.");
        }

        viewWishList(user);
    }

    static void deleteProduct(UserBuilderPlan user) throws IOException, SQLException, ClassNotFoundException{
        WishListBuilderPlan wishlist = new WishListBuilder();
        int pr_id;

        wishlist.buildUserId(user.getUser().getUserId());

        do{
            pr_id = InputClass.getIdFromConsole();

            wishlist.buildProductId(pr_id);
        }while (!(DatabaseClass.checkProductDuplicate(wishlist.getWishList(), false)));

        DatabaseClass.createConnection();

        int status = DatabaseClass.deleteProductFromWishList(wishlist.getWishList());

        DatabaseClass.terminateConnection();

        if(status>0){
            System.out.println("Product removed from wish list successfully!!");
        }
        else {
            System.out.println("Error while removing product from wishlist.");
        }

        viewWishList(user);
    }
}
