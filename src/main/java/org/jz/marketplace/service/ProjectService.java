package org.jz.marketplace.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ProjectService {

	private BidRepository bidRepo;
	private ProjectRepository projectRepo;
	private UserRepository userRepo;
	
	public ProjectService(DataConnector dataConnector) {
		bidRepo = dataConnector.getBidRepository();
		projectRepo = dataConnector.getProjectRepository();
		userRepo = dataConnector.getUserRepository();
	}
	
	public List<Map<String,String>> getProjectList(Long sellerId) {
	
		List<Project> projectList = null;
		if(sellerId == null) {
			projectList = projectRepo.findTop100ProjectsByOrderByDeadlineDesc();
		} else {
			User seller = userRepo.findOne(sellerId);
			projectList = projectRepo.findTop100ProjectsBySellerOrderByDeadlineDesc(seller);
		}
		
		LocalDateTime currentDateTime = LocalDateTime.now();
		List<Map<String,String>> result = new ArrayList<>();

		for(Project project : projectList) {
			Map<String,String> row = ProjectFormatter.format(project,  currentDateTime);
			result.add(row);
		}
		
		return result;
	}	
	
	public Map<String,Object> getProjectDetail(long projectId) {
		
		Project project = projectRepo.findOne(projectId);
		List<Bid> bids = bidRepo.findTop100BidsByProjectOrderByBidDateTimeAsc(project);
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		Map<String,String> p = ProjectFormatter.format(project, currentDateTime);
		
		List<Map<String,String>> bidList = new ArrayList<>();
		for(Bid b : bids) {
			Map<String,String> bidMap = new HashMap<>();
			bidMap.put("amount", "$" + String.valueOf(b.getAmount()));
			bidMap.put("bidDateTime", b.getBidDateTime().toString());
			bidMap.put("buyerName", b.getBuyer().getUsername());
			bidList.add(bidMap);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("project", p);
		result.put("bids", bidList);
		
		return result;
	}
	
	public Map<String,String> createProject(Project project) {
		
		Map<String,String> result = new HashMap<>();
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		User seller = userRepo.findOne(project.getSeller().getUserId());
		project.setSeller(seller);
		
		if(seller == null) {
			result.put("error", "user not found");
		} else if(!seller.isSeller()) {
			result.put("error", "user is not a seller");
		} else if(project.getStartingBid() < 1) {
			result.put("error", "starting bid must be at least $1");
		} else if(currentDateTime.isAfter(project.getDeadline())) {
			result.put("error",  "deadline cannot be in the past");
		}
		
		if(result.size() > 0) {
			return result;
		}

		projectRepo.save(project);
		
		result.put("result", "true");
		return result;
	}

}
