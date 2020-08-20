package com.ssalog.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.ssalog.dto.PostSub;
import com.ssalog.dto.Problem;
import com.ssalog.repository.PostRepository;
import com.ssalog.repository.PostSubRepository;
import com.ssalog.repository.ProblemRepository;

@Service
public class PostSubServiceImpl implements PostSubService{
	@Autowired
	PostSubRepository postSubRepository;
	@Autowired
	PostRepository postRepository;

	@Autowired
	ProblemRepository problemRepository;
	@Override
	public Page<PostSub> select_by_problemid(String problemid,String language, PageRequest pageable) {
		return postSubRepository.findByProblemidAndLanguage(problemid, language, pageable);
	}

	@Override
	public Page<PostSub> findMyPost(String username, PageRequest pageable){
		return postSubRepository.findByUsername(username, pageable);
	}

	@Override
	public Page<PostSub> select_by_problemname(String problemname, PageRequest pageable){
		return postSubRepository.findByProblemnameLike(problemname, pageable);
	}

	public Page<PostSub> findkey(List<String> keyword, PageRequest pageable){
		return postSubRepository.findAnyOfTheseValues(keyword, pageable);
	}

	@Override
	public Page<PostSub> select_by_username(String username, PageRequest pageable){
		return postSubRepository.findByUsername(username, pageable);
	}
	@Override
	public Map<String, Integer> find_py_graph(String username, int num){
		List<PostSub> list = postSubRepository.findPyGraphTouser(username);
		Map<String, Integer> result = new TreeMap<String, Integer>();
		for(int i=0; i<list.size(); i++) {
			List<String> sub = list.get(i).getKeyword();
			for(int j=0; j<sub.size(); j++) {
				String key = sub.get(j);
				if(result.containsKey(key)) {
					int key_val = result.get(key);
					key_val +=1;
					result.put(key, key_val);
				}else {
					result.put(key, 1);
				}
			}
		}
		Iterator<Object> it = sortByValue(result).iterator();
		int k = 0;
		Map<String, Integer> result2 = new TreeMap<String, Integer>();
		while(it.hasNext()) {
            if(k == num) break;
			String temp = (String) it.next();
			result2.put(temp,result.get(temp));
            k++;
        }
		return result2;
	}

	@Override
	public long problem_cnt(String problemid) {
		return postSubRepository.findByProblemid(problemid).size();
	}
	public List sortByValue(Map map) {
        List<String> list = new ArrayList<>();
        list.addAll(map.keySet());
        Collections.sort(list,new Comparator<Object>() {
            public int compare(Object o1,Object o2) {
                Object v1 = map.get(o1);
                Object v2 = map.get(o2);
                return ((Comparable) v2).compareTo(v1);
            }
        });
        return list;
    }
	@Override
	public List<PostSub> latestPost(int cnt){
		List<PostSub> list = postRepository.latestpost(cnt).getMappedResults();
		return list;
	}
	@Override
	public Map<String, Integer> detail_py(String problemid, int cnt){
		Problem problem = problemRepository.findByProblemid(problemid);
		Map<String, Integer> result = new TreeMap<String, Integer>();
		if(problem != null) {
			Map<String, Integer> m = problem.getKey();
			for (String key : m.keySet()) {
				Integer value = m.get(key);
				result.put(key, value);
			}
		}
		Iterator<Object> it = sortByValue(result).iterator();
		int k = 0;
		Map<String, Integer> result2 = new TreeMap<String, Integer>();
		while(it.hasNext()) {
            if(k == cnt) break;
			String temp = (String) it.next();
			result2.put(temp,result.get(temp));
            k++;
        }
		return result2;
	}
}
