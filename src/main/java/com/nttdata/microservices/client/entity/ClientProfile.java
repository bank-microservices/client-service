package com.nttdata.microservices.client.entity;

import java.util.Arrays;

public enum ClientProfile {

  REGULAR, VIP, PYME;

  public boolean in(ClientProfile... clientProfiles) {
    return Arrays.stream(clientProfiles).anyMatch(profile -> profile == this);
  }
}
