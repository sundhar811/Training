package com.webapp;

public interface WishListBuilderPlan {
    public void buildUserId(int userId);

    public void buildProductId(int productId);

    public void buildProductName(String productName);

    public WishListPlan getWishList();
}
