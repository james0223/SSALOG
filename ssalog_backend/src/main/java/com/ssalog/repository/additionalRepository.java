package com.ssalog.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.ssalog.dto.Comment;
import com.ssalog.dto.Problem;
import com.ssalog.dto.jandi;

public interface additionalRepository {
	public AggregationResults<jandi> getJandiCount(String username);
	public void delete_comment(String id);
	public void update_comment(Comment comment, String id);
	public void write_subcomment(String id, Comment comment);
	public void update_subcomment(String id, Comment comment);
	public Page<Problem> keyword_search(String[] list, Pageable pageable);
	
}
