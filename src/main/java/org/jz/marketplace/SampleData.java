package org.jz.marketplace;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jz.marketplace.data.Bid;
import org.jz.marketplace.data.Bid.BidBuilder;
import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.Project.BillingType;
import org.jz.marketplace.data.Project.ProjectBuilder;
import org.jz.marketplace.data.ProjectRepository;
import org.jz.marketplace.data.User;
import org.jz.marketplace.data.User.UserBuilder;
import org.jz.marketplace.data.UserRepository;
import org.jz.marketplace.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SampleData {
	
	private static List<User> initialUsers;
	private static List<Project> initialProjects;
	private static List<Bid> initialBids;

	@Autowired
	private BidService bidService;
	@Autowired
	private ProjectRepository projectRepo;
	@Autowired
	private UserRepository userRepo;
	

	@Bean
	public Void loadSampleData() {
		userRepo.save(getUsers());
		projectRepo.save(getProjects());
		for(Bid b : getBids())
			bidService.createBid(b);

		return null;
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
		
		LocalDateTime now = LocalDateTime.now();

		initialProjects.add(new ProjectBuilder().billingType(BillingType.FIXED).deadline(now.plusSeconds(5)).seller(s[0])
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
