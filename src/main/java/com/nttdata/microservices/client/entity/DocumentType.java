package com.nttdata.microservices.client.entity;

import java.util.Arrays;

public enum DocumentType {

  DNI, RUC, CE;

  public boolean in(DocumentType... documentTypes) {
    return Arrays.stream(documentTypes).anyMatch(type -> type == this);
  }

}
