package cart;

public interface SellerDetailsBuilderPlan {

    public void buildSellerAddressId(int sellerAddressId);

    public void buildSellerId(int sellerId);

    public void buildSellerPhoneNumber(long phoneNumber);

    public void buildSellerAddress(String Address);

    public void buildSellerPinCode(int sellerPinCode);

    public SellerDetailsPlan getSellerDetails();
    
}
