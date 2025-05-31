package org.slog.user.dto;

public record RegisterRequestDTO(
        String userid,
        String userpw,
        String username
) {}
