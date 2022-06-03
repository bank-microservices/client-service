package com.nttdata.microservices.client.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;


@Data
@NoArgsConstructor
public class ClientDto {
    private String id;
    @NotBlank(message = "customer.firstNameBusiness must be present")
    private String firstNameBusiness;
    @NotBlank(message = "customer.surnames must be present")
    private String surnames;
    @NotBlank(message = "customer.documentNumber must be present")
    private String documentNumber;
    private String address;
    private String email;
    private String phoneNumber;
    private String documentType;
    private String customerType;
    private Date createAt;
    private Boolean status;

    public ClientDto(String firstNameBusiness, String surnames, String documentNumber, String address, String email, String phoneNumber, String documentType, String customerType, Date createAt, Boolean status) {
        this.firstNameBusiness = firstNameBusiness;
        this.surnames = surnames;
        this.documentNumber = documentNumber;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.documentType = documentType;
        this.customerType = customerType;
        this.createAt = createAt;
        this.status = status;
    }
}
