package vn.edu.topica.edumall.security.config.auto;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.util.Assert;

import vn.edu.topica.edumall.security.core.model.UserPrincipal;
import vn.edu.topica.edumall.security.core.payload.UserToken;
import vn.edu.topica.edumall.security.jdbc.provider.AbstractTokenProvider;

/**
 * This class to define default token provider storage token use database
 * 
 * @author trungnt9
 *
 */
public class DefaultTokenProvider extends AbstractTokenProvider implements InitializingBean {

	private UserDetailsService userDetailServices;

	private DefaultTokenServices defaultTokenServices;

	public DefaultTokenProvider(UserDetailsService userDetailServices, DefaultTokenServices defaultTokenServices,
			TokenStore tokenStore) {
		super(tokenStore);
		this.userDetailServices = userDetailServices;
		this.defaultTokenServices = defaultTokenServices;
	}

	/**
	 * @param authentication
	 * @return
	 */
	@Override
	public UserToken generateToken(Authentication authentication) {
		String emailOrUsername = (String) authentication.getPrincipal();
		return generateTokenWithoutCredential(emailOrUsername);
	}

	@Override
	public UserToken generateTokenWithoutCredential(String emailOrUsername) {
		UserPrincipal userPrincipal = loadByUsernameOrEmail(emailOrUsername);
		OAuth2AccessToken oauth2AccessToken = buildAccessToken(buildAuthentication(userPrincipal.getUsername()));
		return toUserToken(oauth2AccessToken, userPrincipal);
	}

	/**
	 * @param oauth2Authen
	 * @return
	 */
	private OAuth2AccessToken buildAccessToken(OAuth2Authentication oauth2Authen) {
		return defaultTokenServices.createAccessToken(oauth2Authen);
	}

	@Override
	protected UserPrincipal loadByUsernameOrEmail(String usernameOrEmail) {
		return (UserPrincipal) userDetailServices.loadUserByUsername(usernameOrEmail);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(tokenStore, "tokenStore must be set");
		Assert.notNull(userDetailServices, "userDetailServices must be set");
		Assert.notNull(defaultTokenServices, "defaultTokenServices must be set");
	}

}
