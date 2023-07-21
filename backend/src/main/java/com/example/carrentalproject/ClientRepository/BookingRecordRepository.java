package com.example.carrentalproject.ClientRepository;

import com.example.carrentalproject.domain.BookingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRecordRepository extends JpaRepository<BookingRecord,Long> {


    @Query("""
    select br from BookingRecord br
    where br.client.name = :name and br.bookedCar.color = :color
""")
    Optional<BookingRecord> findByClientNameAndBookedCarColor(@Param("name") String name,
                                                              @Param("color") String color);

}
