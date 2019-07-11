package com.webapp;

public interface CartPlan {
    public void setUserId(int userId);

    public int getUserId();

    public void setProductId(int productId);

    public int getProductId();

    public void setProductQuantity(int productQuantity);

    public int getProductQuantity();

    public void setProductPrice(float productPrice);

    public float getProductPrice();
}
