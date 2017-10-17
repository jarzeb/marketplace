package org.jz.marketplace.service;

import java.util.List;

import org.jz.marketplace.data.DataConnector;
import org.jz.marketplace.data.User;
import org.jz.marketplace.data.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService {
	private UserRepository userRepo;

	public UserService(DataConnector dataConnector) {
		userRepo = dataConnector.getUserRepository();
	}

	public User getUser(long userId) {
		return userRepo.findOne(userId);
	}
	
	public List<User> getFirst10Users() {
		return userRepo.findTop10ByOrderByUserId();
	}
}
