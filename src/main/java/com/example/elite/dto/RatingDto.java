package com.example.elite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private String comment;
    private String ratingStar ;
    private String productId;
    private String productName;
    private String userName;
}
