package org.jz.marketplace.controller;

import org.jz.marketplace.data.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping("/user")
	public User user(@RequestParam(value="username") String username) {
		return new User(username);   
	}

	


}