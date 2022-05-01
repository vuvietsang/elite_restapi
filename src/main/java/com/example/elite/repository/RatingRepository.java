package com.example.elite.repository;

import com.example.elite.entities.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Integer> {
    Page<Rating> getAllRatingByProductId(Long productId, Pageable pageable);
}
