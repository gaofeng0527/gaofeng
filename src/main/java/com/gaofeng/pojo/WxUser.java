package com.gaofeng.pojo;

import java.util.Date;
import java.util.List;

import cn.afterturn.easypoi.excel.annotation.Excel;

public class WxUser {

	private Long id;
	@Excel(name = "微信昵称", width = 30)
	private String nickName;
	private String headImage;
	@Excel(name = "微信OPENID", width = 40)
	private String openId;
	@Excel(name = "用户姓名", width = 20)
	private String uname;
	private String upwd;
	@Excel(name = "联系电话", width = 30)
	private String uphone;
	@Excel(name = "证件类型", width = 20)
	private String ctype;
	@Excel(name = "证件号码", width = 40)
	private String cardId;
	@Excel(name = "详细地址", width = 80)
	private String address;
	@Excel(name = "所属区域", width = 20)
	private String area;
	@Excel(name = "城市", width = 20)
	private String city;
	@Excel(name = "省份", width = 20)
	private String province;
	@Excel(name = "性别", width = 15)
	private String sex;
	@Excel(name = "添加日期", format = "yyyy-MM-dd HH:mm:ss", width = 30)
	private Date regTime;
	@Excel(name = "用户状态", replace = { "正常_0", "流失_1" }, width = 20)
	private int status;
	private Company company;

	/**
	 * 用户拥有的宽带
	 */
	private List<Account> accounts;

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getUpwd() {
		return upwd;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public String getUphone() {
		return uphone;
	}

	public void setUphone(String uphone) {
		this.uphone = uphone;
	}

	public String getCtype() {
		return ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getRegTime() {
		return regTime;
	}

	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	@Override
	public String toString() {
		return "WxUser [id=" + id + ", nickName=" + nickName + ", headImage=" + headImage + ", openId=" + openId
				+ ", uname=" + uname + ", upwd=" + upwd + ", uphone=" + uphone + ", ctype=" + ctype + ", cardId="
				+ cardId + ", address=" + address + ", area=" + area + ", city=" + city + ", province=" + province
				+ ", sex=" + sex + ", regTime=" + regTime + ", status=" + status + ", company=" + company + "]";
	}

}
