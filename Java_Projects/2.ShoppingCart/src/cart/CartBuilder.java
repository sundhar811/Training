package cart;

public class CartBuilder implements CartBuilderPlan{
    private CartPlan cart;

    public CartBuilder(){
        this.cart = new Cart();
    }

    @Override
    public void buildUserId(int userId) {
        this.cart.setUserId(userId);
    }

    @Override
    public void buildProductId(int productId) {
        this.cart.setProductId(productId);
    }

    @Override
    public void buildProductQuantity(int productQuantity) {
        this.cart.setProductQuantity(productQuantity);
    }

    @Override
    public void buildProductPrice(float productPrice) {
        this.cart.setProductPrice(productPrice);
    }

    @Override
    public CartPlan getCartProduct() {
        return this.cart;
    }
}
