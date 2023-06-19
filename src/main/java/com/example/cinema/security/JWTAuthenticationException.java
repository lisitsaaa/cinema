package com.example.cinema.security;

import javax.naming.AuthenticationException;

public class JWTAuthenticationException extends AuthenticationException {

  public JWTAuthenticationException(String msg) {
    super(msg);
  }
}
