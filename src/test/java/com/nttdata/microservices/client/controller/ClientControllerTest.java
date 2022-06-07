package com.nttdata.microservices.client.controller;

import com.nttdata.microservices.client.service.dto.ClientDto;
import com.nttdata.microservices.client.entity.ClientType;
import com.nttdata.microservices.client.entity.Client;
import com.nttdata.microservices.client.entity.DocumentType;
import com.nttdata.microservices.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class ClientControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ClientRepository clientRepository;

    static String CLIENT_URL = "/api/client";

    @BeforeEach
    void setUp() {
        List<Client> clients = List.of(new Client("001", "Luis Gustavo", "Cueva Basso", "00000015", "Jr. Eucaliptos 493", "lucuba06@gmail.com", "994451236", DocumentType.DNI, ClientType.PERSONAL, new Date(), true),
                new Client("Carlos", "Zarate Gomes", "00000016", "czarate@gmail.com", "Av. Brasil 1345", "994451237", DocumentType.RUC, ClientType.BUSINESS, new Date(), false));
        clientRepository
                .deleteAll()
                .thenMany(clientRepository.saveAll(clients))
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
                .uri(CLIENT_URL + "/documentNumber/{number}", number)
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

        var createClient = new ClientDto("Juana Maria", "Lopez Garcia", "00000020", "Jr. Molina 500", "juana.lopez@gmail.com", "994458956", "CE", "PERSONAL", new Date(), true);

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
        var updatedClient = new ClientDto("Luis Jorge", "Cueva Basso", "00000015", "Jr. Eucaliptos 000", "lucuba06@gmail.com", "994451236", "DNI", "PERSONAL", new Date(), true);

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
