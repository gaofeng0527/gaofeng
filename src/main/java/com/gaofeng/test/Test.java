package com.gaofeng.test;

import java.io.File;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.gaofeng.pojo.Product;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;

public class Test {

	public static void main(String[] args) {
		 ImportParams params = new ImportParams();
	        params.setHeadRows(1);
	        params.setNeedVerfiy(true);
	        long start = new Date().getTime();
	        ExcelImportResult<Product> result= ExcelImportUtil.importExcelMore(new File("F:/a.xls"),Product.class, params);
	        System.out.println(result.getList());
	        System.out.println(result.getFailList());
	        
	        List<Product> list = ExcelImportUtil.importExcel(
	           new File("F:/a.xls"),
	           Product.class, params);
	        System.out.println(new Date().getTime() - start);
	        System.out.println(list.size());
	        System.out.println(ReflectionToStringBuilder.toString(list.get(0)));

	}

}
