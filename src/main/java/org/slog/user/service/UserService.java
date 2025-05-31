package org.slog.user.service;

import org.slog.jwt.TokenProvider;
import org.slog.jwt.service.RefreshTokenService;
import org.slog.user.domain.User;
import org.slog.user.dto.JwtResponseDTO;
import org.slog.user.dto.LoginRequestDTO;
import org.slog.user.dto.RegisterRequestDTO;
import org.slog.user.dto.UserResponseDTO;
import org.slog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenService refreshTokenService;

    // 회원가입
    public void register(RegisterRequestDTO request) {
        if (userRepository.existsByUserid(request.userid())) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        User user = User.builder()
                .userid(request.userid())
                .username(request.username())
                .userpw(passwordEncoder.encode(request.userpw()))
                .build();

        userRepository.save(user);
    }

    // 로그인
    public JwtResponseDTO login(LoginRequestDTO request) {
        User user = userRepository.findByUserid(request.userid())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(request.userpw(), user.getUserpw())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        // 토큰 생성
        String accessToken = tokenProvider.generateToken(user, Duration.ofHours(1));

        return new JwtResponseDTO("Bearer", accessToken);
    }

    // 로그아웃
    public void logout(User user) {
        refreshTokenService.deleteRefreshToken(user.getUserid());
    }


    // 사용자 정보 조회
    public UserResponseDTO getUserInfo(User user) {
        return new UserResponseDTO(user.getId(), user.getUserid(), user.getUsername());
    }

}
