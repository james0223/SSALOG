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
			if(gd.getAccount().getUsername().equals(username)) {
				return "mygroup";
			}else {
				GroupRegist gr = new GroupRegist();
				gr.setAccount(ac);
				gr.setGroupdto(gd);
				gr.setIntroduce(introduce);
				groupRegistRepository.save(gr);
				return "success";
			}
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
				groupRegistRepository.delete(gr.get());
				return "success";
			}
		}
		return "fail";
	}
	// 문제 제출하기
	@Override
	public String makeGoal(String username, String groupname, String problemid, String problemname, Date limit,String mention) {
		GroupDTO g = groupRepository.findByAccount_usernameAndGroupname(username, groupname);
		if(g!=null) {
			GroupGoal is_gg= groupGoalRepository.findByProblemidAndGroupdto_groupname(problemid, groupname);
			if(is_gg != null) {
				return "fail";
			}else {
				GroupGoal gg = new GroupGoal();
				gg.setCnt(0L);
				gg.setDate(limit);
				gg.setProblemid(problemid);
				gg.setProblemname(problemname);
				gg.setGroupdto(g);
				gg.setMention(mention);
				groupGoalRepository.save(gg);
				return "success";
			}
		}
		return "fail";
	}

	// 목표 검사
	@Override
	public Map<String, String> checkGoal(String groupname,String problemid) {
		List<Groupdetail> gd = groupDetailRepository.findByGroupdto_groupname(groupname);
		GroupGoal gg = groupGoalRepository.findByProblemidAndGroupdto_groupname(problemid, groupname);
		if(gg != null) {
			Map<String, String> m = new HashMap<>();
			for(int i=0; i< gd.size(); i++) {
				Groupdetail gd2 = gd.get(i);
				List<PostSub> ps = postSubRepository.findByUsernameAndProblemid(gd2.getAccount().getUsername(), problemid);
				if(ps.size() != 0) {
					// 살짝애매하네 고쳐야함.
					m.put(gd2.getAccount().getNickname(), "https://ssalog.gq/SSALOG/"+gd2.getAccount().getNickname()+"/Solution/"+ps.get(0).getScoring());
				}else {
					m.put(gd2.getAccount().getNickname(), "false");
				}
			}
			return m;
		}
		return null;
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
	public List<Map<String,Object>> preGoal(String username, String groupname, PageRequest pageable) {
		Groupdetail gd = groupDetailRepository.findByAccount_usernameAndGroupdto_groupname(username,groupname);
		if(gd != null) {
			Date time = new Date();
			List<GroupGoal> list = groupGoalRepository.findByDateGreaterThanAndGroupdto_groupname(time,groupname);
			List<Map<String,Object>> lm = new ArrayList<>();
			for(int i=0; i<list.size(); i++) {
				Map<String,Object> m = new HashMap<>();
				m.put("problemid",list.get(i).getProblemid());
				m.put("problemname", list.get(i).getProblemname());
				m.put("limit", list.get(i).getDate());
				m.put("id",list.get(i).getId());
				m.put("mention",list.get(i).getMention());
				lm.add(m);
			}
			//			String[] slist = new String[list.size()];
			//			Map<String, String> m = new HashMap<>();
			return lm;
			//			for(int i=0; i<list.size(); i++) {
			//				GroupGoal gg = list.get(i);
			//				slist[i] = gg.getProblemid();
			//			}
			//			return postRepository.solved_list(slist, pageable);
		}
		return null;
	}
	@Override
	public List<Map<String,Object>> postGoal(String username, String groupname, PageRequest pageable) {
		Groupdetail gd = groupDetailRepository.findByAccount_usernameAndGroupdto_groupname(username,groupname);
		if(gd != null) {
			Date time = new Date();
			List<GroupGoal> list = groupGoalRepository.findByDateLessThanAndGroupdto_groupname(time,groupname);
			List<Map<String,Object>> lm = new ArrayList<>();
			for(int i=0; i<list.size(); i++) {
				Map<String,Object> m = new HashMap<>();
				m.put("problemid",list.get(i).getProblemid());
				m.put("problemname", list.get(i).getProblemname());
				m.put("limit", list.get(i).getDate());
				m.put("id",list.get(i).getId());
				m.put("mention",list.get(i).getMention());
				lm.add(m);
			}
			return lm;
			//			String[] slist = new String[list.size()];
			//			Map<String, String> m = new HashMap<>();
			//			for(int i=0; i<list.size(); i++) {
			//				GroupGoal gg = list.get(i);
			//				slist[i] = gg.getProblemid();
			//			}
			//			return postRepository.solved_list(slist, pageable);
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

	// 그룹 검색
	@Override
	public List<Map<String, String>> findGroup(String groupname, PageRequest pageable){
		List<Map<String, String>> lm = new ArrayList<>();
		List<GroupDTO> li = new ArrayList<>();
		if(groupname != null) {
			li = groupRepository.findByGroupnameLike("%"+groupname+"%", pageable);
		}else {
			li = groupRepository.findByGroupnameLike("%",pageable);
		}
		for(int i=0; i<li.size(); i++) {
			Map<String, String> m = new HashMap<>();
			m.put("groupname", li.get(i).getGroupname());
			m.put("groupdes", li.get(i).getGroupdesc());
			m.put("groupowner", li.get(i).getAccount().getNickname());
			lm.add(m);
		}
		return lm;
	}
	// 그룹 강퇴
	@Override
	public Boolean group_kick(String username, String groupname,String wantkick) {
		Groupdetail gd = groupDetailRepository.findByAccount_usernameAndGroupdto_groupname(username, groupname);
		if(gd!= null && gd.getRole().equals(GroupRole.owner)) {
			Groupdetail gd2 = groupDetailRepository.findByAccount_nicknameAndGroupdto_groupname(wantkick, groupname);
			if(gd2 != null && gd2.getRole().equals(GroupRole.member)) {
				groupDetailRepository.delete(gd2);
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	@Override
	public String Check_Role( String groupname,String nickname) {
		Groupdetail gd = groupDetailRepository.findByAccount_nicknameAndGroupdto_groupname(nickname, groupname);
		if(gd!= null ) {
			if(gd.getRole().equals(GroupRole.owner)) {
				return "owner";
			}else if(gd.getRole().equals(GroupRole.member)){
				return "member";
			}
			return "false";
		}else {
			return "false";
		}
	}
	
	@Override
	public String delete_goal(String username, long id) {
		Optional<GroupGoal> gg = groupGoalRepository.findById(id);
		if(gg.isPresent()) {
			GroupGoal gg2 = gg.get();
			if(gg2.getGroupdto().getAccount().getUsername().equals(username)) {
				gg2.setGroupdto(null);
				groupGoalRepository.delete(gg2);
				return "success";
			}else {
				return "fail";
			}
		}else {
			return "fail";
		}
	}

	@Override
	public String delete_group(String username, String groupname) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	@Override
//	public String delete_group(String username, String groupname) {
//		GroupDTO gd = groupRepository.findByAccount_usernameAndGroupname(username, groupname);
//		if(gd != null) {
//			groupDetailRepository.findBy
//			groupRepository.delete(gd);
//			groupDetailRepository
//			return "success";
//		}else {
//			return "fail";
//		}
//	}
}
