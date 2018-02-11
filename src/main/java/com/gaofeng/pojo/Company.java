package com.gaofeng.pojo;

public class Company {

	private Long id;
	private String cname;

	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Company(Long id, String cname) {
		super();
		this.id = id;
		this.cname = cname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", cname=" + cname + "]";
	}

}
