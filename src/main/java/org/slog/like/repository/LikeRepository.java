package org.slog.like.repository;

import org.slog.article.domain.Article;
import org.slog.like.domain.Like;
import org.slog.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndArticle(User user, Article article);
    List<Like> findAllByUser(User user);
    boolean existsByUserAndArticle(User user, Article article);
    void deleteByUserAndArticle(User user, Article article);
}
