package org.slog.jwt.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.slog.jwt.TokenProvider;
import org.slog.jwt.domain.RefreshToken;
import org.slog.jwt.repository.RefreshTokenRepository;
import org.slog.user.domain.User;
import org.slog.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;

    public String createAccessToken(String refreshToken) {
        String userid = getUserIdFromToken(refreshToken);

        RefreshToken token = refreshTokenRepository.findById(userid)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 refreshToken"));

        if (!token.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("토큰 정보가 일치하지 않습니다.");
        }

        User user = userRepository.findByUserid(userid)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return tokenProvider.generateToken(user, Duration.ofHours(1));
    }

    private String getUserIdFromToken(String token) {
        Claims claims = tokenProvider.getClaims(token);
        return claims.getSubject();
    }
}
