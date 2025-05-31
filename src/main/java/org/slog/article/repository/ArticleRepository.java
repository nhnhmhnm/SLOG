package org.slog.article.repository;

import org.slog.article.domain.Article;
import org.slog.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findByUser(User user, Pageable pageable);
}
