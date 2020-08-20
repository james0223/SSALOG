package com.ssalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ssalog.dto.TempPost;

public interface TempPostRepository extends MongoRepository<TempPost, String>{

}
