package com.dental.service;

import com.dental.entity.RateStar;
import com.dental.repository.RateStarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RateStarService {

    @Autowired
    RateStarRepository rateStarRepository;

    public List<RateStar> getAll() {
        return rateStarRepository.findAll();
    }

    public RateStar get(int id) {
        return rateStarRepository.findById(id).get();
    }

    public Page<RateStar> getAllByServiceId(int serviceId, Pageable pageable) {
        return rateStarRepository.findAllByServiceServiceId(serviceId, pageable);
    }

    public void save(RateStar rateStar) {
        rateStarRepository.save(rateStar);
    }

    public void delete(int id) {
        rateStarRepository.deleteById(id);
    }

    public Page<RateStar> findAll(Pageable pageable) {
        return rateStarRepository.findAll(pageable);
    }
}
