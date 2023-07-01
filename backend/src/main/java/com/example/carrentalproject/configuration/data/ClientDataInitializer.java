package com.example.carrentalproject.configuration.data;

import com.example.carrentalproject.ClientRepository.ClientRepository;
import com.example.carrentalproject.domain.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;

// TODO : Move it to develop profile
@Component
@Slf4j
public class ClientDataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;

    public ClientDataInitializer(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Creating some clients");

        Client client = Client.builder()
                .name("Jevgeni")
                .surname("Vahrusev")
                .phone("12312333")
                .email("asdasd@gmail.com")
                .address("Estonia")
                .dateOfBirth(LocalDate.of(2002, Month.AUGUST, 30))
                .build();

        clientRepository.save(client);
    }
}
