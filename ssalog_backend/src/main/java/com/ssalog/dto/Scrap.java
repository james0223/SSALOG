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
	
	@ManyToOne(targetEntity=Account.class, fetch=FetchType.LAZY)
	@JoinColumnOrFormula(column = @JoinColumn(name="scrapeduser"))
	private Account scrapeduser;
	public Scrap() {}

	public Scrap(Long id, Account scrap, String scraped, Account scrapeduser) {
		super();
		this.id = id;
		this.scrap = scrap;
		this.scraped = scraped;
		this.scrapeduser = scrapeduser;
	}


	public Account getScrapeduser() {
		return scrapeduser;
	}


	public void setScrapeduser(Account scrapeduser) {
		this.scrapeduser = scrapeduser;
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
