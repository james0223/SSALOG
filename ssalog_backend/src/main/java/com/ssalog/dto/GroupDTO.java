package com.ssalog.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class GroupDTO {
	@Id
	@Column(nullable=false, unique=true, length=50)
	private String groupname; 	// 그룹이름
	
	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
	@JoinColumn(name="username")
	private Long groupowner;
	
	private String groupdesc;
	
	public GroupDTO() {}

	public GroupDTO(String groupname, Long groupowner, String groupdesc) {
		super();
		this.groupname = groupname;
		this.groupowner = groupowner;
		this.groupdesc = groupdesc;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Long getGroupowner() {
		return groupowner;
	}

	public void setGroupowner(Long groupowner) {
		this.groupowner = groupowner;
	}

	public String getGroupdesc() {
		return groupdesc;
	}

	public void setGroupdesc(String groupdesc) {
		this.groupdesc = groupdesc;
	}
	
}
