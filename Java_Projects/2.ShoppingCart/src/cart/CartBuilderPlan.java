package cart;

public interface CartBuilderPlan {
    public void buildUserId(int userId);

    public void buildProductId(int productId);

    public void buildProductQuantity(int productQuantity);

    public void buildProductPrice(float productPrice);

    public CartPlan getCartProduct();
}
