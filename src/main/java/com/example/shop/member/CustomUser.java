package com.example.shop.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

//User를 확장해서 displayName까지 추가하는 CustomUser 클래스
public class CustomUser extends User {
    public String displayName;
    public Long userId;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
