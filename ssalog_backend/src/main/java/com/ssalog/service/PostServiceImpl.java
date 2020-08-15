package com.ssalog.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssalog.dto.Account;
import com.ssalog.dto.Post;
import com.ssalog.dto.Problem;
import com.ssalog.dto.TempPost;
import com.ssalog.dto.jandi;
import com.ssalog.dto.solvelang;
import com.ssalog.repository.AccountRepository;
import com.ssalog.repository.PostRepository;
import com.ssalog.repository.ProblemRepository;
import com.ssalog.repository.TempPostRepository;


@Service
public class PostServiceImpl implements PostService{
	@Autowired
	PostRepository postRepository;
	@Autowired
	ProblemRepository problemRepository;
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	TempPostRepository tempPostRepository;
	@Override
	public void write_post(TempPost post) {
		String changelang = unify_lang(post.getLanguage());
		post.setLanguage(changelang);
		tempPostRepository.save(post);
	}

	@Override
	public Post read_post(String post_pk) {
		Optional<Post> p = postRepository.findById(post_pk);
		if(p.isPresent()) {
			return p.get();
		}else {
			return null;
		}
	}
	@Override
	public TempPost read_temppost(String post_pk) {
		Optional<TempPost> p = tempPostRepository.findById(post_pk);
		if(p.isPresent()) {
			return p.get();
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
				Problem problem = problemRepository.findByProblemid(p1.getProblemid());
				problem = delete_problem(problem, p1);
				if(problem != null) {
					problemRepository.save(problem);
				}
				postRepository.delete(p1);
				return 1;
			}else {
				return 2;
			}
		}else {
			return 3;
		}
	}
	public Problem delete_problem(Problem pr, Post p) {
		if(pr.getAll_cnt() == 1) {
			problemRepository.delete(pr);
			return null;
		}else {
			// 총 개수 하나빼고
			pr.setAll_cnt(pr.getAll_cnt()-1);
			// 이전꺼를 지우자 , keyword부터
			List<String> key = p.getKeyword(); 
			if(key!= null) {
				Map<String,Integer> m1 = pr.getKey();
				for(int i=0; i<key.size(); i++) {
					if(m1.containsKey(key.get(i))) {
						int key_val = m1.get(key.get(i));
						key_val -=1;
						if(key_val == 0) {m1.remove(key.get(i));}
						else{
							m1.put(key.get(i), key_val);
						}
					}
				}
				pr.setKey(m1);
			}
			// solvelang에서 이거만큼 지워
			Map<String, solvelang> m2 = pr.getLanguage();
			solvelang l = m2.get(p.getLanguage());
			if((l.getCnt()-1)==0) {m2.remove(p.getLanguage());}
			else{
				l.setCnt(l.getCnt()-1);
				l.setMemory_sum(l.getMemory_sum()-p.getMemory());
				l.setTime_sum(l.getTime_sum()-p.getTime());
				m2.put(p.getLanguage(), l);
			}
			pr.setLanguage(m2);
			return pr;
		}
	}
	public void update_problem(Problem pr, Post p) {
		if(pr == null) {
			pr = new Problem();
		}
		pr.setName(p.getProblemname());
		pr.setAll_cnt(pr.getAll_cnt()+1);
		pr.setProblemid(p.getProblemid());
		List<String> key = p.getKeyword();
		if(key!= null) {
			Map<String,Integer> m1 = pr.getKey();
			if(m1 == null) {
				m1 = new HashMap<String, Integer>();
			}
			for(int i=0; i<key.size(); i++) {
				if(m1.containsKey(key.get(i))) {
					int key_val = m1.get(key.get(i));
					key_val +=1;
					m1.put(key.get(i), key_val);
				}else {
					m1.put(key.get(i), 1);
				}
			}
			pr.setKey(m1);
		}
		Map<String, solvelang> m2 = pr.getLanguage();
		if(m2 == null) {
			m2 = new HashMap<>();
		}
		solvelang l = m2.get(p.getLanguage());
		if(l == null) {
			l = new solvelang();
		}
		l.setCnt(l.getCnt()+1);
		l.setMemory_sum(l.getMemory_sum()+p.getMemory());
		l.setTime_sum(l.getTime_sum()+p.getTime());
		m2.put(p.getLanguage(), l);
		pr.setLanguage(m2);
		input_problem(pr);
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
		if(p1.isPresent()) { // 이전에 해당 채점번호로 작성한 글이 존재하면?
			if(p1.get().getUsername().equals(username)) { // 똑같은 작성자인지 확인.
				System.out.println("여기1");
				Problem problem = problemRepository.findByProblemid(p.getProblemid());
				if(problem == null) {
					problem = new Problem();
					problem.setStarter(username);
				}else {
					problem = delete_problem(problem, p1.get());
				}
				update_problem(problem, p);
				p.setUsername(username);
				postRepository.save(p); // 맞으면 update
				return 1;
			}else { // 자기가 작성한 글이 아니면, 반려
				return 2;
			}
		}else { // 해당 채점번호로 작성한 글이 없으면.
			System.out.println("여기2");
			Problem problem = problemRepository.findByProblemid(p.getProblemid());
			if(problem == null) {
				problem = new Problem();
				problem.setStarter(username);
			}
			update_problem(problem,p);
			p.setIswrite(true);
			p.setUsername(username);
			postRepository.save(p);
			return 3;
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
	public Map<String, Integer> detail_py(String problemid){
		Problem problem = problemRepository.findByProblemid(problemid);
		Map<String, Integer> m = problem.getKey();
		Map<String, Integer> result = new TreeMap<String, Integer>();
		//		long div = problem.getAll_cnt();
		for (String key : m.keySet()) {
			Integer value = m.get(key);
			//            double val = Math.round((((double)value/div)*100)*10)/10.0;
			result.put(key, value);
		}
		return result;
	}
	public void input_problem(Problem problem){
		problemRepository.save(problem);
	}

	@Override
	public String find_problemname(String problemid){
		List<Post> l = postRepository.findByProblemid(problemid);
		if(l == null) {
			return "not exists";
		}else {
			return l.get(0).getProblemname();
		}
	}
	@Override
	public void set_username(String username,String scoring){
		Optional<TempPost> p = tempPostRepository.findById(scoring);
		p.get().setUsername(username);
		tempPostRepository.save(p.get());
	}
	public long get_allpost() {
		return postRepository.count();
	}

	@Override
	public Boolean is_solved(String username, String problemid) {
		List<Post> p =postRepository.findByUsernameAndProblemid(username, problemid);
		if(p.size() == 0) {
			return false;
		}else {
			return true;
		}
	}
	
	@Override
	public Boolean is_write(String Scoring) {
		Optional<Post> p1 = postRepository.findById(Scoring);
		if(p1.isPresent()) {
			return true;
		}else {
			return false;
		}
	}
	
	public String unify_lang(String language) {
		String result = language;
		if(result.equals("Java") || result.equals("Java (OpenJDK)") || result.equals("Java 11")) {
			result = "Java";
		}else if(result.equals("C++14") || result.equals("C++") || result.equals("C++11") || result.equals("C++17") || result.equals("C++2a")){
			result = "C++";
		}else if(result.equals("C") || result.equals("C11")) {
			result = "C";
		}else if(result.equals("Python 3") || result.equals("PyPy3") || result.equals("Python 2") || result.equals("PyPy2")){
			result = "Python";
		}
		return result;
	}
}
