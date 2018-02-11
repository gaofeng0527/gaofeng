package com.gaofeng.controller.wx;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gaofeng.pojo.Account;
import com.gaofeng.pojo.InstallMessage;
import com.gaofeng.pojo.Order;
import com.gaofeng.pojo.OrderItem;
import com.gaofeng.pojo.Product;
import com.gaofeng.pojo.WxUser;
import com.gaofeng.service.AccountService;
import com.gaofeng.service.InstallMessageService;
import com.gaofeng.service.OrderItemService;
import com.gaofeng.service.OrderService;
import com.gaofeng.service.ProductService;
import com.gaofeng.service.WxUserService;
import com.gaofeng.service.wx.WeixinService;

import ch.qos.logback.classic.Logger;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("/open/admin")
public class WxUserAdminController {
	private static Logger logger = (Logger) LoggerFactory.getLogger(WxInstallController.class);
	@Autowired
	private WeixinService wxService;

	@Autowired
	private WxUserService uservice;

	@Autowired
	private AccountService aservice;
	@Autowired
	private ProductService pservice;
	@Autowired
	private OrderService oservice;
	@Autowired
	private OrderItemService oiservice;
	@Autowired
	private InstallMessageService iservice;

	/**
	 * 打开个人中心连接菜单
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/redirect")
	public String weixinRedirect() throws UnsupportedEncodingException {
		String encodeUrl = wxService.oauth2buildAuthorizationUrl("http://gaof.edu-edu.com/gaofeng/open/admin/index",
				"snsapi_base", "1");
		return "redirect:" + encodeUrl;
	}

	/**
	 * 1.获得授权，获取用户openid，根据openid获取数据库中的用户信息，存储到session中 2.解决刷新问题
	 * 
	 * @param model
	 * @param request
	 * @param session
	 * @return
	 * @throws WxErrorException
	 */
	@GetMapping("/index")
	public String index(Model model, HttpServletRequest request, HttpSession session) throws WxErrorException {

		String code = request.getParameter("code");
		WxMpOAuth2AccessToken oauth = null;
		if (null == session.getAttribute("code") || "".equals(session.getAttribute("code"))) {
			session.setAttribute("code", code);
			oauth = wxService.oauth2getAccessToken(code);
			session.setAttribute("oauth", oauth);
		} else {
			oauth = (WxMpOAuth2AccessToken) session.getAttribute("oauth");
			oauth = wxService.oauth2refreshAccessToken(oauth.getRefreshToken());
			if (null == oauth) {
				oauth = wxService.oauth2getAccessToken(code);
				session.setAttribute("code", code);
			}
			session.setAttribute("oauth", oauth);
		}
		logger.debug(oauth.getOpenId() + " 用户访问了个人中心");
		session.setAttribute("openid", oauth.getOpenId());
		WxUser user = uservice.findWxUserByOpenId(oauth.getOpenId());
		model.addAttribute("user", user);
		return "/wx/index";
	}

	@GetMapping("/editUser")
	public String editUser(Model model, HttpSession session) {
		String openid = (String) session.getAttribute("openid");
		if (null == openid || "".equals(openid)) {
			return "redirect:redirect";
		}
		WxUser user = uservice.findWxUserByOpenId(openid);
		model.addAttribute("wxuser", user);
		return "/wx/editUser";
	}

	/**
	 * 宽带续费
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/renew")
	public String renew(Model model, HttpSession session) {
		String openid = (String) session.getAttribute("openid");
		if (null == openid || "".equals(openid)) {
			return "redirect:redirect";
		}
		logger.debug(openid + " 用户访问了个人中心>宽带续费");
		WxUser user = uservice.findWxUserByOpenId(openid);
		List<Account> accounts = user.getAccounts();
		if (null != accounts && accounts.size() > 1) {
			model.addAttribute("accounts", accounts);
			return "/wx/accounts";
		} else if (null != accounts && accounts.size() == 1) {
			Account account = accounts.get(0);
			model.addAttribute("account", account);
			if (null == user.getUphone() || "".equals(user.getUphone())) {
				user.setUphone(account.getPhone());
			}
			if (null == user.getUname() || "".equals(user.getUname())) {
				user.setUname(account.getAccountName());
			}
			if (null == user.getAddress() || "".equals(user.getAddress())) {
				user.setAddress(account.getAddress());
			}

			model.addAttribute("wxUser", user);
			model.addAttribute("product", account.getProduct());
			return "/wx/account";
		} else {
			return "/wx/error";
		}
	}

	/**
	 * 确定报装
	 * 
	 * @param wxUser
	 * @param session
	 * @param request
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	@PostMapping("/addInstall")
	public String addInstall(WxUser wxUser, HttpSession session, HttpServletRequest request) {
		// 1.补全用户信息
		String openid = (String) session.getAttribute("openid");
		if (null == openid) {
			return "redirect:/open/install/redirect";
		}
		String pid = request.getParameter("productid");
		if (null == pid || "".equals(pid)) {
			return "redirect:/open/install/redirect";
		}
		Product product = pservice.findOneByProductId(Long.parseLong(pid));
		if (null == product) {
			return "redirect:/open/install/redirect";
		}
		WxUser user = uservice.findWxUserByOpenId(openid);
		user.setAddress(null != wxUser.getAddress() ? wxUser.getAddress() : null);
		user.setCardId(null != wxUser.getCardId() ? wxUser.getCardId() : null);
		user.setUname(wxUser.getUname());
		user.setUphone(wxUser.getUphone());
		uservice.updateWxUserById(user);
		// 2.添加订单信息
		Order order = new Order();
		order.setUserId(user.getId());
		order.setModuleId("123456789");
		order.setOrderPrice(product.getPrice());
		order.setPhone(wxUser.getUphone());
		order.setUname(wxUser.getUname());
		order.setPayType(0);
		order.setOtype(0);
		oservice.addOrder(order);
		// 3.添加订单详情
		OrderItem item = new OrderItem();
		item.setOrderId(order.getId());
		item.setPrice(order.getOrderPrice());
		item.setProductId(product.getId());
		item.setTitle(product.getTitle());
		oiservice.addOrderItem(item);
		// 4.添加报装信息
		InstallMessage install = new InstallMessage();
		install.setOrderId(order.getId());
		install.setItemId(item.getId());
		install.setUserId(user.getId());
		install.setUname(user.getUname());
		install.setPhone(user.getUphone());
		install.setArea(product.getArea());
		install.setCity(product.getCity());
		install.setProvince(null != product.getProvince() ? product.getProvince() : null);
		install.setAddress(user.getAddress());
		install.setProductId(product.getId());
		install.setCompanyId(1L);
		iservice.addInstall(install);
		return "redirect:/open/install/success";
	}

	@GetMapping("/accountModel")
	public String accountModel(@RequestParam(value="id") Long accountId, HttpSession session, Model model) {
		// 1.补全用户信息
		String openid = (String) session.getAttribute("openid");
		if (null == openid) {
			return "redirect:/open/install/redirect";
		}
		Account account = aservice.findAccountById(accountId);
		WxUser user = uservice.findWxUserByOpenId(openid);
		model.addAttribute("account", account);
		if (null == user.getUphone() || "".equals(user.getUphone())) {
			user.setUphone(account.getPhone());
		}
		if (null == user.getUname() || "".equals(user.getUname())) {
			user.setUname(account.getAccountName());
		}
		if (null == user.getAddress() || "".equals(user.getAddress())) {
			user.setAddress(account.getAddress());
		}

		model.addAttribute("wxUser", user);
		model.addAttribute("product", account.getProduct());

		return "/wx/account";
	}

	@GetMapping("/index2")
	public String test(HttpSession session) {
		session.setAttribute("openid", "o_Zr30vj7NlHl5ABxeT0u1NQhlSE");
		return "/wx/index";
	}

	@GetMapping("/index3")
	public String accounts() {
		return "/wx/accounts";
	}

}
