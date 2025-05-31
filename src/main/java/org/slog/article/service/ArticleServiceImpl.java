package org.slog.article.service;

import lombok.RequiredArgsConstructor;
import org.slog.article.domain.Article;
import org.slog.article.dto.ArticleRequestDTO;
import org.slog.article.dto.ArticleResponseDTO;
import org.slog.article.repository.ArticleRepository;
import org.slog.user.domain.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    // 게시글 작성
    @Override
    public ArticleResponseDTO createArticle(User user, ArticleRequestDTO request) {
        Article article = Article.builder()
                .user(user)
                .content(request.content())
                .createdAt(LocalDateTime.now())
                .likes(new ArrayList<>()) // 초기화 명시
                .build();

        Article saved = articleRepository.save(article);
        return toDto(saved);
    }

    // 게시글 수정
    @Override
    public ArticleResponseDTO updateArticle(User user, Long articleId, ArticleRequestDTO request) {
        Article article = getOwnedArticle(user, articleId);
        article.setContent(request.content());
        return toDto(articleRepository.save(article));
    }

    // 게시글 삭제
    @Override
    public void deleteArticle(User user, Long articleId) {
        Article article = getOwnedArticle(user, articleId);
        articleRepository.delete(article);
    }

    // 전체 게시글 목록 조회
    @Override
    public List<ArticleResponseDTO> getArticleList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        return articleRepository.findAll(pageable)
                .stream()
                .map(this::toDto)
                .toList();
    }

    // 게시글 상세 조회
    @Override
    public ArticleResponseDTO getArticle(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return toDto(article);
    }

    // 내 글 목록 조회
    @Override
    public List<ArticleResponseDTO> getMyArticles(User user, int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createdAt").descending());
        return articleRepository.findByUser(user, pageable)
                .stream()
                .map(this::toDto)
                .toList();
    }


    private Article getOwnedArticle(User user, Long articleId) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        if (!article.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }
        return article;
    }

    private ArticleResponseDTO toDto(Article article) {
        return new ArticleResponseDTO(
                article.getId(),
                article.getUser().getId(),
                article.getUser().getUsername(),
                article.getContent(),
                article.getCreatedAt(),
                article.getComments().size(),
                article.getLikes().size(),
                article.getLikes().stream()
                        .map(like -> like.getUser().getUserid())
                        .toList()
        );
    }
}

