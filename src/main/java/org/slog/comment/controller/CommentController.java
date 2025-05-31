package org.slog.comment.controller;

import lombok.RequiredArgsConstructor;
import org.slog.comment.dto.CommentRequestDTO;
import org.slog.comment.dto.CommentResponseDTO;
import org.slog.comment.service.CommentService;
import org.slog.user.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponseDTO> addComment(
            @AuthenticationPrincipal User user,
            @RequestBody CommentRequestDTO request
    ) {
        return ResponseEntity.ok(
                commentService.createComment(user, request.articleId(), request.comment())
        );
    }

    // 댓글 목록 조회
    @GetMapping("/{articleId}")
    public ResponseEntity<List<CommentResponseDTO>> getComments(
            @AuthenticationPrincipal User user,
            @PathVariable Long articleId
    ) {
        return ResponseEntity.ok(commentService.getComments(articleId));
    }

    // 댓글 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal User user,
            @RequestParam Long id,
            @RequestParam Long articleId
    ) {
        commentService.deleteComment(user, id, articleId);
        return ResponseEntity.noContent().build();
    }
}
