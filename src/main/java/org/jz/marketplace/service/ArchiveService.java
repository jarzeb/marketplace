package org.jz.marketplace.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.jz.marketplace.archiver.Archiver;
import org.jz.marketplace.archiver.ArchiverFactory;
import org.jz.marketplace.data.Bid;
import org.jz.marketplace.data.BidRepository;
import org.jz.marketplace.data.DataConnector;
import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.ProjectRepository;
import org.springframework.stereotype.Component;

@Component
public class ArchiveService {

	private BidRepository bidRepo;
	private ProjectRepository projectRepo;
	private Archiver archiver;
	
	public ArchiveService(DataConnector dataConnector) {
		bidRepo = dataConnector.getBidRepository();
		projectRepo = dataConnector.getProjectRepository();
		archiver = new ArchiverFactory().getArchiver();
	}

	public Map<String,String> archiveCompletedProjects() {
		
		LocalDateTime now = LocalDateTime.now();
		
		int bidCount = 0;
		int projectCount = 0;
		boolean done = false;
		
		while(!done) {
			// Fetch the projects in batches of 100
			List<Project> projects = projectRepo.findTop100ProjectsByOrderByDeadlineAsc();
			if(projects == null || projects.isEmpty())
				break;

			for(Project p : projects) {
				if(p.getDeadline().isAfter(now)) {
					done = true;
					break;
				}
				List<Bid> bids = bidRepo.findBidsByProject(p);				
				// Archive the Bids
				bidCount += archiveBidsAndProject(bids, p);
				projectCount++;
			}
		}
		Map<String,String> result = new HashMap<>();
		result.put("projectsArchived", String.valueOf(projectCount));
		result.put("bidsArchived", String.valueOf(bidCount));
		
		return result;	
	}

	
	@Transactional
	private int archiveBidsAndProject(List<Bid> bids, Project p) {		
		int bidCount = 0;

		// archive the Bids
		if(bids != null && !bids.isEmpty()) { 
			for(Bid b : bids) {
				archiver.archive(b.toString());
			}
			bidRepo.delete(bids);
			bidCount = bids.size();
		}
		
		// archive the Project
		archiver.archive(p.toString());
		projectRepo.delete(p);
		
		return bidCount;
	}
	
}
