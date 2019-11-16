package vn.edu.topica.edumall.security.core.filter;

import org.springframework.security.core.Authentication;


public interface TokenProvider {

    Authentication getAuthentication(String token);
}
