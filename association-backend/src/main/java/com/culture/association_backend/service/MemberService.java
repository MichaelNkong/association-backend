package com.culture.association_backend.service;

import com.culture.association_backend.model.Member;
import com.culture.association_backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<com.culture.association_backend.model.Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }
}