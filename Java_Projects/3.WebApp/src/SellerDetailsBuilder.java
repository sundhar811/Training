package com.webapp;

public class SellerDetailsBuilder implements SellerDetailsBuilderPlan {

    private SellerDetailsPlan seller_details;

    public SellerDetailsBuilder(){
        this.seller_details = new SellerDetails();
    }

    @Override
    public void buildSellerAddressId(int sellerAddressId) {
        seller_details.setSellerAddressId(sellerAddressId);
    }

    @Override
    public void buildSellerId(int sellerId) {
        seller_details.setSellerId(sellerId);
    }

    @Override
    public void buildSellerPhoneNumber(long phoneNumber) {
        seller_details.setSellerPhoneNumber(phoneNumber);
    }

    @Override
    public void buildSellerAddress(String Address) {
        seller_details.setSellerAddress(Address);
    }

    @Override
    public void buildSellerPinCode(int sellerPinCode) {
        seller_details.setSellerPinCode(sellerPinCode);
    }

    @Override
    public SellerDetailsPlan getSellerDetails() {
        return this.seller_details;
    }
}
