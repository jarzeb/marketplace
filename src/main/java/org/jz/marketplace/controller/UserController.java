package org.jz.marketplace.controller;

import java.util.List;

import org.jz.marketplace.data.User;
import org.jz.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/getFirst10Users", method = RequestMethod.GET)
	public List<User> getUsers() {
		return userService.getFirst10Users();
	}


}