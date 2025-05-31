package org.slog.like.controller;

import lombok.RequiredArgsConstructor;
import org.slog.article.dto.ArticleResponseDTO;
import org.slog.like.dto.LikeRequestDTO;
import org.slog.like.service.LikeService;
import org.slog.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class LikeController {

    private final LikeService likeService;

    // 좋아요 누르기
    @PutMapping("/like")
    public ResponseEntity<Void> likeArticle(
            @AuthenticationPrincipal User user,
            @RequestBody LikeRequestDTO request
    ) {
        likeService.like(user, request.articleId());
        return ResponseEntity.ok().build();
    }

    // 좋아요 취소
    @PutMapping("/cancellike")
    public ResponseEntity<Void> cancelLike(
            @AuthenticationPrincipal User user,
            @RequestBody LikeRequestDTO request
    ) {
        likeService.cancelLike(user, request.articleId());
        return ResponseEntity.ok().build();
    }

    // 좋아요 여부 조회
    @GetMapping("/like/check")
    public ResponseEntity<Boolean> checkLike(
            @AuthenticationPrincipal User user,
            @RequestParam Long articleId
    ) {
        return ResponseEntity.ok(likeService.hasLiked(user, articleId));
    }


    // 좋아요 누른 게시글 목록
    @GetMapping("/likes/{page}")
    public ResponseEntity<List<ArticleResponseDTO>> getLikedArticles(
            @AuthenticationPrincipal User user,
            @PathVariable int page
    ) {
        return ResponseEntity.ok(likeService.getLikedArticles(user, page));
    }
}
