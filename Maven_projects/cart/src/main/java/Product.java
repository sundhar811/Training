//$Id$
package com.shopping.cart;

public class Product {
	private int productId;
    private int sellerId;
    private String productName;
    private int productStock;
    private float productPrice;
    private float avgRating;
    private int noOfUsersRated;
    private SellerDetail sellerDetail;
   
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getSellerId() {
		return sellerId;
	}
	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public int getProductStock() {
		return productStock;
	}
	public void setProductStock(int productStock) {
		this.productStock = productStock;
	}
	public float getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(float productPrice) {
		this.productPrice = productPrice;
	}
	public float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}
	public int getNoOfUsersRated() {
		return noOfUsersRated;
	}
	public void setNoOfUsersRated(int noOfUsersRated) {
		this.noOfUsersRated = noOfUsersRated;
	}
	public SellerDetail getSellerDetail() {
		return sellerDetail;
	}
	public void setSellerDetail(SellerDetail sellerDetail) {
		this.sellerDetail = sellerDetail;
	}
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", sellerId=" + sellerId + ", productName=" + productName
				+ ", productStock=" + productStock + ", productPrice=" + productPrice + ", avgRating=" + avgRating
				+ ", noOfUsersRated=" + noOfUsersRated + ", sellerDetail=" + sellerDetail + "]";
	}    
}
