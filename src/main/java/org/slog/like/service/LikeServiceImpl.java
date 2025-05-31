package org.slog.like.service;

import lombok.RequiredArgsConstructor;
import org.slog.article.domain.Article;
import org.slog.article.dto.ArticleResponseDTO;
import org.slog.article.repository.ArticleRepository;
import org.slog.like.domain.Like;
import org.slog.like.repository.LikeRepository;
import org.slog.user.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final ArticleRepository articleRepository;
    private final LikeRepository likeRepository;

    // 좋아요 누르기
    @Override
    public void like(User user, Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 이미 좋아요한 경우 무시
        if (!likeRepository.existsByUserAndArticle(user, article)) {
            Like like = Like.builder()
                    .user(user)
                    .article(article)
                    .build();
            likeRepository.save(like);
        }
    }

    // 좋아요 취소
    @Override
    public void cancelLike(User user, Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        likeRepository.deleteByUserAndArticle(user, article);
    }

    // 좋아요 여부 조회
    @Override
    public boolean hasLiked(User user, Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return likeRepository.existsByUserAndArticle(user, article);
    }

    // 좋아요 누른 게시글 목록
    @Override
    public List<ArticleResponseDTO> getLikedArticles(User user, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        List<Like> likes = likeRepository.findAllByUser(user);

        return likes.stream()
                .map(Like::getArticle)
                .sorted((a1, a2) -> a2.getCreatedAt().compareTo(a1.getCreatedAt())) // 최신순 정렬
                .map(article -> new ArticleResponseDTO(
                        article.getId(),
                        article.getUser().getId(),
                        article.getUser().getUsername(),
                        article.getContent(),
                        article.getCreatedAt(),
                        article.getComments().size(),
                        article.getLikes().size(),
                        article.getLikes().stream().map(like -> like.getUser().getUserid()).toList()
                ))
                .toList();
    }
}
