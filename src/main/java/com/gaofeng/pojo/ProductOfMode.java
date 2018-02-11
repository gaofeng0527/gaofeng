package com.gaofeng.pojo;

import cn.afterturn.easypoi.handler.inter.IExcelModel;

public class ProductOfMode extends Product implements IExcelModel {
	
	private String errorMsg;
	public String getErrorMsg() {
		// TODO Auto-generated method stub
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		// TODO Auto-generated method stub
		this.errorMsg = errorMsg;
	}

}
