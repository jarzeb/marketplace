package org.jz.marketplace.data;

public interface DataConnector {

	public BidRepository getBidRepository();
	public ProjectRepository getProjectRepository();
	public UserRepository getUserRepository();

}
