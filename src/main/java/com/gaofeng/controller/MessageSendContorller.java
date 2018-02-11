package com.gaofeng.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaofeng.pojo.Result;
import com.gaofeng.utils.HttpSender;
import com.gaofeng.utils.StringUtil;

/**
 * 短信验证类
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/msg")
public class MessageSendContorller {

	/**
	 * 给手机号发短信
	 * 
	 * @param phone
	 * @param model
	 * @param session
	 * @return
	 */
	@ResponseBody
	@PostMapping("/send")
	public Result sendMsg(String phone, Model model, HttpSession session,HttpServletRequest request,HttpServletResponse response) {
		Integer counts = 1;
		Cookie[] cookie = request.getCookies();
		Cookie co = null;
		if(null != cookie) {
			for (Cookie cookie2 : cookie) {
				if(cookie2.getName().equals("phone"+phone)) {
					co = cookie2;
					
					System.out.println(co.getValue());
					counts = Integer.parseInt(co.getValue());
				}
			}
		}
		if(null == co){
			co = new Cookie("phone"+phone, counts+"");
			co.setMaxAge(3600);
			co.setPath("/");
			response.addCookie(co);
		}
		
		Result res = new Result();
		res.setCode(1);
		
		if (counts > 5) {
			res.setCode(2);
			return res;
		}
		String code = StringUtil.getRandomCode();
		String msg = StringUtil.encodeHexString(15, "【东红天科技】尊敬的用户你好，这是你的验证码" + code + "，请尽快输入验证码");// 短信内容
		System.out.println(msg);
		
		try {
			String returnString = HttpSender.batchSend(phone, msg);
			session.setAttribute("phone"+phone, code);
			co.setValue(++counts+"");
			co.setPath("/");
			response.addCookie(co);
			System.out.println(returnString);
			// TODO 处理返回值,参见HTTP协议文档
		} catch (Exception e) {
			// TODO 处理异常
			res.setCode(0);
			e.printStackTrace();
		}
		System.out.println(phone);

		return res;
	}
	
	@ResponseBody
	@PostMapping("/checkCode")
	public Result checkCode(String code,String phone,Model model,HttpSession session) {
		String vcode  = (String) session.getAttribute("phone"+phone);
		Result result = new Result();
		if(null == vcode || "".equals(vcode)) {
			result.setCode(0);//验证码过期
		}
		
		if(vcode.equals(code)) {
			result.setCode(2);//验证成功
		}else {
			result.setCode(1);//验证码错误
		}
		return result;
	}
}
