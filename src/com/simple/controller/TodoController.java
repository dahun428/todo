package com.simple.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.simple.dto.TodoDto;
import com.simple.service.TodoService;
import com.simple.util.NumberUtil;
import com.simple.util.StringUtil;
import com.simple.vo.Pagenation;
import com.simple.vo.Todo;
import com.simple.vo.User;

import kr.co.jhta.mvc.annotation.Controller;
import kr.co.jhta.mvc.annotation.RequestMapping;
import kr.co.jhta.mvc.servlet.ModelAndView;
import kr.co.jhta.mvc.view.JSONView;

@Controller
public class TodoController {

	private JSONView jsonView = new JSONView();
	private TodoService todoService = new TodoService();
	
	@RequestMapping("/todos.hta")
	public ModelAndView todoMain(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("loginedUser");
		
		// session 값에 user가 없을 경우 home 으로 보낸다.
		if(user == null) {
			mav.setViewName("redirect:../home.hta");
			return mav;
		}
		
		String status = StringUtil.nullToBlank(req.getParameter("status"));
		String keyword = StringUtil.nullToBlank(req.getParameter("keyword"));

		Todo todo = new Todo();
		todo.setUserId(user.getId());
		todo.setStatus(status);

		//pagenation
		int rowsPerPage = NumberUtil.stringToInt(req.getParameter("rows"), 5);
		int pagesPerBlock = 5;
		int pageNo = NumberUtil.stringToInt(req.getParameter("page"), 1);
		int totalRows = todoService.getAllTodosPaginationByTodoCount(todo, keyword);
		Pagenation pagenation = new Pagenation(rowsPerPage, pagesPerBlock, pageNo, totalRows, keyword);
		
		//List
		List<Todo> todos = todoService.getAllTodosPaginationByTodo(todo, pagenation);

		mav.addAttribute("keyword", keyword);
		mav.addAttribute("status", status);
		mav.addAttribute("rows", rowsPerPage);
		mav.addAttribute("pagenation", pagenation);
		mav.addAttribute("todoList", todos);
		mav.setViewName("todos.jsp");
		return mav;
	}
	@RequestMapping("/add.hta")
	public ModelAndView addNewTodo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("loginedUser");
		System.out.println(user);
		
		if(user == null) {
			mav.addAttribute("result", "noId");
			mav.setView(jsonView);
			return mav;
		}
		
		String title = req.getParameter("title");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date day =  df.parse(req.getParameter("day"));
		String content = req.getParameter("content");
		String userId = user.getId();
		
		Todo todo = new Todo();
		todo.setTitle(title);
		todo.setDay(day);
		todo.setContent(content);
		todo.setUserId(userId);
		
		System.out.println(todo);
		boolean isSuccess = todoService.addNewTodo(todo);
		mav.addAttribute("result", isSuccess);
		mav.setView(jsonView);
		
		return mav;
	}
	@RequestMapping("/get.hta")
	public ModelAndView getTodoDtoByNo(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		ModelAndView mav = new ModelAndView();
		int todoNo = NumberUtil.stringToInt(req.getParameter("todoNo"));
		TodoDto todo = todoService.getTodoDtoByNo(todoNo);
		
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("loginedUser");
		if(user != null && user.getId().equals(todo.getUserId())) {
			todo.setModifyCan(true);
		}
		System.out.println(todo);
		mav.addAttribute("todo", todo);
		mav.setView(jsonView);
		
		return mav;
	}
	@RequestMapping("/update.hta")
	public ModelAndView updateTodo(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		ModelAndView mav = new ModelAndView();
		int todoNo = NumberUtil.stringToInt(req.getParameter("todoNo"));
		String status = req.getParameter("status");
		System.out.println(status);
		System.out.println("todoNo : " + todoNo);
		todoService.updateTodoByNo(status, todoNo);
		
		mav.setView(jsonView);
		return mav;
	}
	
}
