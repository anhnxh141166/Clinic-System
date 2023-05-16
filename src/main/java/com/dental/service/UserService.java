package com.dental.service;

import com.dental.entity.User;
import com.dental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    List<User> getAllUser(){
        return userRepository.findAll();
    }
}
