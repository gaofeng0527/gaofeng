package com.gaofeng.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaofeng.pojo.Account;
import com.gaofeng.pojo.InstallMessage;
import com.gaofeng.pojo.Product;
import com.gaofeng.pojo.Result;
import com.gaofeng.pojo.WxUser;
import com.gaofeng.service.AccountService;
import com.gaofeng.service.InstallMessageService;
import com.gaofeng.service.ProductService;
import com.gaofeng.service.WxUserService;
import com.gaofeng.utils.DateUtil;
import com.gaofeng.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;

@Controller
@RequestMapping("/home/install")
public class InstallMgsController {

	@Autowired
	private InstallMessageService iservice;

	@Autowired
	private ProductService pservice;
	@Autowired
	private WxUserService uservice;

	@Autowired
	private AccountService aservice;

	@GetMapping("/index")
	public String index() {
		return "install/allList";
	}

	@ResponseBody
	@PostMapping("/findInstallByPage")
	public PageInfo findInstallByPage(int startPage, int pageSize) {
		PageHelper.startPage(startPage, pageSize);
		List<InstallMessage> mlist = iservice.findAllInstallMes(1L);
		PageInfo pageInfo = new PageInfo(mlist);
		return pageInfo;
	}

	@ResponseBody
	@PostMapping("/search")
	public PageInfo search(String uname, String uphone, String area, Integer status, int startPage, int pageSize) {
		InstallMessage install = new InstallMessage();
		if (StringUtil.isNotNull(uname)) {
			install.setUname(uname);
		}
		if (StringUtil.isNotNull(uphone)) {
			install.setPhone(uphone);
		}
		if (StringUtil.isNotNull(area)) {
			install.setArea(area);
		}
		install.setStatus(null != status ? status : -1);
		PageHelper.startPage(startPage, pageSize);
		List<InstallMessage> mlist = iservice.findByInstall(install);
		PageInfo pageInfo = new PageInfo(mlist);
		return pageInfo;
	}

	@GetMapping("/installAdd")
	public String installAdd(Model model) {
		List<Product> plist = pservice.findAllProduct();
		Product product = new Product();
		if (null != plist && plist.size() > 0) {
			product = plist.get(0);
		}
		WxUser wxUser = new WxUser();
		model.addAttribute("wxUser", wxUser);
		model.addAttribute("product", product);
		return "install/installAdd";
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@PostMapping("/addInstall")
	@ResponseBody
	public Result addInstall(@RequestBody InstallMessage mess, HttpServletRequest request) {
		long productId = getProductId(request);
		System.out.println(productId);
		mess.setCreateTime(new Date());
		mess.setOrigin("manually");
		mess.setCompanyId(1L);
		System.out.println(mess.toString());
		Result result = new Result();
		try {
			iservice.addInstall(mess);
			result.setCode(0);
			result.setMessage("保存成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setCode(1);
			result.setMessage("保存失败");
			result.setSuccess(false);
		}
		return result;
	}

	public Long getProductId(HttpServletRequest request) {
		long productId = 0L;
		if (null != request.getParameter("productId")) {
			productId = Long.parseLong(request.getParameter("productId"));
		}
		return productId;
	}

	@GetMapping("/exportInstall")
	public String exportInstall(ModelMap modelMap, HttpServletRequest request) {

		List<InstallMessage> dataResult = iservice.findAllInstallMes(1L);
		modelMap.put(NormalExcelConstants.CLASS, InstallMessage.class);
		modelMap.put(NormalExcelConstants.DATA_LIST, dataResult);
		modelMap.put(NormalExcelConstants.FILE_NAME, "装机信息列表");
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("装机信息列表", "装机信息列表"));
		return NormalExcelConstants.EASYPOI_EXCEL_VIEW;
	}

	@ResponseBody
	@GetMapping("/accept/{id}")
	public Result updateStatus(@PathVariable("id") Long id) {
		Result result = new Result();
		InstallMessage install = iservice.findInstallById(id);
		if (null == install) {
			result.setCode(1);
			result.setSuccess(false);
			result.setMessage("没有找到对应信息，请刷新后再操作");
			return result;
		}
		install.setStatus(1);
		System.out.println(install);

		try {
			iservice.updateStatus(install);
			result.setCode(0);
			result.setMessage("受理成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setCode(1);
			result.setMessage("受理失败，请稍后再试");
			result.setSuccess(false);
		}
		return result;
	}

	@GetMapping("/installEdit/{id}")
	public String productEdit(@PathVariable("id") Long id, Model model) {
		InstallMessage install = iservice.findInstallById(id);
		Product product = pservice.findOneByProductId(install.getProductId());
		model.addAttribute("product", product);
		model.addAttribute("install", install);
		return "install/installEdit";
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@PostMapping("/installEdit")
	@ResponseBody
	public Result installEdit(@RequestBody InstallMessage mess, HttpServletRequest request) {
		int status = mess.getStatus();
		Result result = new Result();
		boolean success = true;
		if (status == 2) {
			// 添加宽带帐号
			success = addAccount(mess);
		}
		if(!success) {
			result.setCode(1);
			result.setMessage("保存失败，请确定是否填写宽带帐号或者开通日期");
			result.setSuccess(false);
			return result;
		}
		
		try {
			iservice.updateInstall(mess);
			result.setCode(0);
			result.setMessage("保存成功");
			result.setSuccess(true);
		} catch (Exception e) {
			result.setCode(1);
			result.setMessage("保存失败");
			result.setSuccess(false);
		}
		return result;
	}

	public boolean addAccount(InstallMessage mess) {
		InstallMessage install = iservice.findInstallById(mess.getId());
		String accountCode = mess.getAccount();
		if (StringUtil.isNull(accountCode)) {
			return false;
		}
		Date vdate = mess.getVdate();
		if (null == vdate) {
			return false;
		}
		System.out.println(vdate);
		Account account = new Account();
		account.setAccountCode(accountCode);

		Date endTime = DateUtil.addYear(vdate, 1);
		account.setBeginTime(vdate);
		account.setInstallId(mess.getId());
		account.setEndTime(endTime);
		account.setAccountName(mess.getUname());
		account.setAddress(mess.getAddress());
		account.setArea(install.getArea());
		account.setCity(null != install.getCity() ? install.getCity() : null);
		account.setProvince(null != install.getProvince() ? install.getProvince() : null);
		account.setCompany(null);
		account.setCompanyId(1L);
		account.setPhone(mess.getPhone());
		account.setProductId(install.getProductId());
		account.setUid(null != install.getUserId() ? install.getUserId() : null);
		try {
			aservice.addAccount(account);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
