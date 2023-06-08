package com.dental.repository;

import com.dental.entity.User;
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
}
