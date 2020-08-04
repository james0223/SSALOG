package com.ssalog.repository;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import com.ssalog.dto.jandi;

@Repository
public class jandiRepositoryImpl implements jandiRepository{
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	@Override
	public AggregationResults<jandi> getJandiCount(String username){		
//		AggregationOperation group = Aggregation.group("regdate").count().as("cnt");
//		Aggregation aggregation = Aggregation.newAggregation(group);
//		return mongoTemplate.aggregate(Criteria.where("username").is("jso")),aggregation, "post",Object.class);
//	
		List<AggregationOperation> list = new ArrayList<AggregationOperation>();
		list.add(Aggregation.match(Criteria.where("username").is(username)));
		list.add(Aggregation.group("regdate").count().as("count"));
		Aggregation aggregation = Aggregation.newAggregation(list);
		return mongoTemplate.aggregate(aggregation,"post", jandi.class);
	}
}
