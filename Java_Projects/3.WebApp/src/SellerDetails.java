package com.webapp;

public class SellerDetails implements SellerDetailsPlan{
    private int sellerAddressId;
    private int sellerId;
    private long phoneNumber;
    private String sellerAddress;
    private int sellerPinCode;

    @Override
    public void setSellerAddressId(int sellerAddressId) {
        this.sellerAddressId = sellerAddressId;
    }

    @Override
    public int getSellerAddressId() {
        return this.sellerAddressId;
    }

    @Override
    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public int getSellerId() {
        return this.sellerId;
    }

    @Override
    public void setSellerPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public long getSellerPhoneNumber() {
        return this.phoneNumber;
    }

    @Override
    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    @Override
    public String getSellerAddress() {
        return this.sellerAddress;
    }

    @Override
    public void setSellerPinCode(int sellerPinCode) {
        this.sellerPinCode = sellerPinCode;
    }

    @Override
    public int getSellerPinCode() {
        return this.sellerPinCode;
    }

    @Override
    public String toString() {
        String display_text = "Address ID: "+getSellerAddressId()+"\nSeller ID: "+getSellerId()+
                "\nPhone Number: "+getSellerPhoneNumber()+"\nAddress: "+getSellerAddress()+"\n" +
                "Pin Code: "+getSellerPinCode();

        return display_text;
    }
}
