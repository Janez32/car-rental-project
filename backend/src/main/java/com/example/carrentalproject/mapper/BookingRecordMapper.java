package com.example.carrentalproject.mapper;

import com.example.carrentalproject.domain.BookingRecord;
import com.example.carrentalproject.dto.BookingRecordDto;
import org.springframework.stereotype.Component;

@Component
public class BookingRecordMapper implements Mapper<BookingRecord, BookingRecordDto> {

    private final CarMapper carMapper;
    private final ClientMapper clientMapper;

    public BookingRecordMapper(CarMapper carMapper, ClientMapper clientMapper) {
        this.carMapper = carMapper;
        this.clientMapper = clientMapper;
    }

    @Override
    public BookingRecordDto fromEntityToDto(BookingRecord entity) {
        return BookingRecordDto.builder()
                .id(entity.getId())
                .bookedCar(carMapper.fromEntityToDto(entity.getBookedCar()))
                .client(clientMapper.fromEntityToDto(entity.getClient()))
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .fullPriceInEuroCents(entity.getFullPriceInEuroCents())
                .build();
    }

    @Override
    public BookingRecord fromDtoToEntity(BookingRecordDto dto) {
        return BookingRecord.builder()
                .id(dto.id())
                .bookedCar(carMapper.fromDtoToEntity(dto.bookedCar()))
                .client(clientMapper.fromDtoToEntity(dto.client()))
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .fullPriceInEuroCents(dto.fullPriceInEuroCents())
                .build();
    }
}

