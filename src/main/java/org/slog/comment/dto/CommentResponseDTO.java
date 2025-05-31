package org.slog.comment.dto;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        Long id,
        String userName,
        String comment,
        LocalDateTime createdAt
) {}