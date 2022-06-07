package com.nttdata.microservices.client.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.nttdata.microservices.client.repository.ClientRepository;
import com.nttdata.microservices.client.service.ClientService;
import com.nttdata.microservices.client.service.dto.ClientDto;
import com.nttdata.microservices.client.service.mapper.ClientMapper;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    /**
     * Find all clients and map them to a DTO
     * 
     * @return A Flux of ClientDto objects.
     */
    @Override
    public Flux<ClientDto> findAll() {
        return clientRepository.findAll()
                .map(clientMapper::toDto);
    }

    /**
     * Find a client by id and return a Mono of ClientDto
     * 
     * @param id The id of the client to be retrieved.
     * @return A Mono of ClientDto
     */
    @Override
    public Mono<ClientDto> findById(String id) {
        return clientRepository.findById(id)
                .map(clientMapper::toDto);
    }

    /**
     * It takes a ClientDto, converts it to an entity, sets the createAt date,
     * inserts it into the
     * database, and then converts it back to a Dto
     * 
     * @param customerDto The object that will be passed to the method.
     * @return Mono<ClientDto>
     */
    @Override
    public Mono<ClientDto> create(ClientDto customerDto) {
        return Mono.just(customerDto)
                .map(clientMapper::toEntity)
                .map(dto -> {
                    dto.setCreateAt(new Date());
                    return dto;
                })
                .flatMap(this.clientRepository::insert)
                .map(clientMapper::toDto);
    }

    /**
     * Find a client by id, then map the clientDto to an entity, then set the id of
     * the entity to the
     * id of the client, then save the entity, then map the entity to a dto.
     * 
     * @param id          The id of the client to be updated
     * @param customerDto The object that contains the data to be updated.
     * @return Mono<ClientDto>
     */
    @Override
    public Mono<ClientDto> update(String id, ClientDto customerDto) {
        return clientRepository.findById(id)
                .flatMap(p -> Mono.just(customerDto)
                        .map(clientMapper::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.clientRepository::save)
                .map(clientMapper::toDto);
    }

    /**
     * Delete a client by id
     * 
     * @param id The id of the client to be deleted.
     * @return A Mono of Void.
     */
    @Override
    public Mono<Void> delete(String id) {
        return clientRepository.deleteById(id);
    }

    /**
     * It returns a Mono of ClientDto, which is a reactive wrapper for a single
     * value, and it finds a
     * client by document number, which is a string, and it maps the client to a
     * Dto, which is a data
     * transfer object.
     * 
     * @param documentNumber String
     * @return A Mono&lt;ClientDto&gt;
     */
    @Override
    public Mono<ClientDto> findByDocumentNumber(String documentNumber) {
        return clientRepository.findByDocumentNumber(documentNumber)
                .map(clientMapper::toDto);
    }

}
