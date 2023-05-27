package com.dental.service;

import com.dental.entity.Doctor;
import com.dental.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService  {
    @Autowired
    DoctorRepository doctorRepository;

    public List<Doctor> getAll() {
        return doctorRepository.findAll();
    }

    public Doctor get(int id) {
        return doctorRepository.findById(id).get();
    }

    public void save(Doctor blog) {
        doctorRepository.save(blog);
    }

    public void delete(int id) {
        doctorRepository.deleteById(id);
    }

    public Page<Doctor> findAll(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }

    public Page<Doctor> findAllByUserStatusAndUserFullNameAndGender(boolean status ,String fullName, String gender, Pageable pageable) {
        return doctorRepository.findAllByUserStatusAndUserFullNameAndGender(status, fullName, gender, pageable);
    }

    public Page<Doctor> findAllByUserStatusAndUserFullName(boolean status ,String fullName, Pageable pageable) {
        return doctorRepository.findAllByUserStatusAndUserFullName(status, fullName, pageable);
    }

    public Page<Doctor> findAllByUserStatusAndGender(boolean status ,String gender, Pageable pageable) {
        return doctorRepository.findAllByUserStatusAndGender(status, gender, pageable);
    }

    public Page<Doctor> findAllByUserFullNameAndGender(String fullName ,String gender, Pageable pageable) {
        return doctorRepository.findAllByUserFullNameAndGender(fullName, gender, pageable);
    }

    public Page<Doctor> findAllByUserFullName(String fullName, Pageable pageable) {
        return doctorRepository.findAllByUserFullName(fullName, pageable);
    }

    public Page<Doctor> findAllByUserStatus(boolean status, Pageable pageable) {
        return doctorRepository.findAllByUserStatus(status, pageable);
    }

    public Page<Doctor> findAllByGender(String gender, Pageable pageable) {
        return doctorRepository.findAllByGender(gender, pageable);
    }
}
