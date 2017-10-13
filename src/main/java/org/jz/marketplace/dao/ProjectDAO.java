package org.jz.marketplace.dao;

import java.util.HashMap;
import java.util.Map;

import org.jz.marketplace.data.BidRepository;
import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.ProjectRepository;
import org.jz.marketplace.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProjectDAO {

	@Autowired
	private BidRepository bidRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private ProjectRepository projectRepo;
	
	public Map<String,Object> getProjectList() {
		
		Map<String,Object> result = new HashMap<>();
		
		Iterable<Project> i = projectRepo.findAll();
		for(Project p : i) {
			result.put(String.valueOf(p.getProjectId()), p.getDescription());	
		}
		
		return result;
	}
}
