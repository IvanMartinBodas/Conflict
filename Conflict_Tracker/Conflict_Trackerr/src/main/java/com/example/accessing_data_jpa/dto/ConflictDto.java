package com.example.accessing_data_jpa.dto;

import java.time.LocalDate;
import java.util.List;

public record ConflictDto(
        Long id,
        String name,
        LocalDate startDate,
        String status,
        String description,
        List<CountryDto> countries
) {}