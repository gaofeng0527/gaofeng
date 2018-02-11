package com.gaofeng.pojo;

import java.util.Date;

public class MalFunction {

	private Long id;
	private WxUser user;
	private Account account;
	private int status;
	private Date createTime;
	private Date lastTime;
	private String acotrName;
	private String acotrPhone;
	private String description;

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

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
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

	public String getAcotrName() {
		return acotrName;
	}

	public void setAcotrName(String acotrName) {
		this.acotrName = acotrName;
	}

	public String getAcotrPhone() {
		return acotrPhone;
	}

	public void setAcotrPhone(String acotrPhone) {
		this.acotrPhone = acotrPhone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "MalFunction [id=" + id + ", user=" + user + ", account=" + account + ", status=" + status
				+ ", createTime=" + createTime + ", lastTime=" + lastTime + ", acotrName=" + acotrName + ", acotrPhone="
				+ acotrPhone + ", description=" + description + "]";
	}

}
