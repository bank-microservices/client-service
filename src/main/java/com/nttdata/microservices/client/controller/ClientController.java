package com.nttdata.microservices.client.controller;

import com.nttdata.microservices.client.service.dto.ClientDto;
import com.nttdata.microservices.client.service.ClientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/client")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public Flux<ClientDto> getAll() {
        log.info("List of Clients");
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ClientDto>> findById(@PathVariable String id) {
        log.info("get Client id: {}", id);
        return clientService.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/documentNumber/{number}")
    public Mono<ResponseEntity<ClientDto>> findNumber(@PathVariable String number) {
        return clientService.findByDocumentNumber(number)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientDto> create(@Valid @RequestBody ClientDto customer) {
        return clientService.create(customer);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<ClientDto>> update(@PathVariable String id, @Valid @RequestBody ClientDto customer) {
        return clientService.update(id, customer)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable String id) {
        log.info("delete Client id: {} ", id);
        return clientService.delete(id);
    }

}
