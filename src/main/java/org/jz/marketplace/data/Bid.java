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

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Bid {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long bidId;

	@ManyToOne
	@NotNull
	private Project project;

	@ManyToOne
	@NotNull
	private User buyer;
	
	@NotNull
	private Integer amount;
	
	private LocalDateTime bidDateTime = LocalDateTime.now();
	
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


	@Override
	public String toString() {
		return "Bid [bidId=" + bidId + ", project=" + project + ", buyer=" + buyer + ", amount=" + amount + "]";
	}
}
