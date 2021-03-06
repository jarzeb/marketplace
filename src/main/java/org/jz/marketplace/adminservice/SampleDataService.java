package org.jz.marketplace.adminservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jz.marketplace.model.Bid;
import org.jz.marketplace.model.Project;
import org.jz.marketplace.model.User;
import org.jz.marketplace.model.Bid.BidBuilder;
import org.jz.marketplace.model.Project.BillingType;
import org.jz.marketplace.model.Project.ProjectBuilder;
import org.jz.marketplace.model.User.UserBuilder;
import org.jz.marketplace.repository.ProjectRepository;
import org.jz.marketplace.repository.UserRepository;
import org.jz.marketplace.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SampleDataService {
	
	private static List<User> initialUsers;
	private static List<Project> initialProjects;
	private static List<Bid> initialBids;

	@Autowired
	private BidService bidService;
	@Autowired
	private ProjectRepository projectRepo;
	@Autowired
	private UserRepository userRepo;
	
	private boolean loaded;
		
	public Map<String,String> loadSampleData() {
		Map<String,String> result = new HashMap<>();
		if(loaded) {
			result.put("error", "sample data already loaded");
		} else {
			userRepo.save(getUsers());
			projectRepo.save(getProjects());
			for(Bid b : getBids())
				bidService.createBid(b);
			
			result.put("info","finished loading sample data");
			loaded = true;
		}
		return result;

	}
	
	public static List<User> getUsers() {
		if(initialUsers != null)
			return initialUsers;

		initialUsers = new ArrayList<>();
		// sellers
		initialUsers.add(new UserBuilder().username("alice").isBuyer(false).isSeller(true).build());
		initialUsers.add(new UserBuilder().username("brian").isBuyer(false).isSeller(true).build());
		// buyers + sellers
		initialUsers.add(new UserBuilder().username("chantale").isBuyer(true).isSeller(true).build());
		initialUsers.add(new UserBuilder().username("daniel").isBuyer(true).isSeller(true).build());
		// buyers
		initialUsers.add(new UserBuilder().username("emily").isBuyer(true).isSeller(false).build());
		initialUsers.add(new UserBuilder().username("fazaan").isBuyer(true).isSeller(false).build());
		initialUsers.add(new UserBuilder().username("giselle").isBuyer(true).isSeller(false).build());
		initialUsers.add(new UserBuilder().username("henry").isBuyer(true).isSeller(false).build());
		initialUsers.add(new UserBuilder().username("isabelle").isBuyer(true).isSeller(false).build());
		initialUsers.add(new UserBuilder().username("jason").isBuyer(true).isSeller(false).build());

		return initialUsers;
	}

	public static List<Project> getProjects() {
		if(initialProjects != null)
			return initialProjects;

		initialProjects = new ArrayList<>();
		
		User[] s = { getUsers().get(0), getUsers().get(1), getUsers().get(2), getUsers().get(3) };
		
		LocalDateTime now = LocalDateTime.now().plusSeconds(5);

		initialProjects.add(new ProjectBuilder().billingType(BillingType.FIXED).deadline(now).seller(s[0])
				.projectDateTime(now).startingAmount(10000).description("Paint my house.").build());

		initialProjects.add(new ProjectBuilder().billingType(BillingType.HOURLY).deadline(now.plusDays(1)).seller(s[0])
				.projectDateTime(now).startingAmount(9000).description("Cut the grass.").build());

		initialProjects.add(new ProjectBuilder().billingType(BillingType.HOURLY).deadline(now.plusDays(2)).seller(s[1])
				.projectDateTime(now).startingAmount(8000).description("Do my dishes.").build());

		initialProjects.add(new ProjectBuilder().billingType(BillingType.HOURLY).deadline(now.plusDays(3)).seller(s[2])
				.projectDateTime(now).description("Wash my car.").build());

		initialProjects.add(new ProjectBuilder().billingType(BillingType.FIXED).deadline(now.plusDays(4)).seller(s[3])
				.projectDateTime(now).description("Clean the kitchen.").build());

		return initialProjects;
	}

	public static List<Bid> getBids() {
		if(initialBids != null)
			return initialBids;

		initialBids = new ArrayList<>();

		LocalDateTime bidDateTime = LocalDateTime.parse("2017-10-20T13:00:20");
		Project project = getProjects().get(0);
		User buyer = getUsers().get(3);
		
		initialBids.add(new BidBuilder().amount(1002).bidDateTime(bidDateTime).buyer(buyer).project(project).build());
		initialBids.add(new BidBuilder().amount(1001).bidDateTime(bidDateTime.plusDays(1)).buyer(buyer).project(project).build());		
		
		return initialBids;
	}		
		
}
