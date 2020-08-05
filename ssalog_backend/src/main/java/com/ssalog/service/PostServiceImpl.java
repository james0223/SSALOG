package com.ssalog.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.dto.Problem;
import com.ssalog.dto.jandi;
import com.ssalog.dto.solvelang;
import com.ssalog.repository.PostRepository;
import com.ssalog.repository.ProblemRepository;


@Service
public class PostServiceImpl implements PostService{
	@Autowired
	PostRepository postRepository;
	@Autowired
	ProblemRepository problemRepository;
	
	@Override
	public Post write_post(Post post,String username) {
		post.setUsername(username);
		return postRepository.save(post);
	}

	@Override
	public Post read_post(String post_pk) {
		Optional<Post> p = postRepository.findById(post_pk);
		if(p.isPresent()) {
			return postRepository.findById(post_pk).get();
		}else {
			return null;
		}
	}

	
	@Override
	public int delete_post(String post_pk, String username) {
		Optional<Post> p = postRepository.findById(post_pk);
		if(p.isPresent()) { // 존재하면
			Post p1 = p.get();
			if(p1.getUsername().equals(username)) {
				postRepository.delete(p1);
				return 1;
			}else {
				return 2;
			}
		}else {
			return 3;
		}
	}

	@Override
	public int update_post(Post p, String username) {
		Optional<Post> p1 = postRepository.findById(p.getScoring());
		Date date = new Date();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		String time1 = format1.format(date);
		String time2 = format2.format(date);
		p.setRegdate(time1);
		p.setRegtime(time2);
		if(p1.isPresent()) {
			//System.out.println(username + " 사람이름? " + p.getUsername() );
			if(p1.get().getUsername().equals(username)) {
				postRepository.save(p);
				return 1;
			}else {
				return 2;
			}
		}else {
			return 3;
		}
	}

	public boolean is_post(String Scoring) {
		Optional<Post> p = postRepository.findById(Scoring);
		if(p.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	
	public List<Map<String, Object>> find_jandi(String username){
		List<jandi> list = postRepository.getJandiCount(username).getMappedResults();
		List<Map<String,Object>> mlist = new ArrayList<>();
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> m = new HashMap<>();
			m.put("date", list.get(i).get_id());
			m.put("count", list.get(i).getCount());
			mlist.add(m);
		}
		return mlist;
	}
	
	public Page<Post> findMyPost(String username, PageRequest pageable){
		return postRepository.findByUsername(username, pageable);
	}
	
	public Page<Post> findkey(List<String> keyword, PageRequest pageable){
		return postRepository.findAnyOfTheseValues(keyword, pageable);
	}
	
	public Map<String, Object> detail_service(String problemid, String language) {
		Map<String, Object> m = new HashMap<>();
		Problem problem = problemRepository.findByProblemid(problemid);
		Map<String, solvelang> map = problem.getLanguage();
		if(map!=null) {
			solvelang lang = map.get(language);
			double avg_t = Math.round((double)lang.getTime_sum()/lang.getCnt());
			double avg_m = Math.round((double)lang.getMemory_sum()/lang.getCnt());
			m.put("avg_time", avg_t);
			m.put("avg_memory", avg_m);
		}
		return m;
	}
	public Map<String, Double> detail_py(String problemid){
		Problem problem = problemRepository.findByProblemid(problemid);
		Map<String, Integer> m = problem.getKey();
		Map<String, Double> result = new TreeMap<String, Double>();
		long div = problem.getAll_cnt();
		for (String key : m.keySet()) {
            Integer value = m.get(key);
            double val = Math.round((((double)value/div)*100)*10)/10.0;
            result.put(key, val);
        }
		return result;
	}
	public void input_problem(Problem problem){
		problemRepository.save(problem);
	}
	
	@Override
	public Page<Post> select_by_problemid(String problemid, PageRequest pageable) {
		return postRepository.findByProblemid(problemid, pageable);
	}

	@Override
	public Page<Post> select_by_problemname(String problemname, PageRequest pageable){
		return postRepository.findByProblemnameLike(problemname, pageable);
	}

	@Override
	public Page<Post> select_by_keyword(List<String> keyword, PageRequest pageable){
		return postRepository.findByKeyword(keyword, pageable);
	}
}
