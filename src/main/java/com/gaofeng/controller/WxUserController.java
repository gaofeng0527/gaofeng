package com.gaofeng.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaofeng.pojo.Product;
import com.gaofeng.pojo.Result;
import com.gaofeng.pojo.WxUser;
import com.gaofeng.service.WxUserService;
import com.gaofeng.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;

@Controller
@RequestMapping("/home/wxuser")
public class WxUserController {
	
	@Autowired
	private WxUserService uservice;
	
	@GetMapping("/index")
	public String index(Model model) {
		WxUser user = new WxUser();
		model.addAttribute("wxuser", user);
		return "wxuser/wxuserList";
	}
	
	@ResponseBody
	@PostMapping("/findUserByPage")
	public PageInfo findUserByPage(int startPage, int pageSize) {
		PageHelper.startPage(startPage, pageSize);
		List<WxUser> ulist = uservice.findAll();
		PageInfo pageInfo = new PageInfo(ulist);
		return pageInfo;
	}
	
	@GetMapping("/wxUserEdit/{wxUserId}")
	public String wxUserEdit(@PathVariable("wxUserId") Long wxUserId, Model model) {
		WxUser wxuser = uservice.findOneByuId(wxUserId);
		model.addAttribute("wxuser", wxuser);
		return "wxuser/wxuserEdit";
	}
	
	@ResponseBody
	@PostMapping("/search")
	public PageInfo search(String uname,String uphone,String area,Integer status,int startPage,int pageSize) {
		WxUser user = new WxUser();
		if(StringUtil.isNotNull(uname)) {
			user.setUname(uname);
		}
		if(StringUtil.isNotNull(uphone)) {
			user.setUphone(uphone);
		}
		if(StringUtil.isNotNull(area)) {
			user.setArea(area);
		}
		
		user.setStatus(null != status? status:-1);
		
		PageHelper.startPage(startPage, pageSize);
		List<WxUser> ulist = uservice.findByWxUser(user);
		PageInfo pageInfo = new PageInfo(ulist);
		return pageInfo;
	}
	
	@PostMapping("/wxUserEdit")
	@ResponseBody
	public Result wxUserEdit(@RequestBody WxUser wxUser) {
		Result result = new Result();
		if(null == wxUser || null == wxUser.getId()) {
			result.setSuccess(false);
			result.setMessage("保存失败：用户ID信息不完整");
			result.setCode(1);
		}else {
			WxUser user = uservice.findOneByuId(wxUser.getId());
			user.setUname(wxUser.getUname());
			user.setAddress(wxUser.getAddress());
			user.setArea(wxUser.getArea());
			user.setCardId(null != wxUser.getCardId()?wxUser.getCardId():"");
			user.setUphone(wxUser.getUphone());
			user.setStatus(wxUser.getStatus());
			uservice.updateWxUserById(user);
		}
		
		
		
		return result;
	}
	
	@GetMapping("/exportWxUser")
	public String exportWxUser(ModelMap modelMap, HttpServletRequest request) {

		List<WxUser> ulist = uservice.findAll();
		modelMap.put(NormalExcelConstants.CLASS, WxUser.class);
		modelMap.put(NormalExcelConstants.DATA_LIST, ulist);
		modelMap.put(NormalExcelConstants.FILE_NAME, "用户信息列表");
		modelMap.put(NormalExcelConstants.PARAMS, new ExportParams());
		return NormalExcelConstants.EASYPOI_EXCEL_VIEW;
	}


	
}
