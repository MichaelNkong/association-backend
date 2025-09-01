package com.culture.association_backend.service;

import com.culture.association_backend.model.Member;
import com.culture.association_backend.repository.MemberRepository;
import com.culture.association_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UserRepository userrRepository;

    public List<com.culture.association_backend.model.Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
    public void activateMember(Long user_id, String  activation_id) {
        memberRepository.activateMember(user_id, activation_id);
    }
}