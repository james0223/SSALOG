package com.ssalog.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.JoinColumnOrFormula;

@Entity
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
	
//	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
//	@JoinColumnOrFormula(column = @JoinColumn(name="username"))
//	private Account follower;
//	
//	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
//	@JoinColumnOrFormula(column = @JoinColumn(name="username"))
//	private Account following;
//
//	public Follow() {}
//	public Follow(Long id, Account follower, Account following) {
//		super();
//		this.id = id;
//		this.follower = follower;
//		this.following = following;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Account getFollower() {
//		return follower;
//	}
//
//	public void setFollower(Account follower) {
//		this.follower = follower;
//	}
//
//	public Account getFollowing() {
//		return following;
//	}
//
//	public void setFollowing(Account following) {
//		this.following = following;
//	}
//	
	
}
