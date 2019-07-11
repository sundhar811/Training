//$Id$
package com.shopping.cart;

public class UserAddress {
	private int userAddressId;
    private int userId;
    private long phoneNumber;
    private String userAddress;
    private int userPinCode;
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
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	public int getUserPinCode() {
		return userPinCode;
	}
	public void setUserPinCode(int userPinCode) {
		this.userPinCode = userPinCode;
	}
	@Override
	public String toString() {
		return "UserAddress [userAddressId=" + userAddressId + ", userId=" + userId + ", phoneNumber=" + phoneNumber
				+ ", userAddress=" + userAddress + ", userPinCode=" + userPinCode + "]";
	}
    
    
}
