package com.nttdata.microservices.util;

import com.nttdata.microservices.dto.ClientDto;
import com.nttdata.microservices.entity.Client;
import com.nttdata.microservices.entity.ClientType;
import com.nttdata.microservices.entity.DocumentType;
import org.springframework.beans.BeanUtils;

public class EntityDtoUtil {

    public static ClientDto toDto(Client client) {
        ClientDto dto = new ClientDto();
        BeanUtils.copyProperties(client, dto);
        dto.setDocumentType(client.getDocumentType().name());
        dto.setCustomerType(client.getClientType().name());
        return dto;
    }

    public static Client toEntity(ClientDto dto) {
        Client client = new Client();
        BeanUtils.copyProperties(dto, client);
        client.setDocumentType(DocumentType.valueOf(dto.getDocumentType()));
        client.setClientType(ClientType.valueOf(dto.getCustomerType()));
        return client;
    }
}
