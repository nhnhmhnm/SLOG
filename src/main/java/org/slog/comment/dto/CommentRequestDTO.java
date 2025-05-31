package org.slog.comment.dto;

public record CommentRequestDTO(
        Long articleId,
        String comment
) {}
