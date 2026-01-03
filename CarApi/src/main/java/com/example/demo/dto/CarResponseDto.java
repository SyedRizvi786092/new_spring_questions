package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarResponseDto {
    private Long id;
    private String type;
    private String model;
    private String title;
    // constructors, getters, setters...
}

//kuch annotations whi basic