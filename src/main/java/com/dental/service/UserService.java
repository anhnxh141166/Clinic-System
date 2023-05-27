package com.dental.service;

import com.dental.entity.Blog;
import com.dental.entity.User;
import com.dental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public void addUser(User user) {
        System.out.println(user);
        userRepository.save(user);
    }

    public User get(int id) {
        return userRepository.findById(id).get();
    }

    public User update(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
}
