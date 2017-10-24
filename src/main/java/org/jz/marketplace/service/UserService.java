package org.jz.marketplace.service;

import java.util.List;

import org.jz.marketplace.dao.DataConnector;
import org.jz.marketplace.model.User;
import org.jz.marketplace.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserService {
	private UserRepository userRepo;

	public UserService(DataConnector dataConnector) {
		userRepo = dataConnector.getUserRepository();
	}

	public User createUser(User user) {
		return userRepo.save(user);
	}
	
	public User getUser(long userId) {
		return userRepo.findOne(userId);
	}
	
	public List<User> getFirst25Users() {
		return userRepo.findTop25ByOrderByUserId();
	}
	
}
