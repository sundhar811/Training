package com.webapp;

public interface OrderHistoryBuilderPlan {
    public void buildBillId(int billId);

    public void buildUserId(int userId);

    public void buildProductId(int productId);

    public void buildProductQuantity(int productQuantity);

    public void buildProductPrice(float productPrice);

    public OrderHistoryPlan getOH();
}
