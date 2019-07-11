package cart;

public class ProductBuilder implements ProductBuilderPlan {

    private ProductPlan product;

    public ProductBuilder(){
        this.product = new Product();
    }

    @Override
    public void buildProductId(int productId) {
        this.product.setProductId(productId);
    }

    @Override
    public void buildSellerId(int sellerId) {
        this.product.setSellerId(sellerId);
    }

    @Override
    public void buildProductName(String productName) {
        this.product.setProductName(productName);
    }

    @Override
    public void buildProductStock(int productStock) {
        this.product.setProductStock(productStock);
    }

    @Override
    public void buildProductPrice(float productPrice) {
        this.product.setProductPrice(productPrice);
    }

    @Override
    public void buildAvgRating(float avgRating) {
        this.product.setAvgRating(avgRating);
    }

    @Override
    public void buildNoOfUsersRated(int noOfUsersRated) {
        this.product.setNoOfUsersRated(noOfUsersRated);
    }

    @Override
    public ProductPlan getProduct() {
        return this.product;
    }
}
