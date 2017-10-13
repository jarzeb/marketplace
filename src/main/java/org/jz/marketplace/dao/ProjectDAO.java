package org.jz.marketplace.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jz.marketplace.data.Bid;
import org.jz.marketplace.data.BidRepository;
import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectDAO {

	@Autowired
	private ProjectRepository projectRepo;
	@Autowired
	private BidRepository bidRepo;
	
	public List<Map<String,String>> getProjectList() {
		
		List<Map<String,String>> result = new ArrayList<>();
				
		for(Project project : projectRepo.findAll()) {
			Map<String,String> row = new HashMap<>();
			row.put("projectId", String.valueOf(project.getProjectId()));
			row.put("sellerName", project.getSeller().getUsername());
			row.put("description", project.getDescription());
			row.put("deadline", project.getDeadline().toString());
			row.put("billingType", project.getBillingType().name());
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
}
