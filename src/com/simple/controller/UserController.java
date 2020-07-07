package com.simple.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simple.service.UserService;
import com.simple.vo.User;

import kr.co.jhta.mvc.annotation.Controller;
import kr.co.jhta.mvc.annotation.RequestMapping;
import kr.co.jhta.mvc.servlet.ModelAndView;
import kr.co.jhta.mvc.view.JSONView;

@Controller
public class UserController {

	private JSONView jsonView = new JSONView();
	private UserService userService = new UserService();
	
	@RequestMapping("/register.hta")
	public ModelAndView signIn(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView();

		String name = req.getParameter("name");
		String id = req.getParameter("id");
		String password = req.getParameter("pwd");
		String email = req.getParameter("email");
		
		User user = new User();
		user.setName(name);
		user.setId(id);
		user.setPassword(password);
		user.setEmail(email);
		System.out.println(user);

		boolean isSuccess =  userService.addNewUser(user);
		System.out.println(isSuccess);
		mav.addAttribute("result", isSuccess);
		mav.setView(jsonView);
		
		return mav;
	}
	@RequestMapping("/login.hta")
	public ModelAndView login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView();
		boolean isSuccess = true;
		String id = req.getParameter("userid");
		String password = req.getParameter("userpwd");
		
		User user = userService.loginCheck(id, password);
		
		if(user == null) {
			isSuccess = false;
		}
		
		mav.addAttribute("result", isSuccess);
		
		if(isSuccess) {
			HttpSession session = req.getSession();
			session.setAttribute("loginedUser", user);
		}
		
		mav.setView(jsonView);
		
		return mav;
	}
	@RequestMapping("/logout.hta")
	public ModelAndView logout(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = req.getSession();
		session.invalidate();
		mav.setViewName("redirect:../home.hta");
		
		return mav;
	}
	
}
