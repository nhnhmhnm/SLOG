package org.slog.user.repository;

import org.slog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserid(String userid);
    Optional<User> findByUserid(String userid);
}
