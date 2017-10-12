package org.jz.marketplace.data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Project {
	
	public static enum BillingType {
		HOURLY, FIXED;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long projectId;

	@ManyToOne
	private User seller;
	@NotNull
	private String description;
	@NotNull
	private BillingType billingType;
	private int startingBid;
	@NotNull
	private LocalDateTime deadline;
	
	
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

	public int getStartingBid() {
		return startingBid;
	}

	public void setStartingBid(int startingBid) {
		this.startingBid = startingBid;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", seller=" + seller + ", billingType=" + billingType
				+ ", startingBid=" + startingBid + ", deadline=" + deadline + "]";
	}

}
