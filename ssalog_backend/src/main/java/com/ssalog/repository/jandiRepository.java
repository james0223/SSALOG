package com.ssalog.repository;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.ssalog.dto.jandi;

public interface jandiRepository {
	public AggregationResults<jandi> getJandiCount(String username);
}
