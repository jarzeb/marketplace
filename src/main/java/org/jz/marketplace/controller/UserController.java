package org.jz.marketplace.controller;

import java.util.List;

import org.jz.marketplace.model.User;
import org.jz.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User result = userService.createUser(user);
		HttpStatus httpStatus = (result == null) ? HttpStatus.BAD_REQUEST : HttpStatus.CREATED;
		return new ResponseEntity<User>(result, httpStatus);
	}

	@RequestMapping(value = "/getFirst10Users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		List<User> result = userService.getFirst10Users();
		HttpStatus httpStatus = (result == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
		return new ResponseEntity<List<User>>(result, httpStatus);
	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@Param("userId") long userId) {
		User result = userService.getUser(userId);
		HttpStatus httpStatus = (result == null) ? HttpStatus.BAD_REQUEST : HttpStatus.OK;
		return new ResponseEntity<User>(result, httpStatus);
	}
	
}