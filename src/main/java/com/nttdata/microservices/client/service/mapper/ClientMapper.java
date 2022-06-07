package com.nttdata.microservices.client.service.mapper;

import org.mapstruct.Mapper;

import com.nttdata.microservices.client.entity.Client;
import com.nttdata.microservices.client.service.dto.ClientDto;
import com.nttdata.microservices.client.service.mapper.base.EntityMapper;

@Mapper(componentModel = "spring")
public interface ClientMapper extends EntityMapper<ClientDto, Client> {
}
