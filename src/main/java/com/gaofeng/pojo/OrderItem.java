package com.gaofeng.pojo;

import java.math.BigDecimal;

public class OrderItem {
	private Long id;
	private Order order;
	private Long orderId;
	private Product product;
	private Long productId;
	private String title;
	private BigDecimal price;
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", order=" + order + ", orderId=" + orderId + ", product=" + product
				+ ", productId=" + productId + ", title=" + title + ", price=" + price + ", description=" + description
				+ "]";
	}

	
}
