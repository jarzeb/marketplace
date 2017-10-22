package org.jz.marketplace.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.jz.marketplace.dao.DataConnector;
import org.jz.marketplace.model.Bid;
import org.jz.marketplace.model.Project;
import org.jz.marketplace.model.User;
import org.jz.marketplace.repository.BidRepository;
import org.jz.marketplace.repository.ProjectRepository;
import org.jz.marketplace.repository.UserRepository;
import org.jz.marketplace.service.formatter.ProjectFormatter;
import org.springframework.stereotype.Component;

@Component
public class BidService {
	
	private BidRepository bidRepo;
	private ProjectRepository projectRepo;
	private UserRepository userRepo;
	
	public static final String ERROR_KEY = "error";
	public static final String ERR_BID_NOT_FOUND = "project not found";
	public static final String ERR_PROJECT_NOT_FOUND = "project not found";
	public static final String ERR_PROJECT_DEADLINE_PASSED = "project deadline has passed";
	public static final String ERR_BUYER_NOT_FOUND = "buyer not found";
	public static final String ERR_BUYER_SAME_AS_SELLER = "buyer cannot be same user as seller";
	public static final String ERR_USER_IS_NOT_BUYER = "user does not have buyer status";
	public static final String ERR_BID_AMOUNT_MUST_BE_GT_ZERO = "bid must be greater than $0";
	public static final String ERR_BID_MUST_BE_LT_CURRENT_BID = "bid must be less than the current lowest bid";
	public static final String ERR_BID_MUST_BE_LE_STARTING_BID = "bid must be less than or equal to the starting bid";
	
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
			row.put("bidDateTime", b.getBidDateTime().format(ProjectFormatter.DT_FORMAT));
			row.put("amount", "$" + String.valueOf(b.getAmount()));
			row.put("isLowestBid", b.equals(lowestBid) ? "true" : "false");
			Map<String,String> projectRow = ProjectFormatter.format(p, currentDateTime);
			row.putAll(projectRow);
			result.add(row);
		}
		
		return result;
	}
	
	protected Map<String,String> validateBid(Bid bid, Bid lowestBid, Map<String,String> result) {
		String error = null;
		Project project = (bid == null) ? null : bid.getProject();
		User buyer = (bid == null) ? null : bid.getBuyer();
		
		if(bid == null) {
			error = ERR_BID_NOT_FOUND;
		} else if(project == null) {
			error = ERR_PROJECT_NOT_FOUND;
		} else if(bid.getBidDateTime().isAfter(project.getDeadline())) {
			error = ERR_PROJECT_DEADLINE_PASSED;
		} else if(buyer == null) {
			error = ERR_BUYER_NOT_FOUND;
		} else if(buyer.equals(project.getSeller())) {
			error = ERR_BUYER_SAME_AS_SELLER;
		} else if(!buyer.isBuyer()) {
			error = ERR_USER_IS_NOT_BUYER;
		} else if(bid.getAmount() <= 0) {
			error = ERR_BID_AMOUNT_MUST_BE_GT_ZERO;
		} else if(lowestBid != null && lowestBid.getAmount() <= bid.getAmount()) {
			error = ERR_BID_MUST_BE_LT_CURRENT_BID;
		} else if(project.getStartingAmount() != null && bid.getAmount() > project.getStartingAmount()) {
			error = ERR_BID_MUST_BE_LE_STARTING_BID;
		}
		
		if(error != null) {
			result.put(ERROR_KEY, error);
		}
		
		return result;
	}
}
