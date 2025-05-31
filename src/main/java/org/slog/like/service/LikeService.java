package org.slog.like.service;

import org.slog.article.dto.ArticleResponseDTO;
import org.slog.user.domain.User;

import java.util.List;

public interface LikeService {
    void like(User user, Long articleId);
    void cancelLike(User user, Long articleId);
    boolean hasLiked(User user, Long articleId);

    List<ArticleResponseDTO> getLikedArticles(User user, int page);
}

