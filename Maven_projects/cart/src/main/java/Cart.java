//$Id$
package com.shopping.cart;

public class Cart {
    private int userId;
    private int productId;
    private String productName;
    private int productQuantity;
    private float productPrice;
    
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
	public int getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	
	@Override
	public String toString() {
		return "Cart [userId=" + userId + ", productId=" + productId + ", productQuantity=" + productQuantity
				+ ", productPrice=" + productPrice + "]";
	}
	
}
