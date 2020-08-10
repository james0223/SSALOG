package com.ssalog.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class GroupRegist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 			// PK
	
	@ManyToOne(targetEntity=Group.class, fetch=FetchType.LAZY)
	@JoinColumn(name="groupname")
	private String groupname;
	
	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private String username;
	
	private String introduce;
	
	@CreationTimestamp
    private Date regdate;	// 가입신청일

	public GroupRegist(Long id, String groupname, String username, String introduce, Date regdate) {
		super();
		this.id = id;
		this.groupname = groupname;
		this.username = username;
		this.introduce = introduce;
		this.regdate = regdate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
}
