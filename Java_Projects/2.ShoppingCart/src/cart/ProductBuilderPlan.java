package cart;

public interface ProductBuilderPlan {

    public void buildProductId(int productId);

    public void buildSellerId(int sellerId);

    public void buildProductName(String productName);

    public void buildProductStock(int productStock);

    public void buildProductPrice(float productPrice);

    public void buildAvgRating(float avgRating);

    public void buildNoOfUsersRated(int noOfUsersRated);

    public ProductPlan getProduct();

}
