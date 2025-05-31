package org.slog.article.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ArticleResponseDTO(
        Long articleId,
        Long userId,
        String userName,
        String content,
        LocalDateTime createdAt,
        int commentCount,
        int likeCount,
        List<String> likeUserIds
) {}
