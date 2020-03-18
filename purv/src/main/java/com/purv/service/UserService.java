package com.purv.service;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.purv.DAO.UserDAO;
import com.purv.model.UserInfo;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private UserServiceImp userdao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user=userdao.getUserInfoByUserName(username);
		GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());
		return new User(user.getUserName(), user.getPassword(), Arrays.asList(authority));
	}

}
