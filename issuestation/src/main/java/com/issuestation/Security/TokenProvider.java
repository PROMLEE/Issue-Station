package com.issuestation.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class TokenProvider {
    private static final String SECURITY_KEY = "issuesecretkey!@";

    //토큰 만료시간을 현재시간의 1시간 후로 설정한다.
    //JWT 생성하는 메서드
    public String createJwt(Long userId) {
        Date exprTime = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        return Jwts.builder() // Builder를 이용해서 생성
                .signWith(SignatureAlgorithm.HS512, SECURITY_KEY) // 암호화 알고리즘, 시크릿 키
                .setSubject(String.valueOf(userId)) //JWT의 제목
                .setIssuedAt(new Date()) //생성날짜
                .setExpiration(exprTime) //만료날짜
                .compact(); //생성
    }

    //JWT 검증 메서드
    public String validateJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECURITY_KEY) // token을 SECURITY_KEY를 이용해 파싱
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject(); // token을 파싱해서 토큰 생성할 때 넣은 id를 가져옴
    }
}
