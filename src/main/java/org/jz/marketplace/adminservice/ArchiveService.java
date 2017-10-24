package org.jz.marketplace.adminservice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.jz.marketplace.archiver.Archiver;
import org.jz.marketplace.archiver.ArchiverFactory;
import org.jz.marketplace.dao.DataConnector;
import org.jz.marketplace.model.Bid;
import org.jz.marketplace.model.Project;
import org.jz.marketplace.repository.BidRepository;
import org.jz.marketplace.repository.ProjectRepository;
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
	private int archiveBidsAndProject(List<Bid> bids, Project project) {		
		int bidCount = 0;

		// archive the Bids
		if(bids != null && !bids.isEmpty()) { 
			for(Bid b : bids) {
				archiver.archive(b);
			}
			bidCount = bids.size();
		}
		
		// archive the Project
		archiver.archive(project);

		// Remove Project reference to lowestBid so Bids can be deleted
		if(project.getLowestBid() != null) {
			project.setLowestBid(null);
			projectRepo.save(project);
		}

		// delete the original data
		bidRepo.delete(bids);
		projectRepo.delete(project);
		
		return bidCount;
	}
	
}
