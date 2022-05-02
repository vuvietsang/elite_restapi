package com.example.elite.services.implement;

import com.example.elite.dto.RatingDto;
import com.example.elite.entities.Product;
import com.example.elite.entities.Rating;
import com.example.elite.entities.User;
import com.example.elite.repository.ProductRepository;
import com.example.elite.repository.RatingRepository;
import com.example.elite.repository.UserRepository;
import com.example.elite.services.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class RatingServiceImplement implements RatingService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RatingRepository ratingRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public Page<RatingDto> getAllRatingByProductId(Long productId, Pageable pageable) throws NoSuchElementException {
        Page<Rating> ratingPage = ratingRepository.getAllRatingByProductId(productId, pageable);
        Page<RatingDto> ratingDtoPage = ratingPage.map(rating -> modelMapper.map(rating, RatingDto.class));
        return ratingDtoPage;
    }

    @Override
    public RatingDto addRatingtoProduct(RatingDto dto) throws NoSuchElementException {
        Optional<Product> product = productRepository.findById(dto.getProductId());
        Optional<User> user = userRepository.findById(dto.getUserId());
        Rating rating = Rating.builder().product(product.get()).user(user.get()).ratingDate(LocalDate.now()).ratingStar(dto.getRatingStar()).comment(dto.getComment()).build();
        Rating ratingSave = ratingRepository.save(rating);
        RatingDto ratingDto = modelMapper.map(ratingSave, RatingDto.class);
        return ratingDto;
    }
}
