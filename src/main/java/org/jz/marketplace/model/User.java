package org.jz.marketplace.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	
	@Id	@GeneratedValue(strategy=GenerationType.AUTO)
	private long userId;
	
	@Column(unique=true)
	private String username;
	
	private boolean isBuyer;
	private boolean isSeller;
	
	public User() {}
	
		
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public boolean isBuyer() {
		return isBuyer;
	}
	
	public void setIsBuyer(boolean isBuyer) {
		this.isBuyer = isBuyer;
	}
	
	public boolean isSeller() {
		return isSeller;
	}
	
	public void setIsSeller(boolean isSeller) {
		this.isSeller = isSeller;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", isBuyer=" + isBuyer + ", isSeller=" + isSeller
				+ "]";
	}


	public static class UserBuilder {
		
		private User user;
		
		public UserBuilder() { user = new User(); }
		
		public UserBuilder userId(long userId)			{ this.user.setUserId(userId); return this; }
		public UserBuilder username(String username) 	{ this.user.setUsername(username); return this; }
		public UserBuilder isBuyer(boolean isBuyer) 	{ this.user.setIsBuyer(isBuyer); return this; }
		public UserBuilder isSeller(boolean isSeller) 	{ this.user.setIsSeller(isSeller); return this; }
		
		public User build() { return user; }
	}

}
