package com.ssalog.repository;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Post;
import com.ssalog.dto.jandi;

public interface additionalRepository {
	public AggregationResults<jandi> getJandiCount(String username);
	public void delete_comment(String id);
	public void update_comment(Comment comment, String id);
	public void write_subcomment(String id, Comment comment);
	public void update_subcomment(String id, Comment comment);
}
