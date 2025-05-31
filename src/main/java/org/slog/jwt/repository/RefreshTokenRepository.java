package org.slog.jwt.repository;


import org.slog.jwt.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    void deleteByUserid(String userid);
}
