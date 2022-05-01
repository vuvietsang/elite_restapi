package com.example.elite.services;

import com.example.elite.dto.RatingDto;
import com.example.elite.entities.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RatingService {
    public Page<RatingDto> getAllRatingByProductId(Long productId, Pageable pageable);
}
