package com.example.carrentalproject.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record BookingRecordDto (
        Long id,
        CarDto bookedCar,
        ClientDto client,
        LocalDate startDate,
        LocalDate endDate,
        long fullPriceInEuroCents
) {

}