package com.example.elite.services.implement;

import com.example.elite.dto.RatingDto;
import com.example.elite.entities.Rating;
import com.example.elite.repository.ProductRepository;
import com.example.elite.repository.RatingRepository;
import com.example.elite.services.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class RatingServiceImplement implements RatingService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Page<RatingDto> getAllRatingByProductId(Long productId, Pageable pageable) throws NoSuchElementException {
        Page<Rating> ratingPage = ratingRepository.getAllRatingByProductId(productId,pageable);
        Page<RatingDto> ratingDtoPage = ratingPage.map(rating -> modelMapper.map(rating,RatingDto.class));
        return ratingDtoPage;
    }
}
