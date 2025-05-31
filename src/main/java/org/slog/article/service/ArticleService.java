package org.slog.article.service;

import org.slog.article.dto.ArticleRequestDTO;
import org.slog.article.dto.ArticleResponseDTO;
import org.slog.user.domain.User;
import java.util.List;

public interface ArticleService {
    ArticleResponseDTO createArticle(User user, ArticleRequestDTO request);
    ArticleResponseDTO updateArticle(User user, Long articleId, ArticleRequestDTO request);
    void deleteArticle(User user, Long articleId);

    List<ArticleResponseDTO> getArticleList(int page);
    ArticleResponseDTO getArticle(Long id);
    public List<ArticleResponseDTO> getMyArticles(User user, int page);

}
