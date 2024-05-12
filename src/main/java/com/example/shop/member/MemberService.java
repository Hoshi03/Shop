package com.example.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void saveMember(String userName, String password, String displayName){
        Member member = new Member();
        member.setUserName(userName);
        member.setPassWord(passwordEncoder.encode(password));
        member.setDisplayName(displayName);
        memberRepository.save(member);
    }

    public MemberDto getMemberInfo(Long id){
        Optional<Member> member = memberRepository.findById(id);
        if (member.isPresent()){
            Member result = member.get();
            return new MemberDto(result.getUserName(),result.getDisplayName(), result.getId());
        }
        else return null;
    }
}
