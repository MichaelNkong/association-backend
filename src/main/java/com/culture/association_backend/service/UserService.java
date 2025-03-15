package com.culture.association_backend.service;

import com.culture.association_backend.model.User;
import com.culture.association_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

   public List<User> getAllusers() {
        return userRepository.findAll();
    }
}