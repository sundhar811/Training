package com.webapp;

public class WishList implements WishListPlan {
    private int userId;
    private int productId;
    private String productName;

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
    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Override
    public String getProductName() {
        return this.productName;
    }

    @Override
    public String toString() {
        String display_text = "Product ID: "+getProductId()+"\nUser ID: "+getUserId()+
                "\nProduct Name: "+getProductName();

        return display_text;
    }
}
