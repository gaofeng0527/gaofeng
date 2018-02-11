package com.gaofeng.pojo;

import java.math.BigDecimal;
import java.util.Date;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class InstallMessage {

	private Long id; // 标识列
	private Order order; // 订单
	private Long orderId;
	private OrderItem orderItem;
	private Long itemId;
	private WxUser user; // 报装用户
	private Long userId;
	@Excel(name = "客户姓名", width = 40, isImportField = "true")
	private String uname;
	@Excel(name = "客户电话", width = 40, isImportField = "true")
	private String phone;
	@Excel(name = "所属区域", width = 30, isImportField = "true")
	private String area;
	private String city;
	private String province;
	@Excel(name = "详细地址", width = 50, isImportField = "true")
	private String address;
	private Product product;
	@Excel(name = "选择套餐", width = 60)
	private String title;
	@Excel(name = "产品价格", width = 20)
	private BigDecimal price;
	private Long productId;
	@Excel(name = "状态", width = 50, replace = { "新申请_0", "装机中_1", "装机成功_2", "装机失败_3" }, isImportField = "true")
	private int status; // 状态

	private String statusName;
	@Excel(name = "报装时间", width = 50, format = "yyyy-MM-dd HH:mm:ss", isImportField = "true")
	private Date createTime; // 报装时间
	private Date lastTime;
	private Company company;
	private Long companyId;
	@Excel(name = "装机负责人", width = 50, isImportField = "true")
	private String acotrUser;
	@Excel(name = "装机负责人电话", width = 50, isImportField = "true")
	private String acotrPhone;
	private String description;
	@Excel(name = "装机来源", replace = { "人工导入_manually", "微信报装_weixin" }, width = 50)
	private String origin;
	private String orgName;
	private String jobNumber;// 工号
	@Excel(name = "督导", width = 50, isImportField = "true")
	private String supervisor;// 督导
	@Excel(name = "证件号码", width = 50, isImportField = "true")
	private String card;// 证件号

	// 辅助字段
	private Date vdate;
	private String account;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public Date getVdate() {
		return vdate;
	}

	public void setVdate(Date vdate) {
		this.vdate = vdate;
	}

	public String getTitle() {
		if (null != product)
			return product.getTitle();
		else
			return "";
	}

	public BigDecimal getPrice() {
		if (null != product)
			return product.getPrice();
		else
			return null;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}

	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public String getOrgName() {
		if ("weixin".equals(origin))
			return "微信报装";
		else
			return "人工添加";
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getStatusName() {
		if (status == 0) {
			return "新申请";
		} else if (status == 1) {
			return "装机中";
		} else if (status == 2) {
			return "装机成功";
		} else if (status == 3) {
			return "装机失败";
		} else {
			return "装机失败";
		}
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public OrderItem getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem orderItem) {
		this.orderItem = orderItem;
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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
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

	public String getAcotrUser() {
		return acotrUser;
	}

	public void setAcotrUser(String acotrUser) {
		this.acotrUser = acotrUser;
	}

	public String getAcotrPhone() {
		return acotrPhone;
	}

	public void setAcotrPhone(String acotrPhone) {
		this.acotrPhone = acotrPhone;
	}

	@Override
	public String toString() {
		return "InstallMessage [id=" + id + ", order=" + order + ", orderId=" + orderId + ", orderItem=" + orderItem
				+ ", itemId=" + itemId + ", user=" + user + ", userId=" + userId + ", uname=" + uname + ", phone="
				+ phone + ", area=" + area + ", city=" + city + ", province=" + province + ", address=" + address
				+ ", product=" + product + ", productId=" + productId + ", status=" + status + ", createTime="
				+ createTime + ", lastTime=" + lastTime + ", company=" + company + ", companyId=" + companyId
				+ ", acotrUser=" + acotrUser + ", acotrPhone=" + acotrPhone + ", description=" + description + "]";
	}

}
