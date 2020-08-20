package com.ssalog.dto;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import javax.persistence.*;
import java.util.Date;

@Entity
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Account {

	@Id
    @Column(nullable=false, unique=true, length=30)
    private String username; 	// 아이디 (이메일)
	
	@Column(nullable=false, unique=true, length=30)
	private String nickname; 	// 닉네임
    
    private String password;	// 비밀번호

    @CreationTimestamp
    private Date regdate;		// 등록일(회원가입일)

    @UpdateTimestamp
    private Date updatedate;	// 수정일

    private String role;		// 권한관리
    private String imgpath; 
    
    @Column(columnDefinition = "TEXT")
    private String introduce;
    
    @Column(columnDefinition = "TEXT")
    private String form;
    
    private String language;
	public Account() {
		super();
	}
	
	public Account(String username, String nickname, String password, Date regdate, Date updatedate, String role,
			String imgpath, String introduce, String form, String language) {
		super();
		this.username = username;
		this.nickname = nickname;
		this.password = password;
		this.regdate = regdate;
		this.updatedate = updatedate;
		this.role = role;
		this.imgpath = imgpath;
		this.introduce = introduce;
		this.form = form;
		this.language = language;
	}
	
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getForm() {
		return form;
	}

	
	public void setForm(String form) {
		this.form = form;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getImgpath() {
		return imgpath;
	}
	public void setImgpath(String imgpath) {
		this.imgpath = imgpath;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	

	

}
