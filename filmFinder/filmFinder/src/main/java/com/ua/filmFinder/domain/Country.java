package com.ua.filmFinder.domain;

import org.springframework.security.core.GrantedAuthority;

public enum Country implements GrantedAuthority {
  USA, RUSSIA, FRANCE, GB, GERMANY, CANADA, JAPAN;

  @Override
  public String getAuthority() {
    return name();
  }
}
