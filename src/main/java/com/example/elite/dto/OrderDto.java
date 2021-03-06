package com.example.elite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto {
    private int id;
    private boolean status;
    private boolean isConfirmed;
    private LocalDate createDate;
    private double totalPrice;
    private int userId;
    private String userName;
}
