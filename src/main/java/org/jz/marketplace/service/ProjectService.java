package org.jz.marketplace.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class ProjectService {

	private BidRepository bidRepo;
	private ProjectRepository projectRepo;
	private UserRepository userRepo;
	
	public static final String ERROR_KEY = "error";
	public static final String ERR_USER_NOT_FOUND = "user not found";
	public static final String ERR_USER_NOT_SELLER = "user does not have seller status";
	public static final String ERR_STARTING_AMOUNT_MUST_BE_BLANK_OR_GE_1 = "starting amount must be either blank or >= $1";
	public static final String ERR_DEADLINE_CANNOT_BE_IN_PAST = "deadline cannot be in the past";
	
	public ProjectService(DataConnector dataConnector) {
		bidRepo = dataConnector.getBidRepository();
		projectRepo = dataConnector.getProjectRepository();
		userRepo = dataConnector.getUserRepository();
	}
	
	public List<Map<String,String>> getProjectListByBuyer(long buyerId) {
		User buyer = userRepo.findOne(buyerId);
		List<Project> projectList = projectRepo.findTop100ProjectsByLowestBidBuyerOrderByDeadlineDesc(buyer);
		return formatProjectList(projectList);		
	}
	
	public List<Map<String,String>> getProjectListBySeller(long sellerId) {
		User seller = userRepo.findOne(sellerId);
		List<Project> projectList = projectRepo.findTop100ProjectsBySellerOrderByDeadlineDesc(seller);
		return formatProjectList(projectList);
	}
	
	public List<Map<String,String>> getProjectList() {
		List<Project> projectList = projectRepo.findTop100ProjectsByOrderByDeadlineDesc();
		return formatProjectList(projectList);
	}	
	
	public Map<String,Object> getProjectDetail(long projectId) {
		
		Project project = projectRepo.findOne(projectId);
		List<Bid> bids = bidRepo.findTop100BidsByProjectOrderByBidDateTimeAsc(project);
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		Map<String,String> formattedProject = ProjectFormatter.format(project, currentDateTime);
		
		List<Map<String,String>> bidList = new ArrayList<>();
		for(Bid b : bids) {
			Map<String,String> bidMap = new HashMap<>();
			bidMap.put("amount", "$" + String.valueOf(b.getAmount()));
			bidMap.put("bidDateTime", b.getBidDateTime().toString());
			bidMap.put("buyerName", b.getBuyer().getUsername());
			bidList.add(bidMap);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("project", formattedProject);
		result.put("bids", bidList);
		
		return result;
	}
	
	public Map<String,String> createProject(Project project) {
		
		Map<String,String> result = new HashMap<>();
		LocalDateTime currentDateTime = LocalDateTime.now();
		
		User seller = (project.getSeller() == null) 
				? null : userRepo.findOne(project.getSeller().getUserId());

		project.setSeller(seller);
		project.setProjectDateTime(currentDateTime);
		
		if(seller == null) {
			result.put(ERROR_KEY, ERR_USER_NOT_FOUND);
		} else if(!seller.isSeller()) {
			result.put(ERROR_KEY, ERR_USER_NOT_SELLER);
		} else if(project.getStartingAmount() != null && project.getStartingAmount() < 1) {
			result.put(ERROR_KEY, ERR_STARTING_AMOUNT_MUST_BE_BLANK_OR_GE_1);
		} else if(project.getProjectDateTime().isAfter(project.getDeadline())) {
			result.put(ERROR_KEY, ERR_DEADLINE_CANNOT_BE_IN_PAST);
		}
		
		if(result.size() > 0) {
			return result;
		}
		projectRepo.save(project);
		
		return result;
	}
	
	private List<Map<String,String>> formatProjectList(List<Project> projectList) {
		LocalDateTime currentDateTime = LocalDateTime.now();
		List<Map<String,String>> result = new ArrayList<>();

		for(Project project : projectList) {
			Map<String,String> row = ProjectFormatter.format(project,  currentDateTime);
			result.add(row);
		}

		return result;
	}

}
