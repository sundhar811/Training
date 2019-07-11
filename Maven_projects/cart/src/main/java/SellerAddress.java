//$Id$
package com.shopping.cart;

public class SellerAddress {
	
	private int sellerAddressId;
    private int sellerId;
    private long phoneNumber;
    private String sellerAddress;
    private int sellerPinCode;
    
    public int getSellerAddressId() {
		return sellerAddressId;
	}
	public void setSellerAddressId(int sellerAddressId) {
		this.sellerAddressId = sellerAddressId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getSellerAddress() {
		return sellerAddress;
	}
	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}
	public int getSellerPinCode() {
		return sellerPinCode;
	}
	public void setSellerPinCode(int sellerPinCode) {
		this.sellerPinCode = sellerPinCode;
	}
	@Override
	public String toString() {
		return "SellerAddress [sellAddressId=" + sellerAddressId + ", sellerId=" + sellerId + ", phoneNumber="
				+ phoneNumber + ", sellerAddress=" + sellerAddress + ", sellerPinCode=" + sellerPinCode + "]";
	}
}
