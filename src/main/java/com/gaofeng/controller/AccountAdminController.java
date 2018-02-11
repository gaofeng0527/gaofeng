package com.gaofeng.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaofeng.pojo.Account;
import com.gaofeng.pojo.InstallMessage;
import com.gaofeng.pojo.Product;
import com.gaofeng.pojo.WxUser;
import com.gaofeng.service.AccountService;
import com.gaofeng.service.ProductService;
import com.gaofeng.service.WxUserService;
import com.gaofeng.utils.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 宽带帐号管理
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/home/account")
public class AccountAdminController {

	@Autowired
	private AccountService aservice;
	
	@Autowired
	private ProductService pservice;
	
	@Autowired
	private WxUserService wservice;

	// 全部列表
	@GetMapping("/index")
	public String index() {
		return "account/allList";
	}

	@ResponseBody
	@PostMapping("/findAccountByPage")
	public PageInfo findAccountByPage(int startPage,int pageSize) {
		PageHelper.startPage(startPage, pageSize);
		List<Account> plist = aservice.findAccounts();
		PageInfo pageInfo = new PageInfo(plist);
		return pageInfo;
	}
	
	/**
	 * 条件查询
	 * @param uname
	 * @param uphone
	 * @param area
	 * @param status
	 * @param startPage
	 * @param pageSize
	 * @return
	 */
	@ResponseBody
	@PostMapping("/search")
	public PageInfo search(String uname, String uphone, String area, Integer status, int startPage, int pageSize) {
		Account account = new Account();
		if (StringUtil.isNotNull(uname)) {
			account.setAccountName(uname);
		}
		if (StringUtil.isNotNull(uphone)) {
			account.setPhone(uphone);
		}
		if (StringUtil.isNotNull(area)) {
			account.setArea(area);
		}
		account.setStatus(null != status ? status : -1);
		PageHelper.startPage(startPage, pageSize);
		List<Account> mlist = aservice.findByAccount(account);
		PageInfo pageInfo = new PageInfo(mlist);
		return pageInfo;
	}


	/**
	 * 显示单个详情
	 * @param model
	 * @return
	 */
	@GetMapping("/details/{id}")
	public String details(@PathVariable("id") Long id,Model model) {
		//查询宽带本身信息
		Account account = aservice.findOneByAccountId(id);
		
		//查询产品信息
		Product product = new Product();
		if(null != account && null != account.getProductId()) {
			product = pservice.findOneByProductId(account.getProductId());
		}
		/*//查询用户信息
		WxUser wxUser = new WxUser();
		if(null != account && null != account.getUid()) {
			wxUser = wservice.findOneByuId(account.getUid());
		}*/
		model.addAttribute("account", account);
		model.addAttribute("product", product);
		//model.addAttribute("wxUser", wxUser);
		return "/account/accountDetails";
	}

	// 修改详情

	//

}
