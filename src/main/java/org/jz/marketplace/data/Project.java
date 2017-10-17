package org.jz.marketplace.data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import org.springframework.data.rest.core.annotation.RestResource;

@Entity
public class Project {
	
	public static enum BillingType {
		HOURLY, FIXED;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private long projectId;

	@ManyToOne @RestResource(exported = false)
	private User seller;
	
	@NotNull
	private String description;
	
	@NotNull
	private BillingType billingType;
	
	private Integer startingAmount;
	
	@NotNull
	private LocalDateTime deadline;
	
	@NotNull
	private LocalDateTime projectDateTime;


	@OneToOne
	private Bid lowestBid;
	
	
	public Project() {}
		
	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	public BillingType getBillingType() {
		return billingType;
	}

	public void setBillingType(BillingType billingType) {
		this.billingType = billingType;
	}

	public Integer getStartingAmount() {
		return startingAmount;
	}

	public void setStartingAmount(Integer startingAmount) {
		this.startingAmount = startingAmount;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	public LocalDateTime getProjectDateTime() {
		return projectDateTime;
	}

	public void setProjectDateTime(LocalDateTime projectDateTime) {
		this.projectDateTime = projectDateTime;
	}

	public Bid getLowestBid() {
		return lowestBid;
	}

	public void setLowestBid(Bid lowestBid) {
		this.lowestBid = lowestBid;
	}
	

	public static class ProjectBuilder {
		
		private Project project;
		
		public ProjectBuilder() { project = new Project(); }
		
		public ProjectBuilder seller(User seller) 			{ this.project.setSeller(seller); return this; }
		public ProjectBuilder description(String description) 	{ this.project.setDescription(description); return this; }
		public ProjectBuilder billingType(BillingType billingType) { this.project.setBillingType(billingType); return this; }
		public ProjectBuilder startingAmount(Integer startingAmount) 	{ this.project.setStartingAmount(startingAmount); return this; }
		public ProjectBuilder deadline(LocalDateTime deadline) { this.project.setDeadline(deadline); return this; }
		public ProjectBuilder projectDateTime(LocalDateTime projectDateTime) { this.project.setProjectDateTime(projectDateTime); return this; }
		public ProjectBuilder lowestBid(Bid lowestBid) 		{ this.project.setLowestBid(lowestBid); return this; }
		
		public Project build() { return project; }
	}

}
