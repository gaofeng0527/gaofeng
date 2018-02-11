package com.gaofeng.pojo;

public class Sysuser {

	private Long id;
	private String login;
	private String sysName;
	private String sysPwd;
	private String phone;
	private String email;
	private String status;
	private Company company;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysPwd() {
		return sysPwd;
	}

	public void setSysPwd(String sysPwd) {
		this.sysPwd = sysPwd;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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
		return "Sysuser [id=" + id + ", login=" + login + ", sysName=" + sysName + ", sysPwd=" + sysPwd + ", phone="
				+ phone + ", email=" + email + ", status=" + status + ", company=" + company + "]";
	}

}
