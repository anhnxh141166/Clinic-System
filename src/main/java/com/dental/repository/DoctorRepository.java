package com.dental.repository;

import com.dental.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
// nó là làm hàm để liên kết với data
@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Page<Doctor> findAll(Pageable pageable);

    Page<Doctor> findAllByUserStatusAndUserFullNameAndGender(boolean status, String name, String gender, Pageable pageable);

    Page<Doctor> findAllByUserStatusAndUserFullName(boolean status, String name, Pageable pageable);

    Page<Doctor> findAllByUserStatusAndGender(boolean status, String gender, Pageable pageable);

    Page<Doctor> findAllByUserFullNameAndGender(String fullName, String gender, Pageable pageable);

    Page<Doctor> findAllByUserFullName(String fullName, Pageable pageable);

    Page<Doctor> findAllByUserStatus(boolean status, Pageable pageable);

    Page<Doctor> findAllByGender(String gender, Pageable pageable);
}
