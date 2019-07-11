//$Id$
package com.shopping.cart;

public class WishList {
	private int userId;
    private int productId;
    private String productName;
    
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Override
	public String toString() {
		return "WishList [userId=" + userId + ", productId=" + productId + ", productName=" + productName + "]";
	}
    
}
