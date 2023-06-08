package com.dental.repository;

import com.dental.entity.RateStar;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RateStarRepository extends JpaRepository<RateStar, Integer> {
    Page<RateStar> findAll(Pageable pageable);

    Page<RateStar> findAllByServiceServiceId(int serviceId, Pageable pageable);

    @Query("SELECT r.service.serviceId, AVG(r.star) FROM RateStar r GROUP BY r.service.serviceId")
    @Transactional
    List<Object[]> findAllWithAvg();


    List<RateStar> findTop5ByStarGreaterThanOrderByStarDesc(int greater);
//    List<RateStar> findTop5ByStarGreaterThanOrderByStarDesc(int greater);

}
