package cart;

public class WishListBuilder implements WishListBuilderPlan {
    private WishListPlan wishlist;

    public WishListBuilder(){
        this.wishlist = new WishList();
    }

    @Override
    public void buildUserId(int userId) {
        this.wishlist.setUserId(userId);
    }

    @Override
    public void buildProductId(int productId) {
        this.wishlist.setProductId(productId);
    }

    @Override
    public void buildProductName(String productName) {
        this.wishlist.setProductName(productName);
    }

    @Override
    public WishListPlan getWishList() {
        return this.wishlist;
    }
}
