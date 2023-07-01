package com.example.carrentalproject.service;

import com.example.carrentalproject.ClientRepository.ClientRepository;
import com.example.carrentalproject.domain.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ClientService {
    private final ClientRepository clientRepository;
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    public List<Client> getAllClients() {
        log.info("getting all clients from repository");

        var result = clientRepository.findAll();

        log.info("found [{}] clients", result.size());
        log.debug("results: {}", result);

        return result;
    }
}
