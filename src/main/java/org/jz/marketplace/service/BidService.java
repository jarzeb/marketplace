package org.jz.marketplace.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
		
		// save the bid
		bidRepo.save(bid);
		
		// update the project's reference to lowest bid
		project.setLowestBid(bid);
		projectRepo.save(project);

		result.put("result", "true");
		return result;
	}
	
	public List<Map<String,String>> findBidsByBuyerId(long buyerId) {
		User buyer = userRepo.findOne(buyerId);
		
		List<Bid> bids = bidRepo.findBidsByBuyerOrderByBidDateTimeDesc(buyer);
		List<Map<String,String>> result = new ArrayList<>();
		
		for(Bid b : bids) {
			Map<String,String> row = new HashMap<>();
			Project p = b.getProject();
			Bid lowestBid = p.getLowestBid();
			row.put("bidDateTime", b.getBidDateTime().toString());
			row.put("amount", String.valueOf(b.getAmount()));
			row.put("projectId", String.valueOf(p.getProjectId()));
			row.put("projectDescription", p.getDescription());
			row.put("projectDeadline", p.getDeadline().toString());
			row.put("sellerName", p.getSeller().getUsername());
			if(lowestBid != null) {
				row.put("lowestBidAmount", String.valueOf(lowestBid.getAmount()));
				row.put("lowestBidBuyer", lowestBid.getBuyer().getUsername());
			}
			row.put("isLowestBid", b.equals(lowestBid) ? "true" : "false");
			result.add(row);
		}
		
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
