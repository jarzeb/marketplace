package org.jz.marketplace.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userId;
	
	@Column(unique=true)
	private String username;
	
	private boolean isBuyer;
	private boolean isSeller;
	
	protected User() {}
	
	public User(String username, boolean isBuyer, boolean isSeller) {
		this.username = username;
		this.isBuyer = isBuyer;
		this.isSeller = isSeller;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public boolean isBuyer() {
		return isBuyer;
	}
	
	public void setBuyer(boolean isBuyer) {
		this.isBuyer = isBuyer;
	}
	
	public boolean isSeller() {
		return isSeller;
	}
	
	public void setSeller(boolean isSeller) {
		this.isSeller = isSeller;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String toString() {
		return String.format("User[userId='%d', username='%s', isBuyer='%b', isSeller='%b']",
				getUserId(), getUsername(), isBuyer(), isSeller());
	}
}
