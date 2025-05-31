package org.slog.user.controller;

import lombok.RequiredArgsConstructor;
import org.slog.user.domain.User;
import org.slog.user.dto.JwtResponseDTO;
import org.slog.user.dto.LoginRequestDTO;
import org.slog.user.dto.RegisterRequestDTO;
import org.slog.user.dto.UserResponseDTO;
import org.slog.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        userService.register(request);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(@RequestBody LoginRequestDTO request) {
        JwtResponseDTO token = userService.login(request);
        return ResponseEntity.ok(token);
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@AuthenticationPrincipal User user) {
        userService.logout(user);
        return ResponseEntity.ok("로그아웃되었습니다.");
    }

    // 사용자 정보 조회
    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getUserInfo(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(userService.getUserInfo(user));
    }
}

