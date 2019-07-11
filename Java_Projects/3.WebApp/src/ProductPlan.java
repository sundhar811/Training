package com.webapp;

public interface ProductPlan {

    public void setProductId(int productId);

    public int getProductId();

    public void setSellerId(int sellerId);

    public int getSellerId();

    public void setProductName(String productName);

    public String getProductName();

    public void setProductStock(int productStock);

    public int getProductStock();

    public void setProductPrice(float productPrice);

    public float getProductPrice();

    public void setAvgRating(float avgRating);

    public float getAvgRating();

    public void setNoOfUsersRated(int noOfUsersRated);

    public int getNoOfUsersRated();
}
