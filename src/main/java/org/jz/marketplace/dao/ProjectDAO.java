package org.jz.marketplace.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

@Component
public class ProjectDAO {

	@Autowired
	private ProjectRepository projectRepo;
	
	public List<Map<String,String>> getProjectList() {
		
		List<Map<String,String>> result = new ArrayList<>();
				
		for(Project project : projectRepo.findAll()) {
			Map<String,String> row = new HashMap<>();
			row.put("projectId", String.valueOf(project.getProjectId()));
			row.put("sellerName", project.getSeller().getUsername());
			row.put("description", project.getDescription());
			row.put("deadline", project.getDeadline().toString());
			row.put("billingType", project.getBillingType().name());
			row.put("topBid", "pending");
			result.add(row);
		}
		
		return result;
	}
}
