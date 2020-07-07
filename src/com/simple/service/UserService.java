package com.simple.service;

import com.simple.dao.UserDAO;
import com.simple.vo.User;

public class UserService {

	UserDAO userdao = new UserDAO();
	public boolean addNewUser(User user) throws Exception {
		
		User alreadyUser = userdao.getUserById(user.getId());
		System.out.println(alreadyUser);
		if(alreadyUser != null) {
			return false;
		}
		userdao.insertUser(user);
		return true;
	}
	public User getUserById(String userId) throws Exception {
		return userdao.getUserById(userId);
	}
	public User loginCheck(String id, String password) throws Exception {
		User user = userdao.getUserById(id);
		if(user == null) {
			return null;
		}
		if(!user.getPassword().equals(password)) {
			return null;
		}
		
		return user;
	}
	
	
}
