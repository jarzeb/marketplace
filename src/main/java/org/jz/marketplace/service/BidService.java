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
import org.jz.marketplace.service.formatter.ProjectFormatter;
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
	public Map<String,String> createBid(Bid bid) {

		Map<String,String> result = new HashMap<>();

		Project project = projectRepo.findOne(bid.getProject().getProjectId());
		User buyer = userRepo.findOne(bid.getBuyer().getUserId());
		Bid lowestBid = bidRepo.findFirstBidByProjectOrderByAmount(project);
		
		bid.setProject(project);
		bid.setBuyer(buyer);

		bid.setBidDateTime(LocalDateTime.now());
		result = validateBid(bid, lowestBid, result);		
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
		
		List<Bid> bids = bidRepo.findTop100BidsByBuyerOrderByBidDateTimeAsc(buyer);
		List<Map<String,String>> result = new ArrayList<>();
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		for(Bid b : bids) {
			Map<String,String> row = new HashMap<>();
			Project p = b.getProject();
			Bid lowestBid = p.getLowestBid();
			row.put("bidId", String.valueOf(b.getBidId()));
			row.put("buyerName", b.getBuyer().getUsername());
			row.put("bidDateTime", b.getBidDateTime().toString());
			row.put("amount", "$" + String.valueOf(b.getAmount()));
			row.put("isLowestBid", b.equals(lowestBid) ? "true" : "false");
			Map<String,String> projectRow = ProjectFormatter.format(p, currentDateTime);
			row.putAll(projectRow);
			result.add(row);
		}
		
		return result;
	}
	
	private Map<String,String> validateBid(Bid bid, Bid lowestBid, Map<String,String> result) {
		String error = null;
		Project project = bid.getProject();
		User buyer = bid.getBuyer();
		
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
		} else if(project.getStartingAmount() != null && bid.getAmount() > project.getStartingAmount()) {
			error = "bid must be less than or equal to the starting bid";
		}
		
		if(error != null) {
			result.put("error", error);
			result.put("result", "false");
		}
		
		return result;
	}
}
