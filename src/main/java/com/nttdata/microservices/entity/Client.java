package com.nttdata.microservices.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
@Validated
public class Client {

    @Id
    private String id;
    private String names;
    private String surnames;
    private String documentNumber;
    private String address;
    private String email;
    private String phoneNumber;
    private DocumentType documentType;
    private ClientType clientType;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;
    private Boolean status;

    public Client(String names, String surnames, String documentNumber, String address, String email, String phoneNumber, DocumentType documentType, ClientType clientType, Date createAt, Boolean status) {
        this.names = names;
        this.surnames = surnames;
        this.documentNumber = documentNumber;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.documentType = documentType;
        this.clientType = clientType;
        this.createAt = createAt;
        this.status = status;
    }
}
