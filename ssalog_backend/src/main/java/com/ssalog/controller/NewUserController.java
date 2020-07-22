package com.ssalog.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.jwt.JwtTokenUtil;
import com.ssalog.repository.AccountRepository;
import com.ssalog.service.JwtUserDetailsService;

@RestController
@CrossOrigin
@RequestMapping
public class NewUserController {
    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager am;


}
