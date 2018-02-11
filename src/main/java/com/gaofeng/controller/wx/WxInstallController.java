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
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaofeng.pojo.InstallMessage;
import com.gaofeng.pojo.Order;
import com.gaofeng.pojo.OrderItem;
import com.gaofeng.pojo.Product;
import com.gaofeng.pojo.WxUser;
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
@RequestMapping("/open/install")
public class WxInstallController {
	private static Logger logger = (Logger) LoggerFactory.getLogger(WxInstallController.class);
	@Autowired
	private WeixinService wxService;
	@Autowired
	private ProductService pservice;
	@Autowired
	private WxUserService uservice;
	@Autowired
	private OrderService oservice;
	@Autowired
	private OrderItemService oiservice;
	@Autowired
	private InstallMessageService iservice;

	/**
	 * 打开宽带报装连接菜单
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/redirect")
	public String weixinRedirect() throws UnsupportedEncodingException {
		String encodeUrl = wxService.oauth2buildAuthorizationUrl(
				"http://gaof.edu-edu.com/gaofeng/open/install/addInstallMagess", "snsapi_base", "1");
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
	@GetMapping("/addInstallMagess")
	public String addInstallMessage(Model model, HttpServletRequest request, HttpSession session)
			throws WxErrorException {

		String code = request.getParameter("code");
		WxMpOAuth2AccessToken oauth = null;
		if (null == session.getAttribute("code") || "".equals(session.getAttribute("code"))) {
			session.setAttribute("code", code);
			oauth = wxService.oauth2getAccessToken(code);
			session.setAttribute("oauth", oauth);
		} else {
			oauth = (WxMpOAuth2AccessToken) session.getAttribute("oauth");
			oauth = wxService.oauth2refreshAccessToken(oauth.getRefreshToken());
			if(null == oauth) {
				oauth = wxService.oauth2getAccessToken(code);
				session.setAttribute("code", code);
			}
			session.setAttribute("oauth", oauth);
		}
		logger.debug(code);
		logger.debug(session.getAttribute("code") + "");
		session.setAttribute("openid", oauth.getOpenId());
		List<Product> plist = pservice.findAllProduct();
		Product product = new Product();
		if(null != plist && plist.size()>0) {
			product=plist.get(0);
		}
		model.addAttribute("product", product);
		return "/wx/installAdd";
	}

	/**
	 * 加载页面套餐
	 * 
	 * @param type
	 * @param area
	 * @param operator
	 * @return
	 */
	@ResponseBody
	@PostMapping("/loadProduct")
	public List<Product> loadProduct(int type, String area, String operator) {
		Product product = new Product();
		product.setType(type);
		product.setArea(area);
		product.setOperator(operator);
		product.setCompanyId(1L);
		List<Product> plist = pservice.findProductByTypeAndArea(product);
		return plist;
	}

	/**
	 * 下一步
	 * 
	 * @param product
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping("/next")
	public String next(Product product, HttpSession session, Model model) {
		logger.debug(product.toString());
		Product p = pservice.findOneByProductId(product.getId());
		String key = (String) session.getAttribute("openid");
		session.setAttribute(key + "_product", p);
		String provinceAndArea = p.getCity();
		model.addAttribute("city", provinceAndArea);
		WxUser wxUser = (WxUser) session.getAttribute(key+"_wxUser");
		if(null == wxUser) {
			wxUser = new WxUser();
		}
		model.addAttribute("wxUser", wxUser);
		return "/wx/installNext";
	}

	/**
	 * 第一次上一步 返回到套餐选择页面 把之前存储的用户选择过的产品信息显示出来 如果session中openid为空的话，就重定向到菜单起始请求中
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/backOne")
	public String backOne(HttpSession session, Model model) {
		String openid = (String) session.getAttribute("openid");
		if (null == openid || "".equals(openid)) {
			return "redirect:redirect";
		}
		Product product = (Product) session.getAttribute(openid + "_product");
		if (null != product) {
			model.addAttribute("product", product);
		}
		return "/wx/installAdd";
	}

	/**
	 * 确认信息返回
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/backTwo")
	public String backTwo(HttpSession session, Model model) {
		String openid = (String) session.getAttribute("openid");
		if (null == openid || "".equals(openid)) {
			return "redirect:redirect";
		}
		WxUser wxUser = (WxUser) session.getAttribute(openid + "_wxUser");
		if (null != wxUser) {
			model.addAttribute("wxUser", wxUser);
		}
		Product product = (Product) session.getAttribute(openid + "_product");
		if (null == product) {
			return "redirect:redirect";
		} else {
			String provinceAndArea = product.getCity();
			model.addAttribute("city", provinceAndArea);
		}
		return "/wx/installNext";
	}

	/**
	 * 确认信息
	 * 
	 * @param wxUser
	 * @param session
	 * @param model
	 * @return
	 */
	@PostMapping("/verify")
	public String verify(WxUser wxUser, HttpSession session, Model model) {
		String openid = (String) session.getAttribute("openid");
		if (null == openid || "".equals(openid)) {
			return "redirect:redirect";
		}
		Product product = (Product) session.getAttribute(openid + "_product");
		logger.debug(wxUser.toString());
		session.setAttribute(openid + "_wxUser", wxUser);
		model.addAttribute("product", product);
		return "/wx/install";
	}

	/**
	 * 确定报装
	 * 
	 * @param wxUser
	 * @param session
	 * @param request
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	@PostMapping("/addInstall")
	public String addInstall(WxUser wxUser, HttpSession session, HttpServletRequest request) {
		// 1.补全用户信息
		String openid = (String) session.getAttribute("openid");
		if(null == openid) {
			return "redirect:redirect";
		}
		Product product = (Product) session.getAttribute(openid + "_product");
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
		return "redirect:success;";
	}

	@GetMapping("/test")
	public String test(HttpSession session,Model model) {
		session.setAttribute("openid", "o_Zr30vj7NlHl5ABxeT0u1NQhlSE");
		Product product = pservice.findOneByProductId(1L);
		model.addAttribute("product", product);
		return "/wx/installAdd";
	}

	@GetMapping("/next")
	public String nexts() {
		return "/wx/installNext";
	}

	@GetMapping("/index")
	public String index() {
		return "/wx/install";
	}

	@GetMapping("/success")
	public String success(HttpSession session) {
		session.setAttribute("openid", null);
		return "/wx/installSuccess";
	}
	
	/**
	 * 第一次上一步 返回到套餐选择页面 把之前存储的用户选择过的产品信息显示出来 如果session中openid为空的话，就重定向到菜单起始请求中
	 * 
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/backOnec")
	public String backOneChrome(HttpSession session, Model model) {
		Product product = pservice.findOneByProductId(16L);
		if (null != product) {
			model.addAttribute("product", product);
		}
		return "/wx/installAdd";
	}
}
