package com.gaofeng.pojo;

import java.util.Date;

/**
 * 宽带帐号
 * 
 * @author Administrator
 *
 */
public class Account {

	private Long id;
	private WxUser user;
	private Long uid;
	private String accountName;
	private String accountCode;
	private String phone;
	private String area;
	private String city;
	private String province;
	private String address;
	private int status;
	private String statusName;
	private Date beginTime;
	private Date endTime;
	private Product product;
	private Long productId;
	private Long installId;
	private Long companyId;
	private Company company;
	private String description;
	
	public Long getInstallId() {
		return installId;
	}

	public void setInstallId(Long installId) {
		this.installId = installId;
	}

	public String getStatusName() {
		if(status == 0) {
			return "正常";
		}else if(status == 1) {
			return "已过期";
		}else {
			return "已过期";
		}
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

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", user=" + user + ", uid=" + uid + ", accountName=" + accountName
				+ ", accountCode=" + accountCode + ", phone=" + phone + ", area=" + area + ", city=" + city
				+ ", province=" + province + ", address=" + address + ", status=" + status + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", product=" + product + ", productId=" + productId + ", companyId="
				+ companyId + ", company=" + company + ", description=" + description + "]";
	}

}
