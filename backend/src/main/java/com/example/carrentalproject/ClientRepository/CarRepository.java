package com.example.carrentalproject.ClientRepository;

import com.example.carrentalproject.domain.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {

    Optional<Car> findByIdAndAvailableTrue(Long aLong);

}
