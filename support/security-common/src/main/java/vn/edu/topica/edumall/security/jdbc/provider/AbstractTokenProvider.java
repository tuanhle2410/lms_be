package vn.edu.topica.edumall.security.jdbc.provider;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;

import lombok.extern.slf4j.Slf4j;
import vn.edu.topica.edumall.api.rest.exception.ApiCodeException;
import vn.edu.topica.edumall.security.core.filter.TokenProvider;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

/**
 * @author trungnt9
 *
 */
@Slf4j
public abstract class AbstractTokenProvider implements TokenProvider {

	protected TokenStore tokenStore;

	public AbstractTokenProvider(TokenStore tokenStore) {
		this.tokenStore = tokenStore;
	}

	/**
	 * @param request
	 * @return
	 */
	@Override
	public Authentication getAuthentication(String token) {
		try {
			if (!StringUtils.isBlank(token)) {
				log.info("(getAuthentication)token in cookie {}", token);
				Authentication authentication = tokenStore.readAuthentication(token);
				log.info("(getAuthentication)authentication: {}", authentication);
				if (authentication != null && authentication.isAuthenticated()) {
					String usernameOrEmail = authentication.getName();
					UserPrincipal userDetail = loadByUsernameOrEmail(usernameOrEmail);
					return new UsernamePasswordAuthenticationToken(userDetail.getEmail(), null,
							userDetail.getAuthorities());
				}
			} else {
				log.warn("(Cant not authentication from token {} because null or empty", token);
			}
		} catch (Exception e) {
			throw new ApiCodeException("Error when get authenticate for token " + token, e);
		}
		return null;
	}

	protected abstract UserPrincipal loadByUsernameOrEmail(String usernameOrEmail);
}
