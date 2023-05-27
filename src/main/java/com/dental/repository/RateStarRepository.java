package com.dental.repository;

import com.dental.entity.RateStar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateStarRepository extends JpaRepository<RateStar, Integer> {
    Page<RateStar> findAll(Pageable pageable);

    Page<RateStar> findAllByServiceServiceId(int serviceId, Pageable pageable);
}
