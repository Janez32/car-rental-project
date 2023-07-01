package com.example.carrentalproject.ClientRepository;

import com.example.carrentalproject.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClientRepository extends JpaRepository<Client, Long> {
}