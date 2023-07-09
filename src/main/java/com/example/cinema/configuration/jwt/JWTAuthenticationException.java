package com.example.cinema.configuration.jwt;

import javax.naming.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {

  public JWTAuthenticationException(String msg) {
    super(msg);
  }
}
