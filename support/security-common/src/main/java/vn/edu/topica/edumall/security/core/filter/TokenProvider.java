package vn.edu.topica.edumall.security.core.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;

import vn.edu.topica.edumall.security.core.model.UserPrincipal;
import vn.edu.topica.edumall.security.core.payload.UserToken;

public interface TokenProvider {

	/**
	 * @param emailOrUsername
	 * @return
	 */
	UserToken generateTokenWithoutCredential(String emailOrUsername);

	/**
	 * @param authentication
	 * @return
	 */
	UserToken generateToken(Authentication authentication);

	/**
	 * @param request
	 * @return
	 */
	Authentication getAuthentication(String token);

	/**
	 * @param username
	 * @return
	 */
	default OAuth2Authentication buildAuthentication(String username) {
		HashMap<String, String> authorizationParameters = new HashMap<>();
		authorizationParameters.put("scope", "read");
		authorizationParameters.put("username", "user");
		authorizationParameters.put("client_id", "client_id");
		authorizationParameters.put("grant", "password");

		Set<GrantedAuthority> authorities = new HashSet<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_USER"));

		Set<String> responseType = new HashSet<>();
		responseType.add("password");

		Set<String> scopes = new HashSet<>();
		scopes.add("read");
		scopes.add("write");

		OAuth2Request authorizationRequest = new OAuth2Request(authorizationParameters, "Client_Id", authorities, true,
				scopes, null, "", responseType, null);

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);

		OAuth2Authentication authenticationRequest = new OAuth2Authentication(authorizationRequest,
				authenticationToken);
		authenticationRequest.setAuthenticated(true);

		return authenticationRequest;
	}

	/**
	 * @param oAuth2AccessToken
	 * @param user
	 * @return
	 */
	default UserToken toUserToken(OAuth2AccessToken oAuth2AccessToken, UserPrincipal user) {
		if (oAuth2AccessToken == null || user == null) {
			return null;
		}
		List<String> listRole = new ArrayList<String>();
		if (user.getAuthorities() != null) {
			listRole.addAll(user.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList()));
		}
		UserToken userToken = UserToken.builder().accessToken(oAuth2AccessToken.getValue())
				.refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
				.expiresIn(oAuth2AccessToken.getExpiresIn()).avatar(user.getAvatar()).username(user.getEmail())
				.name(user.getName()).listRole(listRole).build();

		return userToken;
	}
}
