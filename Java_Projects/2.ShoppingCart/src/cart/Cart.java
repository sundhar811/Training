package cart;

public class Cart implements CartPlan {
    private int userId;
    private int productId;
    private int productQuantity;
    private float productPrice;

    @Override
    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public int getUserId() {
        return this.userId;
    }

    @Override
    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int getProductId() {
        return this.productId;
    }

    @Override
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public int getProductQuantity() {
        return this.productQuantity;
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
    public String toString() {
        String display_text = "Product ID: "+getProductId()+"\nUser ID: "+getUserId()+
                "\nQuantity: "+getProductQuantity()+"\nCumulative Price: "+getProductPrice();

        return display_text;
    }
}
