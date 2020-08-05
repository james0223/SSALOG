package com.ssalog.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.dto.jandi;

@Repository
public class jandiRepositoryImpl implements jandiRepository{
	
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
}
