package com.gaofeng.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.map.LinkedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.gaofeng.pojo.Company;
import com.gaofeng.pojo.Product;
import com.gaofeng.pojo.Result;
import com.gaofeng.service.ProductService;
import com.gaofeng.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.afterturn.easypoi.entity.vo.MapExcelConstants;
import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;

@Controller
@RequestMapping("/home/product")
public class ProductController {

	@Autowired
	private ProductService pservice;

	@GetMapping("/allList")
	public String index(Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "product/productList";
	}

	@ResponseBody
	@PostMapping("/findProductByPage")
	public PageInfo findProductByPage(int startPage, int pageSize) {
		PageHelper.startPage(startPage, pageSize);
		List<Product> plist = pservice.findAllProduct();
		PageInfo pageInfo = new PageInfo(plist);
		return pageInfo;
	}

	@ResponseBody
	@PostMapping("/search")
	public PageInfo search(String title, String area, String operator, Integer status, int startPage, int pageSize) {
		Product product = new Product();
		if (StringUtil.isNotNull(title)) {
			product.setTitle("%" + title + "%");
		}
		if (StringUtil.isNotNull(area)) {
			product.setArea(area);
		}
		if (StringUtil.isNotNull(operator)) {
			product.setOperator(operator);
		}
		product.setStatus(null != status ? status : -1);
		PageHelper.startPage(startPage, pageSize);
		List<Product> plist = pservice.findByProduct(product);
		PageInfo pageInfo = new PageInfo(plist);
		return pageInfo;
	}

	@GetMapping("/productAdd")
	public String linksAdd(Model model) {

		return "product/productAdd";
	}

	@PostMapping("/productAdd")
	@ResponseBody
	public Result addProduct(@RequestBody Product product) {
		Result result = new Result();
		product.setAddTime(new Date());
		product.setCompanyId(1L);
		product.setCompany(new Company(1L, "测试公司"));
		product.setStatus(0);
		pservice.addProduct(product);
		result.setSuccess(true);
		result.setMessage("添加成功");
		result.setCode(0);
		return result;
	}

	@GetMapping("/deleteProduct/{productId}")
	@ResponseBody
	public Result deleteProduct(@PathVariable("productId") Long productId) {
		pservice.deleteProduct(productId);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("删除成功");
		result.setCode(0);
		return result;
	}

	@GetMapping("/productEdit/{productId}")
	public String productEdit(@PathVariable("productId") Long productId, Model model) {
		Product product = pservice.findOneByProductId(productId);
		model.addAttribute("product", product);
		return "product/productEdit";
	}

	@GetMapping("/findProduct")
	@ResponseBody
	public Result findProduct(Long productId) {
		Product product = pservice.findOneByProductId(productId);
		Result result = new Result();
		result.setData(product);
		return result;
	}

	@PostMapping("/productEdit")
	@ResponseBody
	public Result productEdit(@RequestBody Product product) {
		pservice.updateProduct(product);
		Result result = new Result();
		result.setSuccess(true);
		result.setMessage("保存成功");
		result.setCode(0);
		return result;
	}

	@ResponseBody
	@PostMapping("/importExcel")
	public String fileUpload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request)
			throws Exception {
		long starttimes = System.currentTimeMillis();
		try {
			String path = request.getSession().getServletContext().getRealPath("");
			File f = new File(path + "/excel/" + file.getOriginalFilename());
			if (!f.exists()) {
				try {
					File dir = new File(path + "/excel/");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					if (f.createNewFile()) {
						System.out.println("创建文件成功");
					} else {
						System.out.println("创建文件失败");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(f));

			ImportParams params = new ImportParams();
			List<Product> plist = ExcelImportUtil.importExcel(f, Product.class, params);
			System.out.println(plist.size());
			System.out.println(plist);
			pservice.addList(plist);
		} catch (Exception e) {
			System.out.println("文件格式有误");
		}

		long endTime = System.currentTimeMillis();
		System.out.println("方法的运行时间：" + String.valueOf(endTime - starttimes) + "ms");
		return "person/fileUpload";
	}

	@GetMapping("/exportProduct")
	public String exportProduct(ModelMap modelMap, HttpServletRequest request) {
		List<ExcelExportEntity> entityList = new ArrayList<ExcelExportEntity>();
		entityList.add(new ExcelExportEntity("产品名称", "title", 40));
		entityList.add(new ExcelExportEntity("产品单价", "price", 20));
		entityList.add(new ExcelExportEntity("所属区域", "area", 20));
		entityList.add(new ExcelExportEntity("运营商", "operator", 25));
		entityList.add(new ExcelExportEntity("产品类型", "ptypeName", 20));
		entityList.add(new ExcelExportEntity("产品状态", "statusName", 20));
		entityList.add(new ExcelExportEntity("添加时间", "addTime", 30));
		entityList.add(new ExcelExportEntity("产品描述", "discounts", 70));
		List<Map<String, String>> dataResult = getData();
		modelMap.put(MapExcelConstants.ENTITY_LIST, entityList);
		modelMap.put(MapExcelConstants.MAP_LIST, dataResult);
		modelMap.put(MapExcelConstants.FILE_NAME, "产品列表");
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams());
		return MapExcelConstants.EASYPOI_MAP_EXCEL_VIEW;
	}

	@GetMapping("/exportProduct2")
	public String exportProduct2(ModelMap modelMap, HttpServletRequest request) {

		List<Product> dataResult = pservice.findAllProduct();
		modelMap.put(NormalExcelConstants.CLASS, Product.class);
		modelMap.put(NormalExcelConstants.DATA_LIST, dataResult);
		modelMap.put(NormalExcelConstants.FILE_NAME, "产品列表");
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("产品列表", "产品列表"));
		return NormalExcelConstants.EASYPOI_EXCEL_VIEW;
	}

	public List<Map<String, String>> getData() {
		List<Map<String, String>> dataResult = new ArrayList<Map<String, String>>();
		List<Product> plist = pservice.findAllProduct();
		Map<String, String> map = null;
		for (Product product : plist) {
			map = new LinkedMap<String, String>();
			map.put("title", product.getTitle());
			map.put("price", product.getPrice() + "");
			map.put("area", product.getArea());
			map.put("operator", product.getOperator());
			map.put("ptypeName", product.getPtypeName());
			map.put("statusName", product.getStatusName());
			map.put("addTime", product.getAddTime().toString());
			map.put("discounts", product.getDiscounts());
			dataResult.add(map);

		}
		return dataResult;
	}
}
