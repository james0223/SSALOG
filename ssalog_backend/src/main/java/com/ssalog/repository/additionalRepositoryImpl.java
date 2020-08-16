package com.ssalog.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.dto.PostSub;
import com.ssalog.dto.Problem;
import com.ssalog.dto.jandi;

@Repository
public class additionalRepositoryImpl implements additionalRepository{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public AggregationResults<jandi> getJandiCount(String username){		
		List<AggregationOperation> list = new ArrayList<AggregationOperation>();
		list.add(Aggregation.match(Criteria.where("username").is(username)));
		list.add(Aggregation.group("regdate").count().as("count"));
		Aggregation aggregation = Aggregation.newAggregation(list);
		return mongoTemplate.aggregate(aggregation,"post", jandi.class);
	}
	
	public void delete_comment(String id) {
		Query q = new Query();
		q.addCriteria(Criteria.where("comment")
						.elemMatch(Criteria.where("_id").is(id)));
		Update update = new Update();
		update.pull("comment", new BasicDBObject("_id", id));
		mongoTemplate.updateFirst(q, update, "post");
	}
	
	public void update_comment(Comment comment, String id) {
		Query q = new Query();
		q.addCriteria(Criteria.where("comment")
						.elemMatch(Criteria.where("_id").is(id)));
		Update update = new Update();
		comment.setUniqueid(id);
		update.set("comment.$",comment);
		mongoTemplate.updateFirst(q, update, "post");
	}
	public void write_subcomment(String id, Comment comment) {
		Query q = new Query();
		q.addCriteria(Criteria.where("comment")
						.elemMatch(Criteria.where("_id").is(id)));
		Update update = new Update();
		update.addToSet("comment.$.subcomment", comment);
		mongoTemplate.updateFirst(q, update, Post.class);
	}
	public void update_subcomment(String id, Comment comment) {
		Query q = new Query();
		q.addCriteria(Criteria.where("comment.$.subcomment")
						.elemMatch(Criteria.where("_id").is(id)));
		Update update = new Update();
		update.set("comment.subcomment.$", comment);
		mongoTemplate.updateFirst(q, update, Post.class);
	}
//	public void find_comment(String id, Comment comment) {
//		Query q = new Query();
//		q.addCriteria(Criteria.where("comment")
//						.elemMatch(Criteria.where("_id").is(id)));
//		Update update = new Update();
//		update.push("comment", comment);
//		mongoTemplate.updateFirst(q, update, Post.class);
//	}
	
	public Page<Problem> keyword_search(String[] list, Pageable pageable) {
		Query query = new Query().with(pageable);
		Criteria criteria = new Criteria();
		Criteria[] criteria_arr = new Criteria[list.length];
		
		for(int i=0; i<list.length; i++) {
			criteria_arr[i] = Criteria.where("key."+list[i]).gt(0);
		}
		query.addCriteria(criteria.andOperator(criteria_arr));
		List<Problem> plist = mongoTemplate.find(query, Problem.class);
		//plist.forEach(System.out::println);
		return PageableExecutionUtils.getPage(plist, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Problem.class));
	}
	
	public Page<Problem> solved_list(String[] list, Pageable pageable) {
		Query query = new Query().with(pageable);
		Criteria criteria = new Criteria();
		Criteria[] criteria_arr = new Criteria[list.length];
		
		for(int i=0; i<list.length; i++) {
			criteria_arr[i] = Criteria.where("_id").is(list[i]);
		}
		query.addCriteria(criteria.orOperator(criteria_arr));
		List<Problem> plist = mongoTemplate.find(query, Problem.class);
		plist.forEach(System.out::println);
		return PageableExecutionUtils.getPage(plist, pageable, () -> mongoTemplate.count(Query.of(query).limit(-1).skip(-1), Problem.class));
	}
	
	public AggregationResults<PostSub> latestpost(int cnt){
		List<AggregationOperation> list = new ArrayList<AggregationOperation>();
		list.add(Aggregation.sort(Sort.Direction.DESC,"regdate").and(Sort.Direction.DESC,"regtime"));
		list.add(Aggregation.limit(cnt));
		Aggregation aggregation = Aggregation.newAggregation(list);
		return mongoTemplate.aggregate(aggregation,"post", PostSub.class);
	}
	public void update_nickname(String nickname, String username) {
		Query q = new Query();
		q.addCriteria(Criteria.where("username").is(username));
		Update update = new Update();
		update.set("nickname", nickname);
		mongoTemplate.updateMulti(q, update, "post");
	}
}
