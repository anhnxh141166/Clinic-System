package com.dental.repository;

import com.dental.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);

    @Transactional
    @Modifying
    @Query(value = "UPDATE user u SET u.full_name = ?1, u.status = ?2 WHERE u.user_id = ?3", nativeQuery = true)
    void setUserInfoById(String fullName, boolean status, int userId);

    public User findByToken(String token);

    Page<User> findAllByStatusAndFullNameAndRole(boolean status, String name, String role, Pageable pageable);

    Page<User> findAllByStatusAndFullName(boolean status, String name, Pageable pageable);

    Page<User> findAllByStatusAndRole(boolean status, String role, Pageable pageable);

    Page<User> findAllByFullNameAndRole(String fullName, String role, Pageable pageable);

    Page<User> findAllByFullName(String fullName, Pageable pageable);

    Page<User> findAllByStatus(boolean status, Pageable pageable);

    Page<User> findAllByRole(String role, Pageable pageable);

    Page<User> findAll(Pageable pageable);

    @Query("SELECT COUNT(u) FROM User u WHERE (u.role = 'Doctor' OR u.role = 'Staff') AND u.status = ?1 ")
    int countEmployeeActive(boolean status);

}
