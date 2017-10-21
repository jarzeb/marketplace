package org.jz.marketplace.dao;

import org.jz.marketplace.repository.BidRepository;
import org.jz.marketplace.repository.ProjectRepository;
import org.jz.marketplace.repository.UserRepository;

public interface DataConnector {

	public BidRepository getBidRepository();
	public ProjectRepository getProjectRepository();
	public UserRepository getUserRepository();

}
