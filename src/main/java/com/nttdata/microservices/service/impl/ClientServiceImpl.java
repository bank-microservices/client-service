package com.nttdata.microservices.service.impl;

import com.nttdata.microservices.dto.ClientDto;
import com.nttdata.microservices.repository.ClientRepository;
import com.nttdata.microservices.service.ClientService;
import com.nttdata.microservices.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Flux<ClientDto> findAll() {
        return clientRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<ClientDto> findById(String id) {
        return clientRepository.findById(id)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<ClientDto> create(ClientDto customerDto) {
        return Mono.just(customerDto)
                .map(EntityDtoUtil::toEntity)
                .flatMap(this.clientRepository::insert)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<ClientDto> update(String id, ClientDto customerDto) {
        return clientRepository.findById(id)
                .flatMap(p -> Mono.just(customerDto)
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.clientRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    @Override
    public Mono<Void> delete(String id) {
        return clientRepository.deleteById(id);
    }

    @Override
    public Mono<ClientDto> findByDocumentNumber(String documentNumber) {
        return clientRepository.findByDocumentNumber(documentNumber)
                .map(EntityDtoUtil::toDto);
    }


}
