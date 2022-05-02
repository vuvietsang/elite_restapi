package com.example.elite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private String comment;
    private int ratingStar ;
    private Long productId;
    private int userId;
    private String productName;
    private String userName;
    private LocalDate ratingDate;
}
