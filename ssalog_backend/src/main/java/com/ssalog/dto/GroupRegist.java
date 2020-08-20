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

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@Entity
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class GroupRegist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 			// PK
	
	@ManyToOne(targetEntity=GroupDTO.class, fetch=FetchType.LAZY)
	@JoinColumn(name="groupname")
	private GroupDTO groupdto;
	
	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private Account account;
	
	private String introduce;
	
	@CreationTimestamp
    private Date regdate;	// 가입신청일

	public GroupRegist() {}
	public GroupRegist(Long id, GroupDTO groupdto, Account account, String introduce, Date regdate) {
		super();
		this.id = id;
		this.groupdto = groupdto;
		this.account = account;
		this.introduce = introduce;
		this.regdate = regdate;
	}
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
