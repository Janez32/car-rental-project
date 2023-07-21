package com.example.carrentalproject.service;

import com.example.carrentalproject.ClientRepository.BookingRecordRepository;
import com.example.carrentalproject.domain.BookingRecord;
import com.example.carrentalproject.domain.Car;
import com.example.carrentalproject.domain.Client;
import com.example.carrentalproject.dto.CarBookingRequestDto;
import com.example.carrentalproject.exception.PeriodCalculationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@Slf4j
public class BookingRecordService {

    private final BookingRecordRepository bookingRecordRepository;
    private final ClientService clientService;
    private final CarService carService;

    public BookingRecordService(BookingRecordRepository bookingRecordRepository,
                                ClientService clientService, CarService carService) {
        this.bookingRecordRepository = bookingRecordRepository;
        this.clientService = clientService;
        this.carService = carService;
    }

    public List<BookingRecord> findAllBookingRecords() {
        log.info("Finding all booking records");

        var records = bookingRecordRepository.findAll();

        log.info("Number of found booking records: [{}]", records.size());
        log.debug("All booking records: {}", records);

        return records;
    }

    @Transactional
    public BookingRecord createNewBooking(CarBookingRequestDto bookingRequestDto) {
        log.info("creating new booking record based on parameters: [{}]", bookingRequestDto);

        // find client based on given id
        Client currentClient = clientService.findClientWithId(bookingRequestDto.clientId());

        // find a car based on given id
        Car carToBook = carService.findAvailableCarWithId(bookingRequestDto.clientId());

        // calculate full price
        long bookingPriceInEuroCents = calculateBookingPrice(bookingRequestDto, carToBook);

        // save record
        BookingRecord newRecordToSave = BookingRecord.builder()
                .bookedCar(carToBook)
                .client(currentClient)
                .startDate(bookingRequestDto.startDate())
                .endDate(bookingRequestDto.endDate())
                .fullPriceInEuroCents(bookingPriceInEuroCents)
                .build();

        carToBook.setAvailable(false);
        carService.saveCar(carToBook);

        BookingRecord saved = bookingRecordRepository.save(newRecordToSave);
        log.info("Created booking record: [{}}", saved);

        return saved;

    }

    public long calculateBookingPrice(CarBookingRequestDto bookingRequestDto, Car carToBook) {

        long pricePerDayInEuroCents = carToBook.getPriceList().getPricePerDayInEuroCents();
        LocalDate startTime = bookingRequestDto.startDate();
        LocalDate endTime = bookingRequestDto.endDate();

        long durationInDays = Period.between(bookingRequestDto.startDate(), bookingRequestDto.endDate()).getDays();
        final long minimumDurationInDays = 1;
        if (endTime.isBefore(startTime)) {
            throw new PeriodCalculationException("End date is before starting date");
        } else if (durationInDays < minimumDurationInDays) {
            throw new PeriodCalculationException("Booking duration is too low");
        }
        return pricePerDayInEuroCents * durationInDays;
    }


}