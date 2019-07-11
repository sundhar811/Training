package cart;

public interface OrderHistoryPlan {
    public void setBillId(int billId);

    public int getBillId();

    public void setUserId(int userId);

    public int getUserId();

    public void setProductId(int productId);

    public int getProductId();

    public void setProductQuantity(int productQuantity);

    public int getProductQuantity();

    public void setProductPrice(float productPrice);

    public float getProductPrice();
}
