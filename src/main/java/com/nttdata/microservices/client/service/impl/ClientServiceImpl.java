package com.nttdata.microservices.client.service.impl;

import static com.nttdata.microservices.client.util.MessageUtils.getMsg;

import com.nttdata.microservices.client.entity.ClientProfile;
import com.nttdata.microservices.client.entity.ClientType;
import com.nttdata.microservices.client.entity.DocumentType;
import com.nttdata.microservices.client.exception.BadRequestException;
import com.nttdata.microservices.client.repository.ClientRepository;
import com.nttdata.microservices.client.service.ClientService;
import com.nttdata.microservices.client.service.dto.ClientDto;
import com.nttdata.microservices.client.service.mapper.ClientMapper;
import java.util.Date;
import org.springframework.stereotype.Service;
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
   * It returns a Mono of ClientDto, which is a reactive wrapper for a single
   * value, and it finds a
   * client by document number, which is a string, and it maps the client to a
   * Dto, which is a data
   * transfer object.
   *
   * @param documentNumber document number of Client
   * @return A Mono&lt;ClientDto&gt;
   */
  @Override
  public Mono<ClientDto> findByDocumentNumber(String documentNumber) {
    return clientRepository.findByDocumentNumber(documentNumber)
        .map(clientMapper::toDto);
  }

  /**
   * It takes a ClientDto, converts it to an entity, sets the createAt date,
   * inserts it into the database, and then converts it back to a Dto
   *
   * @param clientDto The object that will be passed to the method.
   * @return Mono&lt;ClientDto&gt;
   */
  @Override
  public Mono<ClientDto> create(ClientDto clientDto) {
    return Mono.just(clientDto)
        .flatMap(this::existClient)
        .flatMap(this::validDocumentType)
        .flatMap(this::validClientProfile)
        .map(clientMapper::toEntity)
        .map(dto -> {
          dto.setCreateAt(new Date());
          return dto;
        })
        .flatMap(this.clientRepository::insert)
        .map(clientMapper::toDto);
  }

  private Mono<ClientDto> existClient(final ClientDto clientDto) {
    return findByDocumentNumber(clientDto.getDocumentNumber())
        .flatMap(client -> Mono.error(new BadRequestException(getMsg("client.document.already"))))
        .thenReturn(clientDto);
  }

  private Mono<ClientDto> validDocumentType(final ClientDto clientDto) {
    final DocumentType documentType = DocumentType.valueOf(clientDto.getDocumentType());
    final ClientType clientType = ClientType.valueOf(clientDto.getClientType());
    if (ClientType.PERSONAL == clientType && !documentType.in(DocumentType.DNI, DocumentType.CE)) {
      return Mono.error(new BadRequestException(getMsg("client.document.type.invalid",
          documentType, clientType)));
    } else if (ClientType.BUSINESS == clientType && !documentType.in(DocumentType.RUC)) {
      return Mono.error(new BadRequestException(getMsg("client.document.type.invalid",
          documentType, clientType)));
    }
    return Mono.just(clientDto);

  }

  private Mono<ClientDto> validClientProfile(final ClientDto clientDto) {
    final ClientProfile clientProfile = ClientProfile.valueOf(clientDto.getClientProfile());
    final ClientType clientType = ClientType.valueOf(clientDto.getClientType());
    if (ClientType.PERSONAL == clientType
        && !clientProfile.in(ClientProfile.VIP, ClientProfile.REGULAR)) {
      return Mono.error(new BadRequestException(getMsg("client.profile.invalid",
          clientProfile, clientType)));
    } else if (ClientType.BUSINESS == clientType
        && !clientProfile.in(ClientProfile.PYME, ClientProfile.REGULAR)) {
      return Mono.error(new BadRequestException(getMsg("client.profile.invalid",
          clientProfile, clientType)));
    }
    return Mono.just(clientDto);
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
            .flatMap(this::validDocumentType)
            .flatMap(this::validClientProfile)
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


}
