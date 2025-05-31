package org.slog.user.dto;

public record JwtResponseDTO(
        String grantType,
        String accessToken
) {}
