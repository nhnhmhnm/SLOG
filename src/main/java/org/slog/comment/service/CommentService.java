package org.slog.comment.service;

import org.slog.comment.dto.CommentResponseDTO;
import org.slog.user.domain.User;

import java.util.List;

public interface CommentService {
    CommentResponseDTO createComment(User user, Long articleId, String text);
    List<CommentResponseDTO> getComments(Long articleId);
    void deleteComment(User user, Long commentId, Long articleId);
}
