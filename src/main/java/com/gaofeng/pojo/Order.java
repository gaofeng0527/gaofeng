package com.gaofeng.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Order {

	private Long id;
	private WxUser user;
	private Long userId;
	private String uname;
	private String phone;
	private int otype;
	private int status;
	private int payStatus;
	private Date orderTime;
	private BigDecimal orderPrice;
	private BigDecimal coupanPay;
	private int payType;
	private Date finishTime;
	//交易流水号
	private String moduleId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public WxUser getUser() {
		return user;
	}

	public void setUser(WxUser user) {
		this.user = user;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getOtype() {
		return otype;
	}

	public void setOtype(int otype) {
		this.otype = otype;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public BigDecimal getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(BigDecimal orderPrice) {
		this.orderPrice = orderPrice;
	}

	public BigDecimal getCoupanPay() {
		return coupanPay;
	}

	public void setCoupanPay(BigDecimal coupanPay) {
		this.coupanPay = coupanPay;
	}

	public int getPayType() {
		return payType;
	}

	public void setPayType(int payType) {
		this.payType = payType;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", user=" + user + ", uname=" + uname + ", phone=" + phone + ", otype=" + otype
				+ ", status=" + status + ", payStatus=" + payStatus + ", orderTime=" + orderTime + ", orderPrice="
				+ orderPrice + ", coupanPay=" + coupanPay + ", payType=" + payType + ", finishTime=" + finishTime
				+ ", moduleId=" + moduleId + "]";
	}

}
