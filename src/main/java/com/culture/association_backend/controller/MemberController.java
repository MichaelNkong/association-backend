package com.culture.association_backend.controller;

import com.culture.association_backend.model.User;
import com.culture.association_backend.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public List<com.culture.association_backend.model.Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @PostMapping
    public Member addMember(@RequestBody Member member) {
        return (Member) memberService.saveMember((com.culture.association_backend.model.Member) member);
    }
    @PostMapping("/activate")
    public ResponseEntity<Object> activateMember(@RequestBody Map<String, String> data) {
        Map<String, Object> response = new HashMap<>();
        try{

            memberService.activateMember(Long.valueOf(data.get("user_id")),data.get("activation_id"));
            response.put("message", "user activated successfully");
            return ResponseEntity.ok(response);
        }
        catch(Exception e){
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);

        }


    }
}