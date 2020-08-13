package com.ssalog.service;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.text.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssalog.dto.Account;
import com.ssalog.dto.GroupDTO;
import com.ssalog.dto.GroupGoal;
import com.ssalog.dto.GroupRegist;
import com.ssalog.dto.GroupRole;
import com.ssalog.dto.Groupdetail;
import com.ssalog.dto.PostSub;
import com.ssalog.dto.Problem;
import com.ssalog.repository.AccountRepository;
import com.ssalog.repository.GroupDetailRepository;
import com.ssalog.repository.GroupGoalRepository;
import com.ssalog.repository.GroupRegistRepository;
import com.ssalog.repository.GroupRepository;
import com.ssalog.repository.PostRepository;
import com.ssalog.repository.PostSubRepository;
import com.ssalog.repository.ProblemRepository;

@Transactional
@Service
public class GroupServiceImpl implements GroupService{

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	GroupRegistRepository groupRegistRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	GroupDetailRepository groupDetailRepository;

	@Autowired
	GroupGoalRepository groupGoalRepository;

	@Autowired
	PostSubRepository postSubRepository;
	@Autowired
	PostRepository postRepository;
	// 그룹 만들기
	@Override
	public String makeGroup(GroupDTO g,String username) {
		GroupDTO is_g = groupRepository.findByGroupname(g.getGroupname());
		if(is_g != null) {
			return "is exist";
		}else {
			Groupdetail gd = new Groupdetail();
			Account ac = new Account();
			ac.setUsername(username);
			gd.setAccount(ac);
			gd.setGroupdto(is_g);
			gd.setRole(GroupRole.owner);
			gd.setGroupdto(g);
			g.setAccount(ac);
			groupRepository.save(g);
			groupDetailRepository.save(gd);
			return "success";
		}
	}

	// 그룹 가입 신청
	@Override
	public String applyGroup(String groupname, String username, String introduce) {
		GroupRegist g = groupRegistRepository.findByGroupdto_groupnameAndAccount_username(groupname, username);
		if(g != null) {
			return "exist";
		}else {
			Account ac = accountRepository.findByUsername(username);
			GroupDTO gd = groupRepository.findByGroupname(groupname);
			GroupRegist gr = new GroupRegist();
			gr.setAccount(ac);
			gr.setGroupdto(gd);
			gr.setIntroduce(introduce);
			groupRegistRepository.save(gr);
			return "success";
		}
	}
	// 가입 신청 목록
	@Override
	public List<Map<String, String>> appliylist(String username,String groupname) {
		GroupDTO g = groupRepository.findByAccount_usernameAndGroupname(username, groupname);
		List<Map<String, String>> li = new ArrayList<>();
		if(g != null) {
			List<GroupRegist> list = groupRegistRepository.findByGroupdto_groupname(groupname);
			for(int i=0; i<list.size(); i++) {
				Map<String,String> m = new HashMap<>();
				m.put("regid", String.valueOf(list.get(i).getId()));
				m.put("nickname",list.get(i).getAccount().getNickname());
				m.put("introduce", list.get(i).getIntroduce()==null?"가입부탁드립니다 ^^":list.get(i).getIntroduce());
				m.put("img",list.get(i).getAccount().getImgpath()==null?"default.png":list.get(i).getAccount().getImgpath());
				li.add(m);
			}
			return li;
			//		}else {
			//			return null;
		}
		return null;
	}
	// 가입 신청 수락
	@Override
	public String applyaccept(String username,String groupname,Long regid){
		GroupDTO g = groupRepository.findByAccount_usernameAndGroupname(username,groupname);
		if(g!= null) {
			// regist에서 삭제하고, 해당 user를 groupdetail에 넣어주면됨
			Optional<GroupRegist> gr = groupRegistRepository.findById(regid);
			if(gr.isPresent()) {
				String reg_user = gr.get().getAccount().getUsername();
				groupRegistRepository.delete(gr.get());
				Groupdetail gd = new Groupdetail();
				Account ac = new Account();
				GroupDTO gdto = new GroupDTO();
				gdto.setGroupname(groupname);
				ac.setUsername(reg_user);
				gd.setAccount(ac);
				gd.setGroupdto(gdto);
				gd.setRole(GroupRole.member);
				groupDetailRepository.save(gd);
				return "success";
			}
		}
		return "fail";
	}
	// 가입 신청 거절
	@Override
	public String applyreject(String username,String groupname, Long regid){
		GroupDTO g = groupRepository.findByAccount_usernameAndGroupname(username,groupname);
		if(g!= null) {
			// regist에서 삭제하고, 해당 user를 groupdetail에 넣어주면됨
			Optional<GroupRegist> gr = groupRegistRepository.findById(regid);
			if(gr.isPresent()) {
				String reg_user = gr.get().getAccount().getUsername();
				groupRegistRepository.delete(gr.get());
				return "success";
			}
		}
		return "fail";
	}
	// 문제 제출하기
	@Override
	public String makeGoal(String username, String groupname, String problemid, String problemname, Date limit) {
		GroupDTO g = groupRepository.findByAccount_usernameAndGroupname(username, groupname);
		if(g!=null) {
			GroupGoal gg = new GroupGoal();
			gg.setCnt(0L);
			gg.setDate(limit);
			gg.setProblemid(problemid);
			gg.setProblemname(problemname);
			gg.setGroupdto(g);
			groupGoalRepository.save(gg);
			return "success";
		}
		return "fail";
	}

	// 목표 검사
	@Override
	public Map<String, Boolean> checkGoal(String groupname,String problemid) {
		List<Groupdetail> gd = groupDetailRepository.findByGroupdto_groupname(groupname);
		Map<String, Boolean> m = new HashMap<>();
		for(int i=0; i< gd.size(); i++) {
			Groupdetail gd2 = gd.get(i);
			List<PostSub> ps = postSubRepository.findByUsernameAndProblemid(gd2.getAccount().getUsername(), problemid);
			if(ps !=null) {
				m.put(gd2.getAccount().getNickname(), true);
			}else {
				m.put(gd2.getAccount().getNickname(), false);
			}
		}
		return m;
	}

	@Override
	public Map<String, GroupRole> myGroup(String username){
		List<Groupdetail> gd = groupDetailRepository.findByAccount_username(username);
		if(gd != null) {
			Map<String, GroupRole> m = new HashMap<>();
			for(int i=0; i<gd.size(); i++) {
				m.put(gd.get(i).getGroupdto().getGroupname(), gd.get(i).getRole());
			}
			return m;
		}
		return null;
	}

	@Override
	public Page<Problem> preGoal(String username, String groupname, PageRequest pageable) {
		Groupdetail gd = groupDetailRepository.findByAccount_usernameAndGroupdto_groupname(username,groupname);
		if(gd != null) {
			Date time = new Date();
			List<GroupGoal> list = groupGoalRepository.findByDateGreaterThanAndGroupdto_groupname(time,groupname);
			String[] slist = new String[list.size()];
			Map<String, String> m = new HashMap<>();
			for(int i=0; i<list.size(); i++) {
				GroupGoal gg = list.get(i);
				slist[i] = gg.getProblemid();
			}
			return postRepository.solved_list(slist, pageable);
		}
		return null;
	}
	@Override
	public Page<Problem> postGoal(String username, String groupname, PageRequest pageable) {
		Groupdetail gd = groupDetailRepository.findByAccount_usernameAndGroupdto_groupname(username,groupname);
		if(gd != null) {
			Date time = new Date();
			List<GroupGoal> list = groupGoalRepository.findByDateLessThanAndGroupdto_groupname(time,groupname);
			String[] slist = new String[list.size()];
			Map<String, String> m = new HashMap<>();
			for(int i=0; i<list.size(); i++) {
				GroupGoal gg = list.get(i);
				slist[i] = gg.getProblemid();
			}
			return postRepository.solved_list(slist, pageable);
		}
		return null;
	}

	@Override
	public List<Map<String,Object>> Mymember(String username, String groupname) {
		Groupdetail gd = groupDetailRepository.findByAccount_usernameAndGroupdto_groupname(username,groupname);
		if(gd != null) {
			List<Groupdetail> glist = groupDetailRepository.findByGroupdto_groupname(groupname);
			List<Map<String,Object>> lm = new ArrayList<>();
			for(int i=0; i<glist.size(); i++) {
				Map<String, Object> m = new HashMap<>();
				Account ac = glist.get(i).getAccount();
				m.put("nickname", ac.getNickname());
				m.put("role", glist.get(i).getRole());
				m.put("img", ac.getImgpath()==null?"default.png":ac.getImgpath());
				lm.add(m);
			}
			return lm;
		}
		return null;
	}
	//	// 문제이름, 푼사람, 안푼사람
	//	public void teamstatus(String nickname, String groupname) {
	//		Groupdetail gd = groupDetailRepository.findByAccount_nicknameAndGroupdto_groupname(nickname,groupname);
	//		if(gd != null) {
	//			List<Map<String,String>> result = new ArrayList<>();
	//			Date time = new Date();
	//			List<Groupdetail> glist = groupDetailRepository.findByGroupdto_groupname(groupname);
	//			List<GroupGoal> list = groupGoalRepository.findByDateGreaterThanAndGroupdto_groupname(time,groupname);
	//			for(int i=0; i<list.size() ;i++) {
	//				GroupGoal gg = list.get(i);
	//				Map<String, String> m = new HashMap<>();
	//				gg.getProblemid();
	//				for(int j=0; j<glist.size(); j++) {
	//					List<PostSub> ps = postSubRepository.findByUsernameAndProblemid(glist.get(j).getAccount().getUsername(), gg.getProblemid());
	//					if(ps!=null) {
	//						m.put("problemname", gg.getProblemname());
	//						m.put("problemid", gg.getProblemid());
	//					}
	//				}
	//				System.out.println(list.get(i).getDate());
	//				System.out.println("=====================");
	//			}
	//		}else {
	//			//return "그룹원이  아닙니다";
	//		}
	//
	//	}
}
