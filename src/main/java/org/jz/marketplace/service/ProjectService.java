package org.jz.marketplace.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jz.marketplace.data.Bid;
import org.jz.marketplace.data.BidRepository;
import org.jz.marketplace.data.DataConnector;
import org.jz.marketplace.data.DataConnectorBean;
import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.ProjectRepository;
import org.jz.marketplace.data.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class ProjectService {

	private DataConnector dataConnector;
	
	private BidRepository bidRepo;
	private ProjectRepository projectRepo;
	private UserRepository userRepo;

	
	public ProjectService(DataConnector dataConnector) {
		this.dataConnector = dataConnector;
		
		bidRepo = dataConnector.getBidRepository();
		projectRepo = dataConnector.getProjectRepository();
		userRepo = dataConnector.getUserRepository();
	}
	
	
	public List<Map<String,String>> getProjectList() {

		LocalDateTime currentDateTime = LocalDateTime.now();
		List<Map<String,String>> result = new ArrayList<>();
				
		for(Project project : projectRepo.findAll()) {
			Map<String,String> row = new HashMap<>();
			row.put("projectId", String.valueOf(project.getProjectId()));
			row.put("sellerName", project.getSeller().getUsername());
			row.put("description", project.getDescription());
			row.put("deadline", project.getDeadline().toString());
			row.put("billingType", project.getBillingType().name());
			String bgColor;
			String status;
			if(isProjectActive(project.getDeadline(), currentDateTime)) {
				bgColor = "lightgray";
				status = "Active";
			} else {
				bgColor = "gray";
				status = "Closed";
			}
			row.put("bgColor", bgColor);
			row.put("status", status);

			result.add(row);
		}
		
		return result;
	}
	
	public Map<String,Object> getProjectDetail(long projectId) {
		
		Project project = projectRepo.findOne(projectId);
		List<Bid> bids = bidRepo.findBidsByProjectOrderByAmountDesc(project);
		
		Map<String,Object> p = new HashMap<>();
		p.put("projectId", String.valueOf(project.getProjectId()));
		p.put("sellerName", project.getSeller().getUsername());
		p.put("description", project.getDescription());
		p.put("deadline", project.getDeadline().toString());
		p.put("billingType", project.getBillingType().name());	
		
		List<Map<String,String>> bidList = new ArrayList<>();
		for(Bid b : bids) {
			Map<String,String> bidMap = new HashMap<>();
			bidMap.put("amount",  String.valueOf(b.getAmount()));
			bidMap.put("bidDateTime", b.getBidDateTime().toString());
			bidMap.put("buyerName", b.getBuyer().getUsername());
			bidList.add(bidMap);
		}
		Map<String, Object> result = new HashMap<>();
		result.put("project", p);
		result.put("bids", bidList);
		
		return result;
	}
	
	private boolean isProjectActive(LocalDateTime deadlineDateTime, LocalDateTime currentDateTime) {
		return !currentDateTime.isAfter(deadlineDateTime);
	}
}
