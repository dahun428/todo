package com.simple.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.simple.util.JdbcHelper.RowMapper;
import com.simple.util.JdbcHelper;
import com.simple.util.QueryUtil;
import com.simple.vo.User;

public class UserDAO {

	public void insertUser(User user) throws SQLException {
		
		String query = QueryUtil.getSQL("user.insertUser");
		JdbcHelper.insert(query, user.getId(), user.getName(), user.getPassword()
								, user.getEmail());	
	}
	public User getUserById(String userId) throws SQLException {
		String query = QueryUtil.getSQL("user.getUserById");
		return JdbcHelper.selectOne(query, new RowMapper<User>() {
			public User mapRow(ResultSet rs) throws SQLException {
				return resultFunc(rs);
			}
		}, userId);
	}
	
	private User resultFunc(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getString("user_id"));
		user.setName(rs.getString("user_name"));
		user.setPassword(rs.getString("user_password"));
		user.setEmail(rs.getString("user_email"));
		user.setCreatedDate(rs.getDate("user_created_date"));
		return user;
	}
	
}
