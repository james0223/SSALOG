package com.ssalog.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Groupdetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
	
	@ManyToOne(targetEntity=GroupDTO.class, fetch=FetchType.LAZY)
	@JoinColumn(name="groupname")
	private String groupname;
	
	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private String username;
	
	@Column(nullable=false, unique=true, length=30)
	private String role;
	
	@CreationTimestamp
    private Date regdate;	// 그룹가입일

	public Groupdetail() {}

	public Groupdetail(Long id, String groupname, String username, String role, Date regdate) {
		super();
		this.id = id;
		this.groupname = groupname;
		this.username = username;
		this.role = role;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
}
