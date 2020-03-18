package com.purv.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;


import com.purv.DAO.UserDAO;

import com.purv.model.UserInfo;
@Repository
@Transactional
public class UserServiceImp {

	@Autowired
	private UserDAO userdao;
	
	public List<UserInfo> getAllActiveUserInfo() {
		// TODO Auto-generated method stub
		return userdao.findAllByEnabled((short) 1);
	}

	public UserInfo addUser(UserInfo userRecord) {
		userRecord.setPassword(new BCryptPasswordEncoder().encode(userRecord.getPassword()));
		return userdao.save(userRecord);
		
	}
	
	public void deleteUser(Integer id) {
		userdao.deleteById(id);
		
	}

	public UserInfo getUserInfoByUserName(String username) {
		short enabled = 1;
		return userdao.findByUserNameAndEnabled(username, enabled);
	}

}
