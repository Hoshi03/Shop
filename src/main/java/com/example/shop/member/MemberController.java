package com.example.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    //로그아웃 된 경우에만 회원가입
    @GetMapping("/register")
    String register(Authentication auth){

        if (auth == null || !auth.isAuthenticated()){
            return "register.html";
        }
        return "redirect:/list";
    }


    @PostMapping("/member")
    String signIn(String username, String password, String displayName){
        if (username == null || password == null || displayName == null
        || username.length() < 3 || password.length() < 3){
            return "redirect:/register";
        }
        memberService.saveMember(username, password, displayName);
        return "redirect:/list";
    }

    @GetMapping("/login")
    String login(){
        return "login.html";
    }

    //로그인된 유저만 볼수 있게
    @GetMapping("/my-page")
    String myPage(Authentication auth){
/*      System.out.println(auth);
        System.out.println(auth.getName());
        //현재 로그인 여부
        System.out.println(auth.isAuthenticated());
        //유저가 가진 권한
        System.out.println(auth.getAuthorities().contains(new SimpleGrantedAuthority("일반유저")));*/

        //타입 캐스팅해서 유저 닉네임까지 보기
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        System.out.println(customUser.displayName);
        return "mypage.html";
    }

    // 유저 정보 보내기, 그대로 member 데이터 전부를 보내면 비밀번호까지 보내버린다
    @GetMapping("/user/{id}")
    @ResponseBody
    MemberDto getUser(@PathVariable Long id){
        return  memberService.getMemberInfo(id);
    }
}