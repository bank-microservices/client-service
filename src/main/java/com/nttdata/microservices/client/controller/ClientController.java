package com.nttdata.microservices.client.controller;

import com.nttdata.microservices.client.service.ClientService;
import com.nttdata.microservices.client.service.dto.ClientDto;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/client")
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
    log.info("find Client by id: {}", id);
    return clientService.findById(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @GetMapping("/document-number/{number}")
  public Mono<ResponseEntity<ClientDto>> findNumber(@PathVariable String number) {
    log.info("find Client by documentNumber: {}", number);
    return clientService.findByDocumentNumber(number)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<ClientDto> create(@Valid @RequestBody ClientDto clientDto) {
    log.info("create Client");
    return clientService.create(clientDto);
  }

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public Mono<ResponseEntity<ClientDto>> update(@PathVariable String id,
                                                @Valid @RequestBody ClientDto clientDto) {
    log.info("update Client by id: {}", id);
    return clientService.update(id, clientDto)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public Mono<Void> delete(@PathVariable String id) {
    log.info("delete Client by id: {} ", id);
    return clientService.delete(id);
  }

}
