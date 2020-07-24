package com.ssalog.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.dto.Account;
import com.ssalog.dto.Token;
import com.ssalog.jwt.JwtTokenUtil;
import com.ssalog.repository.AccountRepository;
import com.ssalog.service.JwtUserDetailsService;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;

//http://i3b101.p.ssafy.io:8080/swagger-ui.html
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping
public class LoginContorller {
    private Logger logger = LoggerFactory.getLogger(ApplicationRunner.class);
	
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private AuthenticationManager am;
    @Autowired
    private PasswordEncoder bcryptEncoder;
    
    // 1-1. 회원가입 form [중복체크] 버튼 클릭 -> 이메일 중복체크 (DB에 이메일 존재하는지 체킹)
    @ApiOperation(value = "[회원가입 기능] 중복되는 이메일이 DB에 없는지 확인(check)한다.")
    @PostMapping(path="/newuser/checkemail")
    public boolean checkEmail (@RequestBody Map<String, String> m) {
        System.out.println("이메일 중복체크 요청 이메일: " + m.get("email"));
        if (accountRepository.findByEmail(m.get("email")) == null) return true;
        else return false;
    }
    
    // 1-2. 신규회원인지 체크
    @ApiOperation(value = "[회원가입 기능] 신규회원인지 확인(check)한다.")
    @PostMapping(path="/newuser/check")
    public Map<String, Object> checker(@RequestBody Map<String, String> m) {
        String username = null;
        Map<String, Object> map = new HashMap<>();
        
        try {
            username = jwtTokenUtil.getUsernameFromToken(m.get("accessToken"));
        } catch (IllegalArgumentException e) {
            logger.warn("[ERROR] JWT Token을 얻어올 수 없습니다.");
        }
        catch (ExpiredJwtException e) {
        }

        if (username != null) { // DB에 존재하는 username이 없는 경우
            map.put("success", true);
            map.put("username", username);
        } else {
            map.put("success", false);
        }
        return map;
    }
    
    // 1-3. 회원가입 진행
    @ApiOperation(value = "[회원가입 기능] 신규회원을 등록한다.")
    @PostMapping(path="/newuser/add") // Map ONLY POST Requests
    public Map<String, Object> addNewUser (@RequestBody Account account) {
        String un = account.getUsername();
        Map<String, Object> map = new HashMap<>();
        
        
        // 아이디: account.getUsername()
        // 닉네임: account.getNickname()
        System.out.println("[회원가입] 요청 아이디: "+un + " 비밀번호: " + account.getPassword());
        
        // 아이디 중복체크 진행
        if (accountRepository.findByUsername(un) == null) {
            account.setUsername(un);
            account.setEmail(account.getEmail());
            
            // username이 admin 이라면 admin role 주기 (우리가 바꿔도 될듯) 쌀로그로 할까?
            if (un.equals("admin")) {
                account.setRole("ROLE_ADMIN");
            } else {
                account.setRole("ROLE_USER");
            }
            account.setNickname(account.getNickname());
            account.setPassword(bcryptEncoder.encode(account.getPassword()));
            account.setQuestion(account.getQuestion());
            account.setAnswer(account.getAnswer());
            
            map.put("success", true); // 등록성공
            accountRepository.save(account); // db에 저장
            return map;
        } else {
            map.put("success", false);
            map.put("message", "duplicated username"); // 에러 메시지 리턴
        }
        return map;
    }
    
    // 1-4. 회원가입 form [중복체크] 버튼 클릭 -> 아이디 중복체크 (DB에 아이디 존재하는지 체킹)
    @ApiOperation(value = "[회원가입 기능] 중복되는 아이디가 DB에 없는지 확인(check)한다.")
    @PostMapping(path="/newuser/checkid")
    public boolean checkId (@RequestBody Map<String, String> m) {
    	System.out.println("진입");
        System.out.println("이메일 중복체크 요청 아이디: " + m.get("username"));
        if (accountRepository.findByUsername(m.get("username")) == null) return true;
        else return false;
    }
    
    // 2. 로그인 요청
    @ApiOperation(value = "[로그인 기능] 회원로그인을 진행한다.")
    @PostMapping(path = "/newuser/login")
    public Map<String, Object> login(@RequestBody Map<String, String> m) throws Exception {
        final String username = m.get("username");
        final String password = m.get("password");
        
        // 디버깅용
        Iterator<String> mapIter = m.keySet().iterator();
        while(mapIter.hasNext()){
            String key = mapIter.next();
            String value = m.get( key );
            System.out.println(key+" : "+value);
        }
        
        try {
        	// 현재 사용자가 입력한 username과 password가 내 DB의 정보와 일치하는가?
            am.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e){
        	System.out.println("비정상적인 요청입니다.");
            throw e;
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
        final String refreshToken = jwtTokenUtil.generateRefreshToken(username);

        Token retok = new Token();
        retok.setUsername(username);
        retok.setRefreshToken(refreshToken);

        //발행한 redis에 저장하는 로직으로, hashmap과 같은 key,value 구조임
        ValueOperations<String, Object> vop = redisTemplate.opsForValue();
        vop.set(username, retok); // key, value 값으로 redis에 저장

        logger.info("generated access token: " + accessToken);
        logger.info("generated refresh token: " + refreshToken);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        //map.put("refreshToken", refreshToken);
        return map;
    }
    
    @ApiOperation(value = "[Token refresh 기능] 신규 Token 요청을 진행한다.")
    @PostMapping(path="/newuser/refresh")
    public Map<String, Object>  requestForNewAccessToken(@RequestBody Map<String, String> m) {
        String accessToken = null;
        String refreshToken = null;
        String refreshTokenFromDb = null;
        String username = null;
        Map<String, Object> map = new HashMap<>();
        try {
            accessToken = m.get("accessToken");
            refreshToken = m.get("refreshToken");
            logger.info("access token in rnat: " + accessToken);
            try {
                username = jwtTokenUtil.getUsernameFromToken(accessToken);
            } catch (IllegalArgumentException e) {

            } catch (ExpiredJwtException e) { //expire됐을 때
                username = e.getClaims().getSubject();
                logger.info("username from expired access token: " + username);
            }

            if (refreshToken != null) { //refresh를 같이 보냈으면.
                try {
                    ValueOperations<String, Object> vop = redisTemplate.opsForValue();
                    Token result = (Token) vop.get(username);
                    refreshTokenFromDb = result.getRefreshToken();
                    logger.info("rtfrom db: " + refreshTokenFromDb);
                } catch (IllegalArgumentException e) {
                    logger.warn("illegal argument!!");
                }
                //둘이 일치하고 만료도 안됐으면 재발급 해주기.
                if (refreshToken.equals(refreshTokenFromDb) && !jwtTokenUtil.isTokenExpired(refreshToken)) {
                    final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    String newtok =  jwtTokenUtil.generateAccessToken(userDetails);
                    map.put("success", true);
                    map.put("accessToken", newtok);
                } else {
                    map.put("success", false);
                    map.put("msg", "refresh token is expired.");
                }
            } else { //refresh token이 없으면
                map.put("success", false);
                map.put("msg", "your refresh token does not exist.");
            }

        } catch (Exception e) {
            throw e;
        }
        logger.info("m: " + m);

        return map;
    }

    // 1. 회원탈퇴 기능
    @ApiOperation(value = "[admin 기능] 관리자가 사용자를 삭제한다.")
    @Transactional // repository를 통한 삭제 요청 시, No EntityManager with actual transaction available for current thread... Error 해결
    @PostMapping(path="/admin/deleteuser")
    public void deleteUser (@RequestBody Map<String, String> m) {
        logger.info("[회원 탈퇴를 요청한 username] " + m.get("username"));
        Long result = accountRepository.deleteByUsername(m.get("username"));
        logger.info("[회원 탈퇴 결과] " + result);
    }
    
    
    // 2. ROLE_ADMIN만 사용 가능한 기능, 모든 user 정보 반환
    @ApiOperation(value = "[admin 기능] 등록된 모든 user를 확인한다.")
    @GetMapping(path="/admin/getusers")
    public Iterable<Account> getAllUsers() {
        return accountRepository.findAll();
    }

    
    // 3. login한 user라면 권한 상관없이 누구나 접근 가능한 페이지
    @ApiOperation(value = "[normal 기능] login한 user라면 누구나 접근 가능한 페이지.")
    @GetMapping(path="/user/normal")
    public ResponseEntity<?> onlyNormal() {
        return new ResponseEntity(HttpStatus.OK);
    }

    
    // 4. 로그아웃 기능
    @ApiOperation(value = "[logout 기능] 로그아웃을 진행한다.")
    @PostMapping(path="/newuser/out")
    public ResponseEntity<?> logout(@RequestBody Map<String, String> m) {
        String username = null;
        String accessToken = m.get("accessToken");
        try {
            username = jwtTokenUtil.getUsernameFromToken(accessToken);
        } catch (IllegalArgumentException e) {
        	
        } catch (ExpiredJwtException e) { //access token이 expire됐을 때
            username = e.getClaims().getSubject();
            logger.info("[access token이 만료된 사용자 이름] " + username);
        }

        try {
            if (redisTemplate.opsForValue().get(username) != null) {
                redisTemplate.delete(username); // refresh token 삭제
            }
        } catch (IllegalArgumentException e) {
            logger.warn("[ERROR] 사용자가 존재하지 않습니다!");
        }

        //cache logout token for 10 minutes! (캐시에 상주시켜둘 시간은 우리가 정하기)
        logger.info(" logout ing : " + accessToken);
        redisTemplate.opsForValue().set(accessToken, true);
        redisTemplate.expire(accessToken, 10*6*1000, TimeUnit.MILLISECONDS);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    @ApiOperation(value = "[비밀번호 찾기기능] 사용자 id와 email을 이용하여 결과와 질문을 retrun한다")
    @PostMapping(path="/newuser/findpw")
    public Map<String, Object> findpw(@RequestBody Account account) {
       Map<String, Object> map = new HashMap<>();
       Account target = accountRepository.findByUsernameAndEmail(account.getUsername(), account.getEmail());
       if(target == null) {
    	   map.put("result", false);
       }else {
    	   map.put("result", true);
    	   map.put("username",target.getUsername());
    	   map.put("question",target.getQuestion());
       }
       return map;
    }
    
    @ApiOperation(value = "[비밀번호 퀴즈풀이] 사용자 id와 answer을 받아내서 인증을 하고 실 사용자인지 true/false 값을 retrun 한다")
    @PostMapping(path="/newuser/quiz")
    public Map<String, Object> quiz(@RequestBody Account account) {
    	String username = account.getUsername();
       Map<String, Object> map = new HashMap<>();
       Account target = accountRepository.findByUsernameAndAnswer(account.getUsername(), account.getAnswer());
       if(target == null) {
    	   map.put("result", "false");
       }else {
    	   map.put("result", "true");
           final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
           final String accessToken = jwtTokenUtil.generateAccessToken(userDetails);
           final String refreshToken = jwtTokenUtil.generateRefreshToken(username);

           Token retok = new Token();
           retok.setUsername(username);
           retok.setRefreshToken(refreshToken);

           //발행한 redis에 저장하는 로직으로, hashmap과 같은 key,value 구조임
           ValueOperations<String, Object> vop = redisTemplate.opsForValue();
           vop.set(username, retok); // key, value 값으로 redis에 저장

           logger.info("generated access token: " + accessToken);
           logger.info("generated refresh token: " + refreshToken);
          
           map.put("accessToken", accessToken);
   
       }
       return map;
    }
    
    
    @ApiOperation(value = "[비밀번호 찾기 - 변경] 비밀번호 찾기기능을 이용해 해당 계정이 존재하면, 페이지 이동 후 비밀번호를 변경한다.")
    @PostMapping(path="/newuser/change_pw")
    public boolean find_changepw(@RequestBody Map<String, String> m) {
    	String username = null;
        try {
            username = jwtTokenUtil.getUsernameFromToken(m.get("accessToken"));
        } catch (Exception e) {
            logger.warn("[ERROR] JWT Token을 얻어올 수 없습니다.");
            return false;
        }
        System.out.println("username="+username);
       Account ac = accountRepository.findByUsername(username);
       //Account change_ac = accountRepository.getOne((int)(long)ac.getId());
       ac.setPassword(bcryptEncoder.encode(m.get("password")));
       accountRepository.save(ac);
       //System.out.println("변경성공");
       return true;
    }
    
    
    
}