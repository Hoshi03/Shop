package com.example.shop.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {

    // derived query methods로 여기선 유저네임이 일치하는 member를 하나 찾아온다
    Optional<Member> findByUserName(String username);
}
