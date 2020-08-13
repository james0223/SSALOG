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
public class Scrap {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; 
	
	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
	@JoinColumnOrFormula(column = @JoinColumn(name="scrap"))
	private Account scrap;
	
	private String scraped;

	public Scrap() {}

	public Scrap(Long id, Account scrap, String scraped) {
		super();
		this.id = id;
		this.scrap = scrap;
		this.scraped = scraped;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getScrap() {
		return scrap;
	}

	public void setScrap(Account scrap) {
		this.scrap = scrap;
	}

	public String getScraped() {
		return scraped;
	}

	public void setScraped(String scraped) {
		this.scraped = scraped;
	}
	
}
