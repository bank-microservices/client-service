package com.nttdata.microservices.repository;

import com.nttdata.microservices.entity.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ClientRepository extends ReactiveMongoRepository<Client, String>{

    Mono<Client> findByDocumentNumber(String documentNumber);
}
