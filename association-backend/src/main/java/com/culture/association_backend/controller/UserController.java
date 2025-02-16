package com.culture.association_backend.controller;

import com.culture.association_backend.model.User;
import com.culture.association_backend.service.MemberService;
import com.culture.association_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllusers();
        return ResponseEntity.ok(users); // Should return a JSON response by default
    }

}