package vn.edu.topica.edumall.security.jwt;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import vn.edu.topica.edumall.api.rest.exception.ApiCodeException;
import vn.edu.topica.edumall.security.core.filter.TokenProvider;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;
import vn.edu.topica.edumall.security.core.payload.UserToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@Profile({"jwt_security"})
public class JwtTokenProviderImpl implements TokenProvider {

  // Secret JWT that only is known by server side
  @Value("${authen.jwtSecret}")
  private  String jwtSecret;

  // expired time of token
  @Value("${authen.jwt.expiredTime}")
  private long jwtExpiration ;

  @Qualifier("lmsUserDetailService")
  @Autowired
  UserDetailsService userDetailsService;

  @Override
  public UserToken generateTokenWithoutCredential(String emailOrUsername) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpiration);
    UserPrincipal userPrincipal =
        (UserPrincipal) userDetailsService.loadUserByUsername(emailOrUsername);

    String jwt =
        Jwts.builder()
            .setSubject(userPrincipal.getEmail())
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact();

    List<String> listRole = new ArrayList<String>();
    if (userPrincipal.getAuthorities() != null) {
      listRole.addAll(
          userPrincipal.getAuthorities().stream()
              .map(e -> e.getAuthority())
              .collect(Collectors.toList()));
    }

    UserToken userToken =
        UserToken.builder()
            .accessToken(jwt)
            .refreshToken("")
            .expiresIn((int) jwtExpiration)
            .avatar(userPrincipal.getAvatar())
            .username(userPrincipal.getEmail())
            .name(userPrincipal.getName())
            .listRole(listRole)
            .build();

    log.info("userToken: {}",userToken);

    return userToken;
  }

  @Override
  public UserToken generateToken(Authentication authentication) {
    String emailOrUsername = (String) authentication.getPrincipal();
    return generateTokenWithoutCredential(emailOrUsername);
  }

  @Override
  public Authentication getAuthentication(String token) {
    try {
      if (!StringUtils.isBlank(token) && validateToken(token)) {
        String email = getEmailFromJWT(token);
        UserPrincipal userDetail =
            (UserPrincipal) userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(
            userDetail.getEmail(), null, userDetail.getAuthorities());
      } else {
        log.warn("(Cant not authentication from token {} because null or empty", token);
      }
    } catch (Exception e) {
      throw new ApiCodeException("Error when get authenticate for token " + token, e);
    }
    return null;
  }

  public  String getEmailFromJWT(String token) {
    Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    return claims.getSubject();
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
      return true;
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.error("JWT claims string is empty.");
    }
    return false;
  }
}
