package com.simple.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.simple.dto.TodoDto;
import com.simple.service.TodoService;
import com.simple.util.NumberUtil;

import kr.co.jhta.mvc.annotation.Controller;
import kr.co.jhta.mvc.annotation.RequestMapping;
import kr.co.jhta.mvc.servlet.ModelAndView;
import kr.co.jhta.mvc.view.JSONView;

@Controller
public class HomeController {

	private TodoService todoService = new TodoService();
	private JSONView jsonView = new JSONView();
	@RequestMapping("/home.hta")
	public ModelAndView home(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView();
		List<TodoDto> dtos = todoService.getRecentTodos();
		
		mav.addAttribute("todoList", dtos);
		mav.setViewName("home.jsp");
		return mav;
	}
	@RequestMapping("/more.hta")
	public ModelAndView moreList(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView();
		int plusNum =  NumberUtil.stringToInt(req.getParameter("plusNum"));
		int beginIndex = plusNum + 1;
		int lastIndex = plusNum + 6;
		List<TodoDto> todos = todoService.getRecentTodosMore(beginIndex, lastIndex);
		System.out.println(beginIndex);
		mav.addAttribute("beginIndex", beginIndex);
		mav.addAttribute("lastIndex", lastIndex);
		mav.addAttribute("todoList", todos);
		mav.setView(jsonView);
		return mav;
	}
	
}
