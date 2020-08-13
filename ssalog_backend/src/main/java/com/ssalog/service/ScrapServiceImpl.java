package com.ssalog.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssalog.dto.Account;
import com.ssalog.dto.Post;
import com.ssalog.dto.PostSub;
import com.ssalog.dto.Scrap;
import com.ssalog.dto.Scrapedpost;
import com.ssalog.repository.AccountRepository;
import com.ssalog.repository.PostRepository;
import com.ssalog.repository.PostSubRepository;
import com.ssalog.repository.ScrapRepository;

@Service
public class ScrapServiceImpl implements ScrapService {
	@Autowired
	ScrapRepository scrapRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PostRepository postRepository;

	@Autowired
	PostSubRepository postSubRepository;
	@Override
	// 스크랩하기
	public String make_scrap(String username, String Scoring ) {
		Account account = accountRepository.findByUsername(username);
		Optional<Post> p = postRepository.findById(Scoring);
		if(p.isPresent() && account != null ) {
			Scrap s1 = scrapRepository.findByScrap_usernameAndScraped(username, Scoring);
			if(s1 == null) {
				p.get().setLike(p.get().getLike()+1);
				postRepository.save(p.get());
				Scrap s = new Scrap();
				s.setScrap(account);
				s.setScraped(p.get().getScoring());
				scrapRepository.save(s);
				return "success";
			}
		}
		return "fail";
	}
	@Override
	public String stop_scrap(String username, String Scoring) {
		Account account = accountRepository.findByUsername(username);
		Optional<Post> p = postRepository.findById(Scoring);
		if(p.isPresent() && account != null) {
			Scrap s1 = scrapRepository.findByScrap_usernameAndScraped(username, Scoring);
			if(s1 != null) {
				p.get().setLike(p.get().getLike()-1);
				postRepository.save(p.get());
				scrapRepository.delete(s1);
				return "success";
			}
		}
		return "fail";
	}
	@Override
	public List<Scrapedpost> get_scrapList(String nickname){
		List<Scrap> li = scrapRepository.findByScrap_nickname(nickname);
		Map<Integer, Scrapedpost> m = new HashMap<>();
		List<Scrapedpost> list = new ArrayList<>();
		for(int i=0; i<li.size(); i++) {
			Scrapedpost p = postSubRepository.findByScoring((li.get(i).getScraped()));
			list.add(p);
		}
		return list;
	}

	@Override
	public Boolean is_scraped(String username, String Scoring) {
		Scrap s = scrapRepository.findByScrap_usernameAndScraped(username, Scoring);
		if(s != null) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public long findScrapsu(String nickname) {
		Account ac = accountRepository.findByNickname(nickname);
		long cnt = 0;
		if(ac != null) {
			List<Post> list = postRepository.findlikesu(ac.getUsername());
			for(int i=0; i<list.size(); i++) {
				cnt += list.get(i).getLike();
			}
			return cnt;
		}
		else {
			return 0;
		}

		//System.out.println(postRepository.findlikesu(username).get(0)+ " 입니다!");
	}
}
