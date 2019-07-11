//$Id$
package com.shopping.cart;

import java.sql.Timestamp;

public class Purchase {
	private int billId;
    private int userAddressId;
    private int userId;
    private float totalPrice;
    private String modeOfPayment;
    private Timestamp timestamp;
    private UserAddress userAddress;
    
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public int getUserAddressId() {
		return userAddressId;
	}
	public void setUserAddressId(int userAddressId) {
		this.userAddressId = userAddressId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public float getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(float totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getModeOfPayment() {
		return modeOfPayment;
	}
	public void setModeOfPayment(String modeOfPayment) {
		this.modeOfPayment = modeOfPayment;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public UserAddress getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(UserAddress userAddress) {
		this.userAddress = userAddress;
	}
	@Override
	public String toString() {
		return "Purchase [billId=" + billId + ", userAddressId=" + userAddressId + ", userId=" + userId
				+ ", totalPrice=" + totalPrice + ", modeOfPayment=" + modeOfPayment + ", timestamp=" + timestamp
				+ ", userAddress=" + userAddress + "]";
	}
	
}
