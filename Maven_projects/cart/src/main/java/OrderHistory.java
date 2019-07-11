//$Id$
package com.shopping.cart;

public class OrderHistory {
	private int billId;
    private int userId;
    private int productId;
    private int productQuantity;
    private float productPrice;
    private Product product;
    
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "OrderHistory [billId=" + billId + ", userId=" + userId + ", productId=" + productId
				+ ", productQuantity=" + productQuantity + ", productPrice=" + productPrice + ", product=" + product
				+ "]";
	}
	
}
