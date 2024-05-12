package com.example.shop.member;

import lombok.Getter;

//DTO를 만들어서 데이터를 보낸다
@Getter
public class MemberDto {
    private String username;
    private String displayname;
    private Long id;
    MemberDto(String username, String displayname){
        this.username = username;
        this.displayname = displayname;
    }

    MemberDto(String username, String displayname, Long id){
        this.username = username;
        this.displayname = displayname;
        this.id = id;
    }
}
