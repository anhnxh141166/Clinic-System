package com.dental.repository;

import com.dental.entity.Blog;
import com.dental.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Page<Doctor> findAll(Pageable pageable);

    Page<Doctor> findAllByUserStatusAndUserFullNameAndUserGender(boolean status, String name, String gender, Pageable pageable);

    Page<Doctor> findAllByUserStatusAndUserFullName(boolean status, String name, Pageable pageable);

    Page<Doctor> findAllByUserStatusAndUserGender(boolean status, String gender, Pageable pageable);

    Page<Doctor> findAllByUserFullNameAndUserGender(String fullName, String gender, Pageable pageable);

    Page<Doctor> findAllByUserFullName(String fullName, Pageable pageable);

    Page<Doctor> findAllByUserStatus(boolean status, Pageable pageable);

    Page<Doctor> findAllByUserGender(String gender, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "UPDATE doctor d SET d.description = ?1 WHERE d.doctor_id = ?2", nativeQuery = true)
    void setDoctorInfoById(String description, int userId);

    // User Page
    Page<Doctor> findAllByUserStatusTrue(Pageable pageable);

    Page<Doctor> findAllByUserFullNameAndUserStatusTrue(String fullName, Pageable pageable);
}
