package com.simple.service;

import java.sql.SQLException;
import java.util.List;

import com.simple.dao.TodoDAO;
import com.simple.dao.UserDAO;
import com.simple.dto.TodoDto;
import com.simple.vo.Pagenation;
import com.simple.vo.Todo;
import com.simple.vo.User;

public class TodoService {

	UserDAO userDao = new UserDAO();
	TodoDAO todoDao = new TodoDAO();
	public boolean addNewTodo(Todo todo) throws Exception {
	
		User user = userDao.getUserById(todo.getUserId());
		if(user == null) {
			return false;
		}
		if(todo.getTitle() == null || todo.getContent() == null || todo.getDay() == null) {
			return false;
		}
		
		todoDao.insertTodo(todo);
		return true;
	}
//	public List<Todo> getAllTodoByUserId(String userId) throws Exception {
//		return todoDao.getAllTodoByUserId(userId);
//	}
//	public List<Todo> getAllTodoPagination(String userId, int beginIndex, int endIndex) throws Exception {
//		return todoDao.getAllTodoPagination(userId, beginIndex, endIndex);
//	}
//	public List<Todo> getAllTodoPaginationWithStatus(String userId, int beginIndex, int endIndex, String status) throws Exception {
//		return todoDao.getAllTodoPaginationWithStatus(userId, beginIndex, endIndex, status);
//	}
//	public int getAllTodoPaginationWithStatusCount(String userId, String status) throws Exception {
//		return todoDao.getAllTodoPaginationWithStatusCount(userId, status);
//	}
//	public List<Todo> getAllTodoPaginationWithStatusAndKeyword(String userId, int beginIndex, int endIndex, String status, String keyword) throws Exception {
//		return todoDao.getAllTodoPaginationWithStatusAndKeyword(userId, beginIndex, endIndex, status, keyword);
//	}
//	public int getAllTodoPaginationWithStatusAndKeywordCount(String userId, String status, String keyword) throws Exception {
//		return todoDao.getAllTodoPaginationWithStatusAndKeywordCount(userId, status, keyword);
//	}
//	
//	public int getAllTodoPaginationCount(String userId) throws Exception {
//		return todoDao.getAllTodoPaginationCount(userId);
//	}
	public List<TodoDto> getRecentTodos () throws Exception {
		return todoDao.getRecentTodos();
	}
	public TodoDto getTodoDtoByNo(int todoNo) throws Exception {
		return todoDao.getTodoDtoByNo(todoNo);
	}
//	public int getAllTodosPaginationCount(String userId, String status, String keyword) throws SQLException {
//		return todoDao.getAllTodosPaginationCount(userId, status, keyword);
//	}
//	public List<Todo> getAllTodosPagination(String userId, int beginIndex, int endIndex, String status, String keyword) throws SQLException {
//		return todoDao.getAllTodosPagination(userId, beginIndex, endIndex, status, keyword);
//	}
	public List<Todo> getAllTodosPaginationByTodo(Todo todo, Pagenation pagenation) throws SQLException {
		return todoDao.getAllTodosPagination(todo.getUserId(), pagenation.getBeginIndex(), pagenation.getEndIndex(), todo.getStatus(), pagenation.getKeyword());
	}
	public int getAllTodosPaginationByTodoCount(Todo todo, String keyword) throws SQLException {
		return todoDao.getAllTodosPaginationCount(todo.getUserId(), todo.getStatus(), keyword);
	}
	public void updateTodoByNo(String status, int todoNo) throws SQLException {
		todoDao.updateTodoByNo(status, todoNo);
	}
}
