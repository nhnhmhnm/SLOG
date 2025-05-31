package org.slog.comment.service;

import lombok.RequiredArgsConstructor;
import org.slog.article.domain.Article;
import org.slog.article.repository.ArticleRepository;
import org.slog.comment.domain.Comment;
import org.slog.comment.dto.CommentResponseDTO;
import org.slog.comment.repository.CommentRepository;
import org.slog.user.domain.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    // 댓글 작성
    @Override
    public CommentResponseDTO createComment(User user, Long articleId, String text) {
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .article(article)
                .user(user)
                .comment(text)
                .createdAt(LocalDateTime.now())
                .build();

        Comment saved = commentRepository.save(comment);
        return new CommentResponseDTO(saved.getId(), user.getUsername(), saved.getComment(), saved.getCreatedAt());
    }

    // 댓글 목록 조회
    @Override
    public List<CommentResponseDTO> getComments(Long articleId) {
        return commentRepository.findByArticleIdOrderByCreatedAtAsc(articleId).stream()
                .map(c -> new CommentResponseDTO(
                        c.getId(),
                        c.getUser().getUsername(),
                        c.getComment(),
                        c.getCreatedAt()
                ))
                .toList();
    }

    // 댓글 삭제
    @Override
    public void deleteComment(User user, Long commentId, Long articleId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글을 찾을 수 없습니다."));
        if (!comment.getUser().getId().equals(user.getId()) || !comment.getArticle().getId().equals(articleId)) {
            throw new RuntimeException("삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }
}

