package com.ssalog.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;


@Entity
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class GroupDTO {
	@Id
	@Column(nullable=false, unique=true, length=50)
	private String groupname; 	// 그룹이름
	
	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
	@JoinColumn(name="groupowner")
	private Account account;
	
	private String groupdesc;
	
	public GroupDTO() {}

	public GroupDTO(String groupname, Account account, String groupdesc) {
		super();
		this.groupname = groupname;
		this.account = account;
		this.groupdesc = groupdesc;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getGroupdesc() {
		return groupdesc;
	}

	public void setGroupdesc(String groupdesc) {
		this.groupdesc = groupdesc;
	}


	

	
	
}
