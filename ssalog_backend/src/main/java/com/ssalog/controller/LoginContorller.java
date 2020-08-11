package com.ssalog.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssalog.dto.Account;
import com.ssalog.dto.Token;
import com.ssalog.jwt.JwtTokenUtil;
import com.ssalog.repository.AccountRepository;
import com.ssalog.service.JwtUserDetailsService;
import com.ssalog.util.Mail;

import io.jsonwebtoken.ExpiredJwtException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ResponseHeader;

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
    
    
    // 1-3. 회원가입 진행
    @ApiOperation(value = "[회원가입 기능](p-011_회원가입) 신규회원을 등록한다. [require] username, password, email, nickname, question,answer")
    @PostMapping(path="/newuser/add") // Map ONLY POST Requests
    public ResponseEntity<Map<String, Object>> addNewUser (@RequestBody Account account) {
        String un = account.getUsername();
        Map<String, Object> map = new HashMap<>();
        System.out.println("[회원가입] 요청 아이디: "+un + " 비밀번호: " + account.getPassword());
        
        // 아이디 중복체크 진행
        if (accountRepository.findByUsername(un) == null) {
            account.setUsername(un);
            
            // username이 admin 이라면 admin role 주기 (우리가 바꿔도 될듯) 쌀로그로 할까?
            if (un.equals("admin")) {
                account.setRole("ROLE_ADMIN");
            } else {
                account.setRole("ROLE_USER");
            }
            account.setNickname(account.getNickname());
            account.setPassword(bcryptEncoder.encode(account.getPassword()));
            
            map.put("success", true); // 등록성공
            accountRepository.save(account); // db에 저장
        } else {
            map.put("success", false);
            map.put("message", "duplicated username"); // 에러 메시지 리턴
        }
        return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
    }
    
    // 1-4. 회원가입 form [중복체크] 버튼 클릭 -> 아이디 중복체크 (DB에 아이디 존재하는지 체킹)
    @ApiOperation(value = "[회원가입 기능](p-011_회원가입) 중복체크 이걸로 하면 될듯? 중복되는 아이디가 DB에 없는지 확인(check)한다.")
    @GetMapping(path="/newuser/checkid")
    public ResponseEntity<Boolean> checkId (@RequestParam("username") String username) {
        if (accountRepository.findByUsername(username) == null) return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        else return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }
    
    // 1-5. 회원가입 form [중복체크] 버튼 클릭 -> 닉네임 중복체크 (DB에 같은 닉네임 존재하는지 체킹)
    @ApiOperation(value = "[회원가입 기능](p-011_회원가입) 중복되는 닉네임이 DB에 없는지 확인(check)한다. 사용 가능하면 true,불가능하면 false")
    @GetMapping(path="/newuser/checkNickname")
    public ResponseEntity<Boolean> checkNickname (@RequestParam("nickname") String nick) {
    	if (accountRepository.findByNickname(nick) == null) return new ResponseEntity<Boolean>(true, HttpStatus.OK);
        return new ResponseEntity<Boolean>(false, HttpStatus.OK);
    }
    
    // 2. 로그인 요청
    @ApiOperation(value = "[로그인 기능](p-012_로그인) 회원로그인을 진행한다.")
    @PostMapping(path = "/newuser/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam("username") String username, @RequestParam("password") String password) throws Exception {
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
        String nickname = accountRepository.findByUsername(username).getNickname();
        logger.info("generated access token: " + accessToken);
        logger.info("generated refresh token: " + refreshToken);
        Map<String, Object> map = new HashMap<>();
        map.put("accessToken", accessToken);
        map.put("refreshToken", refreshToken);
        map.put("nickname", nickname);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }
   
    // 4. 로그아웃 기능
    @ApiOperation(value = "[logout 기능](p-012_로그인) 로그아웃을 진행한다.")
    @PostMapping(path="/user/out")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        String username = response.getHeader("username");
       // String accessToken = response.getHeader("jwtToken");
        try {
            if (redisTemplate.opsForValue().get(username) != null) {
                redisTemplate.delete(username); // refresh token 삭제
            }
        } catch (IllegalArgumentException e) {
            logger.warn("[ERROR] 사용자가 존재하지 않습니다!");
        }

//        //cache logout token for 10 minutes! (캐시에 상주시켜둘 시간은` 우리가 정하기)
//        logger.info(" logout ing : " + accessToken);
//        redisTemplate.opsForValue().set(accessToken, true);
//        redisTemplate.expire(accessToken, 10*6*1000, TimeUnit.MILLISECONDS);

        return new ResponseEntity(HttpStatus.OK);
    }
    
    
    
    
    @ApiOperation(value = "[비밀번호 찾기 - 변경](p-013_비밀번호찾기) 비밀번호 찾기기능을 이용해 토큰을 전달해, 해당 계정이 존재하면, 페이지 이동 후 비밀번호를 변경한다.")
    @PutMapping(path="/user/change_pw")
    public ResponseEntity<Boolean> find_changepw(HttpServletResponse response, @RequestParam("password") String password) {
    	String username = response.getHeader("username");
       Account ac = accountRepository.findByUsername(username);
       ac.setPassword(bcryptEncoder.encode(password));
       accountRepository.save(ac);
       return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    @ApiOperation(value = "[Token refresh 기능] 클라이언트가 받은 refresh token을 이용해, db에 존재하는 값과 일치하면, 신규 Token 갱신과정을 진행한다.")
    @PostMapping(path="/user/refresh")
    public ResponseEntity<Map<String, Object>> requestForNewAccessToken(HttpServletResponse response) {
        String refreshTokenFromDb = null;
        Map<String, Object> map = new HashMap<>();
        String refreshToken = response.getHeader("jwtToken2");
        try {

            if (refreshToken != null) { //refresh를 같이 보냈으면.
            	String username = jwtTokenUtil.getUsernameFromToken(refreshToken);
                try {
                	System.out.println("username입니다 !" + username);
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
        return new ResponseEntity<Map<String, Object>>(map,HttpStatus.OK);
    }
    @ApiOperation(value = "[메일보내기] 이메일 인증을 만들어서,해당 이메일에 random값으로 메일을 보내고 그 random값을 return한다. 클라이언트단에서 비교해서 같으면 true, 틀리면 false 처리해주면 될듯.")
    @GetMapping(path="/newuser/check_email")
    public ResponseEntity<String> sendmail(@RequestParam("reciver") String email) {
    	Mail m = new Mail();
    	String ran = m.sendMail(email);
    	//System.out.println("호출완료!");
    	return new ResponseEntity<String>(ran,HttpStatus.OK);
    } 
}
