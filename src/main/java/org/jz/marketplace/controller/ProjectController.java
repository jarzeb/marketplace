package org.jz.marketplace.controller;

import org.jz.marketplace.data.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

	@RequestMapping("/projectdetails")
	public User user(@RequestParam(value="username") String username) {
		return null;  
	}


}