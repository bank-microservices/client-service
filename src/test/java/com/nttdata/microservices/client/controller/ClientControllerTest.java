package com.nttdata.microservices.client.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.nttdata.microservices.client.entity.ClientProfile;
import com.nttdata.microservices.client.entity.ClientType;
import com.nttdata.microservices.client.entity.DocumentType;
import com.nttdata.microservices.client.repository.ClientRepository;
import com.nttdata.microservices.client.service.dto.ClientDto;
import com.nttdata.microservices.client.service.mapper.ClientMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class ClientControllerTest {

  @Autowired
  private WebTestClient webTestClient;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ClientMapper clientMapper;

  static String CLIENT_URL = "/api/v1/client";

  @BeforeEach
  void setUp() {
    ClientDto clientDto1 = ClientDto.builder().id("001").firstNameBusiness("Luis Gustavo")
        .surnames("Cueva Basso").documentNumber("00000015").email("lucuba06@gmail.com")
        .address("Jr. Eucaliptos 493").phoneNumber("994451236")
        .documentType(DocumentType.DNI.name())
        .clientType(ClientType.PERSONAL.name())
        .clientProfile(ClientProfile.REGULAR.name())
        .status(true).build();

    ClientDto clientDto2 = ClientDto.builder().firstNameBusiness("Carlos")
        .surnames("Zarate Gomez").documentNumber("00000016").email("czarate@gmail.com")
        .address("Av. Brasil 1345").phoneNumber("994451237")
        .documentType(DocumentType.RUC.name())
        .clientType(ClientType.BUSINESS.name())
        .clientProfile(ClientProfile.REGULAR.name())
        .status(false).build();

    List<ClientDto> clients = List.of(clientDto1, clientDto2);
    clientRepository
        .deleteAll()
        .thenMany(clientRepository.saveAll(clientMapper.toEntity(clients)))
        .blockLast();
  }

  @Test
  void getAllClients() {

    webTestClient
        .get()
        .uri(CLIENT_URL)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBodyList(ClientDto.class)
        .hasSize(2);
  }

  @Test
  void getClientById() {
    var id = "001";
    webTestClient
        .get()
        .uri(CLIENT_URL + "/{id}", id)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(ClientDto.class)
        .consumeWith(clientDtoEntityExchangeResult -> {
          var clientDto = clientDtoEntityExchangeResult.getResponseBody();
          assert clientDto != null;
        });
  }

  @Test
  void getClientByDocumentNumber() {
    var number = "00000015";
    webTestClient
        .get()
        .uri(CLIENT_URL + "/document-number/{number}", number)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(ClientDto.class)
        .consumeWith(clientDtoEntityExchangeResult -> {
          var clientDto = clientDtoEntityExchangeResult.getResponseBody();
          assert clientDto != null;
        });
  }

  @Test
  void getClientByIdNotFoud() {
    var id = "NAN";
    webTestClient
        .get()
        .uri(CLIENT_URL + "/{id}", id)
        .exchange()
        .expectStatus()
        .isNotFound();
  }

  @Test
  void createClient() {

    ClientDto createClient = ClientDto.builder().firstNameBusiness("Juana Maria")
        .surnames("Lopez Garcia").documentNumber("00000020").email("juana.lopez@gmail.com")
        .address("Jr. Molina 500").phoneNumber("994458956")
        .documentType(DocumentType.DNI.name())
        .clientType(ClientType.PERSONAL.name())
        .clientProfile(ClientProfile.REGULAR.name())
        .status(false).build();

    webTestClient
        .post()
        .uri(CLIENT_URL)
        .bodyValue(createClient)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(ClientDto.class)
        .consumeWith(clientDtoEntityExchangeResult -> {
          var clientDto = clientDtoEntityExchangeResult.getResponseBody();
          assert clientDto != null;
          assertEquals("Juana Maria", clientDto.getFirstNameBusiness());
        });
  }

  @Test
  void updateClient() {
    var id = "001";

    ClientDto updatedClient = ClientDto.builder().firstNameBusiness("Luis Jorge")
        .surnames("Cueva Basso").documentNumber("00000015").email("juana.lopez@gmail.com")
        .address("Jr. Eucaliptos 000").phoneNumber("994458956")
        .documentType(DocumentType.DNI.name())
        .clientType(ClientType.PERSONAL.name())
        .clientProfile(ClientProfile.REGULAR.name())
        .status(true).build();

    webTestClient
        .put()
        .uri(CLIENT_URL + "/{id}", id)
        .bodyValue(updatedClient)
        .exchange()
        .expectStatus()
        .is2xxSuccessful()
        .expectBody(ClientDto.class)
        .consumeWith(clientDtoEntityExchangeResult -> {
          var clientDto = clientDtoEntityExchangeResult.getResponseBody();
          assert clientDto != null;
          assertEquals("Luis Jorge", clientDto.getFirstNameBusiness());
        });
  }

  @Test
  void deleteClientById() {
    var id = "001";

    webTestClient
        .delete()
        .uri(CLIENT_URL + "/{id}", id)
        .exchange()
        .expectStatus()
        .isNoContent();
  }

}
