package org.jz.marketplace.dao;

import org.jz.marketplace.repository.BidRepository;
import org.jz.marketplace.repository.ProjectRepository;
import org.jz.marketplace.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataConnectorBean implements DataConnector {

	@Autowired
	private BidRepository bidRepository;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserRepository userRepository;
	
	
	public BidRepository getBidRepository() {
		return bidRepository;
	}
	
	public ProjectRepository getProjectRepository() {
		return projectRepository;
	}
	
	public UserRepository getUserRepository() {
		return userRepository;
	}
	
	
}
