package com.gaofeng.controller.wx;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gaofeng.pojo.Account;
import com.gaofeng.pojo.WxUser;
import com.gaofeng.service.AccountService;
import com.gaofeng.service.WxUserService;
import com.gaofeng.service.wx.WeixinService;

import ch.qos.logback.classic.Logger;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;

@Controller
@RequestMapping("/open/account")
public class AccountController {

	private static Logger logger = (Logger) LoggerFactory.getLogger(WxInstallController.class);
	@Autowired
	private WeixinService wxService;

	@Autowired
	private WxUserService uservice;
	
	@Autowired
	private AccountService aservice;

	/**
	 * 打开个人中心连接菜单
	 * 
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@GetMapping("/redirect")
	public String weixinRedirect() throws UnsupportedEncodingException {
		String encodeUrl = wxService.oauth2buildAuthorizationUrl("http://gaof.edu-edu.com/gaofeng/open/account/index",
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
		logger.debug(oauth.getOpenId() + " 用户访问了个人中心>宽带续费");
		session.setAttribute("openid", oauth.getOpenId());
		WxUser user = uservice.findWxUserByOpenId(oauth.getOpenId());
		List<Account> accounts = aservice.findAccountByUserId(user.getId());
		if(null != accounts && accounts.size()>0) {
			model.addAttribute("accounts", accounts);
			return "/wx/account_list";
		}else if(null != accounts && accounts.size()==1){
			model.addAttribute("account", accounts.get(0));
			return "/wx/account";
		}else {
			return "/wx/error";
		}
		
	}

}
