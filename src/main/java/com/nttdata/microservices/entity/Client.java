package com.nttdata.microservices.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "customer")
@Validated
public class Client {

    @Id
    private String id;
    private String firstNameBusiness;
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

    public Client(String firstNameBusiness, String surnames, String documentNumber, String address, String email, String phoneNumber, DocumentType documentType, ClientType clientType, Date createAt, Boolean status) {
        this.firstNameBusiness = firstNameBusiness;
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
