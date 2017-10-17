package org.jz.marketplace.data;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.rest.core.annotation.RestResource;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Bid {

	@Id	@GeneratedValue(strategy=GenerationType.AUTO)
	private long bidId;

	@ManyToOne @NotNull	@RestResource(exported = false)
	private Project project;

	@ManyToOne @NotNull @RestResource(exported = false)
	private User buyer;
	
	@NotNull
	private int amount;
	
	private LocalDateTime bidDateTime;
	
	public Bid() {}


	public long getBidId() {
		return bidId;
	}


	public void setBidId(long bidId) {
		this.bidId = bidId;
	}


	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	public User getBuyer() {
		return buyer;
	}


	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}


	public int getAmount() {
		return amount;
	}

	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	
	public LocalDateTime getBidDateTime() {
		return bidDateTime;
	}


	public void setBidDateTime(LocalDateTime bidDateTime) {
		this.bidDateTime = bidDateTime;
	}
	

	public static class BidBuilder {
		
		private Bid bid;
		
		public BidBuilder() { bid = new Bid(); }
		
		public BidBuilder project(Project project) 		{ this.bid.setProject(project); return this; }
		public BidBuilder buyer(User buyer) 			{ this.bid.setBuyer(buyer); return this; }
		public BidBuilder amount(int amount) 			{ this.bid.setAmount(amount); return this; }
		public BidBuilder bidDateTime(LocalDateTime bidDateTime) 	{ this.bid.setBidDateTime(bidDateTime); return this; }
		public Bid build() { return bid; }
	}


}
