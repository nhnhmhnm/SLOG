package org.slog.jwt.controller;


import lombok.RequiredArgsConstructor;
import org.slog.jwt.dto.CreateAccessTokenRequestDTO;
import org.slog.jwt.dto.CreateAccessTokenResponseDTO;
import org.slog.jwt.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("/token")
    public ResponseEntity<CreateAccessTokenResponseDTO> createAccessToken(
            @RequestBody CreateAccessTokenRequestDTO request
    ) {
        String accessToken = tokenService.createAccessToken(request.refreshToken());
        return ResponseEntity.ok(new CreateAccessTokenResponseDTO(accessToken));
    }
}
