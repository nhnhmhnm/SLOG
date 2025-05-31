package org.slog.article.controller;

import lombok.RequiredArgsConstructor;
import org.slog.article.dto.ArticleRequestDTO;
import org.slog.article.dto.ArticleResponseDTO;
import org.slog.article.service.ArticleService;
import org.slog.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<ArticleResponseDTO> createArticle(
            @AuthenticationPrincipal User user,
            @RequestBody ArticleRequestDTO request
    ) {
        return ResponseEntity.ok(articleService.createArticle(user, request));
    }

    // 게시글 수정
    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> updateArticle(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody ArticleRequestDTO request
    ) {
        return ResponseEntity.ok(articleService.updateArticle(user, id, request));
    }

    // 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        articleService.deleteArticle(user, id);
        return ResponseEntity.noContent().build();
    }

    // 전체 게시글 목록 조회
    @GetMapping("/{page}")
    public ResponseEntity<List<ArticleResponseDTO>> getAllArticles(
            @PathVariable int page
    ) {
        return ResponseEntity.ok(articleService.getArticleList(page));
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDTO> getArticle(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(articleService.getArticle(id));
    }

    // 내 글 목록 조회
    @GetMapping("/my/{page}")
    public ResponseEntity<List<ArticleResponseDTO>> getMyArticles(
            @AuthenticationPrincipal User user,
            @PathVariable int page
    ) {
        return ResponseEntity.ok(articleService.getMyArticles(user, page));
    }
}
