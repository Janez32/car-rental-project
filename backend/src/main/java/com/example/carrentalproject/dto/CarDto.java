package com.example.carrentalproject.dto;

import com.example.carrentalproject.domain.enumeration.Color;
import lombok.Builder;

import java.time.YearMonth;

@Builder
public record CarDto(Long id,
                     String brand,
                     String model,
                     YearMonth productionYear,
                     Color color,
                     boolean available,
                     long pricePerDayInEuroCents
) {

}
