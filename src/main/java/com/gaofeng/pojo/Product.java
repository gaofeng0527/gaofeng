package com.gaofeng.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class Product implements Serializable {
	public static final String PRODUCT_TYPE_ONE = "年付";
	public static final String PRODUCT_TYPE_TWO = "季付";
	public static final String PRODUCT_TYPE_THE = "月付";

	public Product() {
		super();
	}

	public Product(Long id, String title, BigDecimal price, String area, String operator, int type, String discounts,
			BigDecimal dcoupon, int status, Date addTime, Company company, String ptypeName, String statusName,
			Long companyId) {
		super();
		this.id = id;
		this.title = title;
		this.price = price;
		this.area = area;
		this.operator = operator;
		this.type = type;
		this.discounts = discounts;
		this.dcoupon = dcoupon;
		this.status = status;
		this.addTime = addTime;
		this.company = company;
		this.ptypeName = ptypeName;
		this.statusName = statusName;
		this.companyId = companyId;
	}

	private Long id;
	@Excel(name = "产品名称", width = 40, orderNum = "0", isImportField = "true")
	@NotNull(message = "产品名称不能为空")
	private String title;
	private String city;
	private String province;
	@Excel(name = "产品价格", width = 20, orderNum = "2", isImportField = "true")
	private BigDecimal price;
	@Excel(name = "所属区域", width = 25, orderNum = "3", isImportField = "true")
	private String area;
	@Excel(name = "运营商", width = 25, orderNum = "4", isImportField = "true")
	private String operator;
	@Excel(name = "产品类型", width = 25, replace = { "年付_1", "季付_2", "月付_3" }, orderNum = "5", isImportField = "true")
	private Integer type;
	@Excel(name = "优惠政策", width = 65, orderNum = "9")
	private String discounts;
	@Excel(name = "优惠金额", orderNum = "6")
	private BigDecimal dcoupon;
	@Excel(name = "产品状态", replace = { "在用_0", "弃用_1" }, orderNum = "8")
	private Integer status;
	@Excel(name = "添加日期", format = "yyyy-MM-dd HH:mm:ss", width = 20, orderNum = "7")
	private Date addTime;
	private Company company;
	private String ptypeName;
	private String statusName;
	private Long companyId;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getDiscounts() {
		return discounts;
	}

	public void setDiscounts(String discounts) {
		this.discounts = discounts;
	}

	public BigDecimal getDcoupon() {
		return dcoupon;
	}

	public void setDcoupon(BigDecimal dcoupon) {
		this.dcoupon = dcoupon;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getStatusName() {
		if (status == 0) {
			return "在用";
		} else {
			return "弃用";
		}
	}

	public String getPtypeName() {
		if(null == type) {
			return PRODUCT_TYPE_ONE;
		}else if (type == 1) {
			return PRODUCT_TYPE_ONE;
		} else if (type == 2) {
			return PRODUCT_TYPE_TWO;
		} else if (type == 3) {
			return PRODUCT_TYPE_THE;
		} else {
			return PRODUCT_TYPE_ONE;
		}
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", title=" + title + ", price=" + price + ", area=" + area + ", operator="
				+ operator + ", type=" + type + ", discounts=" + discounts + ", dcoupon=" + dcoupon + ", status="
				+ status + ", addTime=" + addTime + ", company=" + company + "]";
	}

}
