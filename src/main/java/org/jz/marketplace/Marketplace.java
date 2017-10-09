package org.jz.marketplace;

import org.jz.marketplace.data.Bid;
import org.jz.marketplace.data.BidRepository;
import org.jz.marketplace.data.Project;
import org.jz.marketplace.data.ProjectRepository;
import org.jz.marketplace.data.User;
import org.jz.marketplace.data.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Marketplace 
{
		
    public static void main(String[] args) {    	
        SpringApplication.run(Marketplace.class, args);
    }

	@Bean
	public Void addSampleData(UserRepository userRepo, ProjectRepository projectRepo, BidRepository bidRepo) {
		for(User u : SampleData.getUsers()) userRepo.save(u);
		for(Project p : SampleData.getProjects()) projectRepo.save(p);
		for(Bid b : SampleData.getBids()) bidRepo.save(b);
		
		return null;
	}

	
    /*
	@Bean
	public CommandLineRunner demo(UserRepository repository) {
		return (args) -> {
			repository.save(new User("bob"));
			
			for(User user : repository.findAll())
				System.out.println(user.toString());
		};
	}
	*/
	

}
