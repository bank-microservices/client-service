package com.nttdata.microservices.service;

import com.nttdata.microservices.dto.ClientDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ClientService {

    Flux<ClientDto> findAll();

    Mono<ClientDto> findById(String id);

    Mono<ClientDto> findByDocumentNumber(String documentNumber);

    Mono<ClientDto> create(ClientDto customerDto);

    Mono<ClientDto> update(String id, ClientDto customerDto);

    Mono<Void> delete(String id);

}
