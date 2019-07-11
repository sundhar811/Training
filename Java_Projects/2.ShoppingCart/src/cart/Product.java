package cart;

public class Product implements ProductPlan {
    private int productId;
    private int sellerId;
    private String productName;
    private int productStock;
    private float productPrice;
    private float avgRating;
    private int noOfUsersRated;

    @Override
    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int getProductId() {
        return this.productId;
    }

    @Override
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public int getSellerId() {
        return this.sellerId;
    }

    @Override
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String getProductName() {
        return this.productName;
    }

    @Override
    public void setProductStock(int productStock) {
        this.productStock = productStock;
    }

    @Override
    public int getProductStock() {
        return this.productStock;
    }

    @Override
    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public float getProductPrice() {
        return this.productPrice;
    }

    @Override
    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    @Override
    public float getAvgRating() {
        return this.avgRating;
    }

    @Override
    public void setNoOfUsersRated(int noOfUsersRated) {
        this.noOfUsersRated = noOfUsersRated;
    }

    @Override
    public int getNoOfUsersRated() {
        return this.noOfUsersRated;
    }

    @Override
    public String toString() {
        String display_text = "Product Id: "+getProductId()+"Seller Id: "+getSellerId()+
                "\nProduct Name: "+getProductName()+"\nProduct Stock: "+getProductStock()+
                "\nProduct Price: "+getProductPrice()+"\nAverage Rating: "+getAvgRating()+
                "\nNo of Users Rated: "+getNoOfUsersRated();

        return display_text;
    }
}
