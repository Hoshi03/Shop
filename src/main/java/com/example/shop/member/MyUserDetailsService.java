package com.example.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    //스프링 시큐리티로 비밀번호 검사
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> result = memberRepository.findByUserName(username);
        if (result.isEmpty())
        {
            throw new UsernameNotFoundException("존재하지 않는 ID입니다");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        Member member = result.get();
        authorities.add(new SimpleGrantedAuthority("일반유저"));
        CustomUser user = new CustomUser(member.getUserName(), member.getPassWord(), authorities);
        user.displayName = member.getDisplayName();
        user.userId = member.getId();
        return user;
    }
}

