package cart;

public class OrderHistoryBuilder implements OrderHistoryBuilderPlan {
    private OrderHistoryPlan order;

    public OrderHistoryBuilder(){
        this.order = new OrderHistory();
    }

    @Override
    public void buildBillId(int billId) {
        this.order.setBillId(billId);
    }

    @Override
    public void buildUserId(int userId) {
        this.order.setUserId(userId);
    }

    @Override
    public void buildProductId(int productId) {
        this.order.setProductId(productId);
    }

    @Override
    public void buildProductQuantity(int productQuantity) {
        this.order.setProductQuantity(productQuantity);
    }

    @Override
    public void buildProductPrice(float productPrice) {
        this.order.setProductPrice(productPrice);
    }

    @Override
    public OrderHistoryPlan getOH() {
        return this.order;
    }
}
