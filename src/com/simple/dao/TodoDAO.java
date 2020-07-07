package com.simple.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.simple.util.JdbcHelper.RowMapper;
import com.simple.dto.TodoDto;
import com.simple.util.ConnectionUtil;
import com.simple.util.JdbcHelper;
import com.simple.util.QueryUtil;
import com.simple.vo.Todo;

public class TodoDAO {

	 public void insertTodo(Todo todo) throws SQLException {

		 String query = QueryUtil.getSQL("todo.insertTodo");
		 JdbcHelper.insert(query, todo.getTitle(), todo.getContent(), todo.getDay()
				 				, todo.getUserId());	
	 }
	 public Todo getTodoByNo(int todoNo) throws SQLException {
		 String query = QueryUtil.getSQL("todo.getTodoByNo");
		 return JdbcHelper.selectOne(query, new RowMapper<Todo>() {
			public Todo mapRow(ResultSet rs) throws SQLException {
				return resultFunc(rs);
			}
		 }, todoNo);
	 }
//	 public List<Todo> getAllTodoByUserId(String userId) throws SQLException {
//		 String query = QueryUtil.getSQL("todo.getAllTodo");
//		 return JdbcHelper.selectList(query, new RowMapper<Todo>() {
//			public Todo mapRow(ResultSet rs) throws SQLException {
//				return resultFunc(rs);
//			}
//		}, userId);
//	 }
//	 public List<Todo> getAllTodoPagination(String userId, int beginIndex, int endIndex) throws SQLException {
//		 String query = QueryUtil.getSQL("todo.getAllTodoPagination");
//		 return JdbcHelper.selectList(query, new RowMapper<Todo>() {
//			public Todo mapRow(ResultSet rs) throws SQLException {
//				return resultFunc(rs);
//			}
//		}, userId, beginIndex, endIndex);
//	 }
//	 public List<Todo> getAllTodoPaginationWithStatus(String userId, int beginIndex, int endIndex, String status) throws SQLException {
//		 String query = QueryUtil.getSQL("todo.getAllTodoPaginationWithStatus");
//		 return JdbcHelper.selectList(query, new RowMapper<Todo>() {
//			public Todo mapRow(ResultSet rs) throws SQLException {
//				return resultFunc(rs);
//			}
//		}, userId, beginIndex, endIndex, status);
//	 }
//	 public int getAllTodoPaginationWithStatusCount(String userId, String status) throws SQLException {
//		 String query = QueryUtil.getSQL("todo.getAllTodoPaginationWithStatusCount");
//		 return JdbcHelper.selectOne(query, new RowMapper<Integer>() {
//			public Integer mapRow(ResultSet rs) throws SQLException {
//				return rs.getInt("cnt");
//			}
//		 }, userId, status);
//	 }
//	 public List<Todo> getAllTodoPaginationWithStatusAndKeyword(String userId, int beginIndex, int endIndex, String status, String keyword) throws SQLException {
//		 String query = QueryUtil.getSQL("todo.getAllTodoPaginationWithStatusAndKeyword");
//		 return JdbcHelper.selectList(query, new RowMapper<Todo>() {
//			public Todo mapRow(ResultSet rs) throws SQLException {
//				return resultFunc(rs);
//			}
//		}, userId, beginIndex, endIndex, status, keyword);
//	 }
//	 public int getAllTodoPaginationWithStatusAndKeywordCount(String userId, String status, String keyword) throws SQLException {
//		 String query = QueryUtil.getSQL("todo.getAllTodoPaginationWithStatusAndKeywordCount");
//		 return JdbcHelper.selectOne(query, new RowMapper<Integer>() {
//			public Integer mapRow(ResultSet rs) throws SQLException {
//				return rs.getInt("cnt");
//			}
//		 }, userId, status, keyword);
//	 }
//	 public int getAllTodoPaginationCount(String userId) throws SQLException {
//		 String query = QueryUtil.getSQL("todo.getAllTodoPaginationCount");
//		 return JdbcHelper.selectOne(query, new RowMapper<Integer>() {
//			public Integer mapRow(ResultSet rs) throws SQLException {
//				return rs.getInt("cnt");
//			}
//		 }, userId);
//	 }
	 public List<TodoDto> getRecentTodos() throws SQLException {
		 String query = QueryUtil.getSQL("todo.getRecentTodos");
		 return JdbcHelper.selectList(query, new RowMapper<TodoDto>() {
			public TodoDto mapRow(ResultSet rs) throws SQLException {
				return resultFuncDto(rs);
			}
		 });
				 
	 }
	 public TodoDto getTodoDtoByNo(int todoNo) throws SQLException {
		 String query = QueryUtil.getSQL("todo.getTodoDtoByNo");
		 return JdbcHelper.selectOne(query, new RowMapper<TodoDto>() {
			public TodoDto mapRow(ResultSet rs) throws SQLException {
				return resultFuncDto(rs);
			}
		 }, todoNo);
	 }
	 
	 
	 public List<Todo> getAllTodosPagination(String userId, int beginIndex, int endIndex, String status, String keyword) throws SQLException {

		 List<Todo> todos = new ArrayList<Todo>();
		 
		 StringBuffer sb = new StringBuffer();
		 String startQuery = "select * from" + 
		 		"					        (SELECT todo_no, todo_title, todo_content, todo_day, todo_completed_day," + 
		 		"					                todo_status, user_id, todo_create_date , " + 
		 		"					                row_number() over (order by todo_create_date desc) rn" + 
		 		"					        FROM SAMPLE_TODOS" + 
		 		"					        WHERE USER_ID = ? ) where rn >= ? and rn <= ? ";
		 String todoStatusStr = (status != null && !status.isEmpty()) ? "and todo_status = ? " : "";
		 String keywordStr = (keyword != null && !keyword.isEmpty()) ? "and todo_title like '%'|| ? ||'%' " : "" ;

		 String query = sb.append(startQuery).append(todoStatusStr).append(keywordStr).toString();
		 
		 Connection conn = ConnectionUtil.getConnection();
		 PreparedStatement pstmt = conn.prepareStatement(query);
		 pstmt.setString(1, userId);
		 pstmt.setInt(2, beginIndex);
		 pstmt.setInt(3, endIndex);

		 int count = 4;
		 if(status != null && !status.isEmpty()) {
			 pstmt.setString(count, status);
			 count++;
		 }
		 if(keyword != null && !keyword.isEmpty()) {
			 pstmt.setString(count, keyword);
		 }
		 ResultSet rs = pstmt.executeQuery();
		 while(rs.next()) {
			 Todo todo = resultFunc(rs);
			 todos.add(todo);
		 }
		 if(rs != null) rs.close();
		 if(pstmt != null) pstmt.close();
		 if(conn != null) conn.close();
		 
		 return todos;
	 }
	 public int getAllTodosPaginationCount(String userId, String status, String keyword) throws SQLException {
		 StringBuffer sb = new StringBuffer();
		 String startQuery = "select count(*) cnt from sample_todos where user_id = ? ";
		 String todoStatusStr = (status != null && !status.isEmpty()) ? "and todo_status = ? " : "";
		 String keywordStr = (keyword != null && !keyword.isEmpty()) ? "and todo_title like '%'|| ? ||'%' " : "" ;

		 String query = sb.append(startQuery).append(todoStatusStr).append(keywordStr).toString();
		 
		 Connection conn = ConnectionUtil.getConnection();
		 PreparedStatement pstmt = conn.prepareStatement(query);
		 pstmt.setString(1, userId);

		 int count = 2;
		 if(status != null && !status.isEmpty()) {
			 pstmt.setString(count, status);
			 count++;
		 }
		 if(keyword != null && !keyword.isEmpty()) {
			 pstmt.setString(count, keyword);
		 }
		 ResultSet rs = pstmt.executeQuery();
		 if(rs.next()) {
			return rs.getInt("cnt");
		 }
		 if(rs != null) rs.close();
		 if(pstmt != null) pstmt.close();
		 if(conn != null) conn.close();
		 return -1; 
	 }
	 public void updateTodoByNo(String status, int todoNo) throws SQLException {
		 
		 String query = QueryUtil.getSQL("todo.updateTodoByNo");
		 JdbcHelper.update(query, status, todoNo);
	 }
	 
	 
	 
	 
	 private Todo resultFunc(ResultSet rs) throws SQLException {
		 Todo todo = new Todo();
		 todo.setNo(rs.getInt("todo_no"));
		 todo.setTitle(rs.getString("todo_title"));
		 todo.setContent(rs.getString("todo_content"));
		 todo.setDay(rs.getDate("todo_day"));
		 todo.setCompletedDay(rs.getDate("todo_completed_day"));
		 todo.setStatus(rs.getString("todo_status"));
		 todo.setUserId(rs.getString("user_id"));
		 todo.setCreatedDate(rs.getDate("todo_create_date"));
		 
		 return todo;
	 }
	 private TodoDto resultFuncDto(ResultSet rs) throws SQLException {
		 TodoDto dto = new TodoDto();
		 dto.setNo(rs.getInt("todo_no"));
		 dto.setTitle(rs.getString("todo_title"));
		 dto.setContent(rs.getString("todo_content"));
		 dto.setDay(rs.getDate("todo_day"));
		 dto.setCompletedDay(rs.getDate("todo_completed_day"));
		 dto.setStatus(rs.getString("todo_status"));
		 dto.setUserId(rs.getString("user_id"));
		 dto.setUserName(rs.getString("user_name"));
		 dto.setCreateDate(rs.getDate("todo_create_date"));
		 return dto;
	 }
}
