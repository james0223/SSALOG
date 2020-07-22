package com.ssalog.dto;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.Data;

@Data
@RedisHash

// redis에 저장할 객체 Data, 덜 critical한 정보인 username + 토큰을 저장한다.
public class Token implements Serializable {
    private static final long serialVersionUID = -7353484588260422449L;
    private String username;
    private String refreshToken;
	
    public Token() {
		super();
	}

	public Token(String username, String refreshToken) {
		super();
		this.username = username;
		this.refreshToken = refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
