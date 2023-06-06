package com.dental.service;

import com.dental.entity.Patient;
import com.dental.entity.User;
import com.dental.repository.PatientRepository;
import com.dental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public void updateUser(String fullName, boolean status, int id) {
        userRepository.setUserInfoById(fullName, status, id);
    }


    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public User get(int id) {
        return userRepository.findById(id).get();
    }


    public void delete(int id) {
        userRepository.deleteById(id);
    }

    public User update(User user, String newPassword){
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(true);
        user.setRole("Patient");
        userRepository.save(user);
        Patient patient = new Patient();

        User user1 = userRepository.findByEmail(user.getEmail());
        patient.setUser(user1);
        patientRepository.save(patient);
    }

    public boolean checkEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }
}
