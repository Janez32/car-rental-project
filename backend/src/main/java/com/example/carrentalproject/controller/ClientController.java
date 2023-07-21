package com.example.carrentalproject.controller;

import com.example.carrentalproject.domain.Client;
import com.example.carrentalproject.dto.ClientDto;
import com.example.carrentalproject.mapper.ClientMapper;
import com.example.carrentalproject.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class ClientController {
    private final ClientService clientService;

    private final ClientMapper clientMapper;
    public ClientController(ClientService clientService, ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }

    @GetMapping("/clients")
    List<ClientDto> allClients(){
        log.info("all clients endpoint");
        var clients = clientService.getAllClients();

        return clients.stream()
                .map(client -> clientMapper.fromEntityToDto(client))
//                .map(clientMapper::fromEntityToDto)
                .toList();
    }

    @PostMapping("/clients")
    ResponseEntity<ClientDto> createNewClient(@RequestBody ClientDto clientToSave, UriComponentsBuilder ucb) {
        log.info("trying to save new client: [{}]", clientToSave);

        Client createdClient = clientService.saveClient(clientMapper.fromDtoToEntity(clientToSave));

        URI path = ucb.path("/api/client/{id}")
                .buildAndExpand(Map.of("id", createdClient.getId()))
                .toUri();

        return ResponseEntity.created(path)
                .body(clientMapper.fromEntityToDto(createdClient));
    }
}
