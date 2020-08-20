package com.ssalog.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class GroupGoal {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 			// PK
	
	@ManyToOne(targetEntity=GroupDTO.class, fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="groupname")
	private GroupDTO groupdto;
	private String problemid;
	private String problemname;
	private Date date;
	private Long cnt;
	private String mention;
	public GroupGoal() {}

	public GroupGoal(Long id, GroupDTO groupdto, String problemid, String problemname, Date date, Long cnt,
			String mention) {
		super();
		this.id = id;
		this.groupdto = groupdto;
		this.problemid = problemid;
		this.problemname = problemname;
		this.date = date;
		this.cnt = cnt;
		this.mention = mention;
	}

	public String getMention() {
		return mention;
	}

	public void setMention(String mention) {
		this.mention = mention;
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

	public String getProblemid() {
		return problemid;
	}

	public void setProblemid(String problemid) {
		this.problemid = problemid;
	}

	public String getProblemname() {
		return problemname;
	}

	public void setProblemname(String problemname) {
		this.problemname = problemname;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getCnt() {
		return cnt;
	}

	public void setCnt(Long cnt) {
		this.cnt = cnt;
	}
	
	
}
// 그룹이름, 마감기한, 문제번호, 문제이름, 푼사람