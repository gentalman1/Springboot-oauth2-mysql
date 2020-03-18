package com.purv.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.purv.model.UserInfo;
import com.purv.service.UserServiceImp;

@EnableResourceServer
@RestController
public class UserController {

	@Autowired
	private UserServiceImp UserServiceImp;
	
	@GetMapping("/user")
	public Object getAllUser(@RequestHeader HttpHeaders requestHeaders) {
		
		List<UserInfo> users=UserServiceImp.getAllActiveUserInfo();
		if (users == null || users.isEmpty()) {
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		}
		return users;
	}
	
	@PostMapping("/user")
	public UserInfo addUser(@RequestBody UserInfo userRecord) {
		return UserServiceImp.addUser(userRecord);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/hi")
	public void sayHello() {
		System.out.println("Hello");
	}
	
    @PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/user/{id}")
	public void deleteUser(@PathVariable Integer id) {
	
		UserServiceImp.deleteUser(id);
	}
	
}
