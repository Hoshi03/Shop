package com.example.shop.comment;

import com.example.shop.member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    //로그인 된 유저 정보는 Authentication에서 가져온다
    @PreAuthorize("isAuthenticated()") //로그인 여부 확인
    @PostMapping("/comment")
    String postComment(@RequestParam String content,
                       @RequestParam Long parentId,
                       Authentication auth){
        CustomUser user = (CustomUser) auth.getPrincipal(); //로그인 된 사용자 정보 가져오기!
        commentService.saveComments(user.getUsername(),content,parentId);
        return "redirect:detail/"+parentId;
    }
}
