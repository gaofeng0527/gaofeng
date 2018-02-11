package com.gaofeng.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gaofeng.pojo.Person;
import com.gaofeng.pojo.Sysuser;
import com.gaofeng.service.PersonService;

@Controller
public class IndexController {

	@Resource(name = "pesonService")
	private PersonService pservice;

	@GetMapping("/index")
	public String index(Model model,HttpSession session) {
		String openId = (String) session.getAttribute("formUser");
		System.out.println("openid:"+openId);
		if(null == session.getAttribute("loginUser")) {
			return "/login";
		}
		return "index";
	}
	
	@GetMapping("/main")
	public String init() {
		return "/main";
	}
	
	
	
	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	@PostMapping("/login")
	public String login(Sysuser user,HttpSession session) {
		if(null == user) {
			return "/login";
		}
		if(!"xiaoma".equals(user.getLogin()) || !"xiaoma".equals(user.getSysPwd()))
		{
			return "/login";
		}
		session.setAttribute("loginUser", user);
		return "index";
	}
	

	@GetMapping("/test/add")
	public String addPerson(Model model) {
		Person p = new Person();
		p.setId(1L);
		p.setUname("张高峰");
		p.setEmail("gaofeng0527@163.com");
		pservice.save(p);
		model.addAttribute("message", "您已经成功添加一个用户");
		return "index";
	}

	@GetMapping("/dome/{id}")
	@ResponseBody
	public Person findPerson(@PathVariable(value = "id") Long id) {
		return pservice.findPersonById(id);
	}
}
