package com.dental.repository;

import com.dental.entity.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {
    Page<Service> findAll(Pageable pageable);

    Page<Service> findAllByStatusAndTitleContaining(boolean status, String title, Pageable pageable);

    Page<Service> findAllByTitleContaining(String title, Pageable pageable);

    Page<Service> findAllByStatus(boolean status, Pageable pageable);

    @Query("SELECT s, AVG(r.star) FROM Service s LEFT JOIN s.rateStar r GROUP BY r.service.serviceId")
    Page<Object[]> findAllServicesWithAverageStar(Pageable pageable);
}
