package org.slog.user.dto;

public record UserResponseDTO(
        Long id,
        String userid,
        String username
) {}