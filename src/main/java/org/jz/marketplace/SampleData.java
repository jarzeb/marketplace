package org.jz.marketplace;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jz.marketplace.data.Bid;
import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.User;
import org.jz.marketplace.data.Project.BillingType;

public class SampleData {
	
	private static List<User> initialUsers;
	private static List<Project> initialProjects;
	private static List<Bid> initialBids;

	public static List<User> getUsers() {
		if(initialUsers != null)
			return initialUsers;

		initialUsers = new ArrayList<>();

		initialUsers.add(new User("alice", false, true));
		initialUsers.add(new User("brian", false, true));
		initialUsers.add(new User("chantale", true, true));
		initialUsers.add(new User("daniel", true, true));

		initialUsers.add(new User("emily", true, false));
		initialUsers.add(new User("fazaan", true, false));
		initialUsers.add(new User("giselle", true, false));
		initialUsers.add(new User("henry", true, false));
		initialUsers.add(new User("isabelle", true, false));
		initialUsers.add(new User("jason", true, false));

		return initialUsers;
	}

	public static List<Project> getProjects() {
		if(initialProjects != null)
			return initialProjects;

		initialProjects = new ArrayList<>();

		Project p = new Project();
		p.setBillingType(BillingType.FIXED);
		p.setDeadline(LocalDateTime.of(2017, 10, 25, 13, 30));
		p.setSeller(getUsers().get(2));
		p.setStartingBid(1000);
		p.setDescription("Paint my house.");
		initialProjects.add(p);

		p = new Project();
		p.setBillingType(BillingType.HOURLY);
		p.setDeadline(LocalDateTime.of(2017, 11, 02, 11, 10));
		p.setSeller(getUsers().get(2));
		p.setStartingBid(50);
		p.setDescription("Cut the grass.");
		initialProjects.add(p);

		p = new Project();
		p.setBillingType(BillingType.FIXED);
		p.setDeadline(LocalDateTime.of(2017, 11, 11, 15, 15));
		p.setSeller(getUsers().get(2));
		p.setStartingBid(5000);
		p.setDescription("Wash my car.");
		initialProjects.add(p);

		p = new Project();
		p.setBillingType(BillingType.HOURLY);
		p.setDeadline(LocalDateTime.of(2017, 10, 01, 9, 00));
		p.setSeller(getUsers().get(1));
		p.setStartingBid(100);
		p.setDescription("Do my dishes.");
		initialProjects.add(p);

		return initialProjects;
	}

	public static List<Bid> getBids() {
		if(initialBids != null)
			return initialBids;

		initialBids = new ArrayList<>();

		Bid b = new Bid();
		b.setAmount(1002);
		b.setBidDateTime(LocalDateTime.of(2017,10,10,13,00));
		b.setBuyer(getUsers().get(1));
		b.setProject(getProjects().get(0));
		initialBids.add(b);

		b = new Bid();
		b.setAmount(1001);
		b.setBidDateTime(LocalDateTime.of(2017,10,20,15,00));
		b.setBuyer(getUsers().get(1));
		b.setProject(getProjects().get(0));
		initialBids.add(b);
		
		return initialBids;
	}

}
