package org.jz.marketplace.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.jz.marketplace.data.Bid;
import org.jz.marketplace.data.BidRepository;
import org.jz.marketplace.data.DataConnector;
import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.ProjectRepository;
import org.jz.marketplace.data.User;
import org.jz.marketplace.data.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class BidService {
	
	private BidRepository bidRepo;
	private ProjectRepository projectRepo;
	private UserRepository userRepo;
	
	public BidService(DataConnector dataConnector) {
		bidRepo = dataConnector.getBidRepository();
		projectRepo = dataConnector.getProjectRepository();
		userRepo = dataConnector.getUserRepository();
	}
	
	@Transactional
	public Map<String,String> createBid(long projectId, long buyerId, int amount) {

		LocalDateTime bidDateTime = LocalDateTime.now();
		Map<String,String> result = new HashMap<>();

		Project project = projectRepo.findOne(projectId);
		User buyer = userRepo.findOne(buyerId);
		Bid lowestBid = bidRepo.findFirstBidByProjectOrderByAmount(project);
		
		Bid bid = new Bid();
		bid.setAmount(amount);
		bid.setBidDateTime(bidDateTime);
		bid.setBuyer(buyer);
		bid.setProject(project);

		result = validateBid(project, buyer, bid, lowestBid, result);		
		if(!result.isEmpty())
			return result;
		
		bidRepo.save(bid);
		result.put("result", "true");
		
		return result;
	}
	
	private Map<String,String> validateBid(Project project, User buyer, Bid bid, Bid lowestBid, Map<String,String> result) {
		String error = null;
		if(project == null) {
			error = "project not found";
		} else if(buyer == null) {
			error = "buyer not found";
		} else if(bid.getBidDateTime().isAfter(project.getDeadline())) {
			error = "project deadline has passed";
		} else if(buyer.equals(project.getSeller())) {
			error = "buyer cannot be same user as seller";
		} else if(!buyer.isBuyer()) {
			error = "user does not have buyer status";
		} else if(bid.getAmount() <= 0) {
			error = "bid must be greater than $0";
		} else if(lowestBid != null && lowestBid.getAmount() <= bid.getAmount()) {
			error = "bid must be less than the current lowest bid";
		} else if(bid.getAmount() > project.getStartingBid()) {
			error = "bid must be less than or equal to the starting bid";
		}
		
		if(error != null) {
			result.put("error", error);
			result.put("result", "false");
		}
		
		return result;
	}
}
