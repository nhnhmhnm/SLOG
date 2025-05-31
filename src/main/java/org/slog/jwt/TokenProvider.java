package org.slog.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slog.jwt.domain.JwtProperties;
import org.slog.user.domain.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), user);
    }

    // 1. JWT 토큰 생성 메서드
    private String makeToken(Date expiry, User user) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 타입  : JWT
                .setIssuer(jwtProperties.getIssuer()) // iss 발급자
                .setIssuedAt(now)  // iat  발급일시
                .setExpiration(expiry) // exp 만료일시 : expiry 값
                .setSubject(user.getUserid()) // sub 토큰 제목 : 유저 이메일
                .claim("id", user.getId()) // 클레임 id  : 유저 ID
                // 서명 : 비밀값과 함께 해시값 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // 2) JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                    .build()
                    .parseClaimsJws(token);


            return true;
        } catch (Exception e) { // 복화화 과정서 에러가 나면 유효하지 않은 토큰
            return false;
        }
    }

    // 3) 토큰 정보 기반으로 인증 정보를 가져오는 메서드
    // 프로퍼티 비밀값으로 복호화 후 getClaim()을 호출 해서 sub와 토큰 기반으로 인증 정보 생성
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
        org.springframework.security.core.userdetails.User user = new org.springframework.security.core.userdetails.User(claims.getSubject(),"", authorities);
        return new UsernamePasswordAuthenticationToken(user, token, authorities);
    }

    // 4) 토큰 기반으로 유저 ID를 가져오는 메서드
    // 클레임의 id로 저장된 값을 가져옴
    public Long getUserId(String token) {
        Claims claims = getClaims(token);
        return claims.get("id", Long.class);
    }

    // 5) 클레임 조회
    public Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}