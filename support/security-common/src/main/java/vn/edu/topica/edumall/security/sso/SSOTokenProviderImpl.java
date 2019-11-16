package vn.edu.topica.edumall.security.sso;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import vn.edu.topica.edumall.data.model.User;
import vn.edu.topica.edumall.security.core.filter.TokenProvider;
import vn.edu.topica.edumall.security.core.model.SSOUserInfo;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;
import vn.edu.topica.edumall.security.core.payload.UserToken;
import vn.edu.topica.edumall.security.core.service.LmsUserService;
import vn.edu.topica.edumall.security.jdbc.repository.UserRepository;

import java.io.IOException;
import java.net.URISyntaxException;

@Slf4j
@Component
@Profile({"sso_security"})
public class SSOTokenProviderImpl implements TokenProvider {

    @Autowired
    @Qualifier("lmsUserDetailService")
    UserDetailsService userDetailsService;

    @Value("${sso.url.get-user-info}")
    String ssoUrlGetUserInfo;

    @Autowired
    LmsUserService lmsUserService;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserToken generateTokenWithoutCredential(String emailOrUsername) {
        return null;
    }

    @Override
    public UserToken generateToken(Authentication authentication) {
        return null;
    }

    @Override
    public Authentication getAuthentication(String token) {
        if (StringUtils.isBlank(token)) {
            log.warn("(Can not authentication from token {} because null or empty", token);
            return null;
        }

        SSOUserInfo ssoUserInfo = getSSOUserInfoFromSSOToken(token);
        if (ssoUserInfo == null) {
            log.warn("Can not get sso user info from sso_token {}", token);
            return null;
        }
        String email = ssoUserInfo.getEmail();
        if (email == null) {
            log.warn("Can not get username from sso_token {}", token);
            return null;
        }

        User userFind = userRepository.findByUsernameOrEmail(ssoUserInfo.getEmail(), ssoUserInfo.getEmail()).orElse(null);
        if (userFind == null) {
            lmsUserService.createTeacher(ssoUserInfo, userFind);
        } else {
            lmsUserService.updateUserInfo(ssoUserInfo, userFind);
        }

        UserPrincipal userDetail = (UserPrincipal) userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetail.getEmail(), userDetail,
                userDetail.getAuthorities());
    }

    private SSOUserInfo getSSOUserInfoFromSSOToken(String token) {
        try {
            URIBuilder uriBuilder = new URIBuilder(ssoUrlGetUserInfo);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet clientRequest = new HttpGet(uriBuilder.build());
            clientRequest.addHeader("Authorization", token);
            HttpResponse httpResponse = httpClient.execute(clientRequest);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                String userInfoStr = EntityUtils.toString(httpResponse.getEntity());
                JSONObject objJson = new JSONObject(userInfoStr);
                JSONObject userInfoJson = objJson.getJSONObject("user_info");
                ObjectMapper objectMapper = new ObjectMapper();
                SSOUserInfo ssoUserInfo = objectMapper.readValue(userInfoJson.toString(), SSOUserInfo.class);
                return ssoUserInfo;
            }
        } catch (URISyntaxException e) {
            log.error("URISyntaxException: ", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
