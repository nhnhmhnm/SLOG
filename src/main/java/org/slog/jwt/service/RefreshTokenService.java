package org.slog.jwt.service;

import lombok.RequiredArgsConstructor;
import org.slog.jwt.domain.RefreshToken;
import org.slog.jwt.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void deleteRefreshToken(String userid) {
        refreshTokenRepository.deleteByUserid(userid);
    }
}
