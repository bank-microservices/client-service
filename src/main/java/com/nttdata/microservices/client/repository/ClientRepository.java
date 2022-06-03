package com.nttdata.microservices.client.repository;

import com.nttdata.microservices.client.entity.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String>{

    Mono<Client> findByDocumentNumber(String documentNumber);
}
