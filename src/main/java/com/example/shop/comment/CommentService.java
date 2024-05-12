package com.example.shop.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public void saveComments(String username, String content, Long parentId){
        Comment comment = new Comment();
        comment.setParentId(parentId);
        comment.setUsername(username);
        comment.setContent(content);
        commentRepository.save(comment);
    }
}
