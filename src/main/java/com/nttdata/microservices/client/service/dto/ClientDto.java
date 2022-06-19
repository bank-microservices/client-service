package com.nttdata.microservices.client.service.dto;

import com.nttdata.microservices.client.entity.ClientProfile;
import com.nttdata.microservices.client.entity.ClientType;
import com.nttdata.microservices.client.entity.DocumentType;
import com.nttdata.microservices.client.util.validation.ValueOfEnum;
import java.util.Date;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientDto {

  private String id;
  @NotBlank(message = "customer.firstNameBusiness  is required")
  private String firstNameBusiness;

  @NotBlank(message = "customer.surnames  is required")
  private String surnames;

  @NotBlank(message = "customer.documentNumber  is required")
  private String documentNumber;

  private String address;

  @Email
  private String email;

  private String phoneNumber;

  @NotBlank(message = "documentType is required")
  @ValueOfEnum(enumClass = DocumentType.class, message = "documentType is invalid value")
  private String documentType;

  @NotBlank(message = "clientProfile is required")
  @ValueOfEnum(enumClass = ClientProfile.class, message = "clientProfile is invalid value")
  private String clientProfile;

  @NotBlank(message = "clientType is required")
  @ValueOfEnum(enumClass = ClientType.class, message = "clientType is invalid value")
  private String clientType;

  private Date createAt;
  private Boolean status;

  public ClientDto(String firstNameBusiness, String surnames, String documentNumber, String address,
                   String email, String phoneNumber, String documentType, String clientType,
                   Date createAt, Boolean status) {
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
