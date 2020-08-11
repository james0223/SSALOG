package com.ssalog.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@Entity
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Groupdetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
	
	@ManyToOne(targetEntity=GroupDTO.class, fetch=FetchType.LAZY)
	@JoinColumn(name="groupname")
	private GroupDTO groupdto;
	
	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private Account account;
	
	@Column(nullable=false,length=30)
	@Enumerated(EnumType.STRING)
	private GroupRole role;
	
	
	public Groupdetail(Long id, GroupDTO groupdto, Account account, GroupRole role, Date regdate) {
		super();
		this.id = id;
		this.groupdto = groupdto;
		this.account = account;
		this.role = role;
		this.regdate = regdate;
	}

	@CreationTimestamp
    private Date regdate;	// 그룹가입일

	public Groupdetail() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public GroupDTO getGroupdto() {
		return groupdto;
	}

	public void setGroupdto(GroupDTO groupdto) {
		this.groupdto = groupdto;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public GroupRole getRole() {
		return role;
	}

	public void setRole(GroupRole role) {
		this.role = role;
	}

	public Date getRegdate() {
		return regdate;
	}

	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}

	
	
	
}
