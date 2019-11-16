package vn.edu.topica.edumall.security.core.payload;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;

@Validated
@Builder
@Data
public class UserToken {
	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("refresh_token")
	private String refreshToken;

	@JsonProperty("roles")
	private List<String> listRole;

	@JsonProperty("name")
	private String name;

	@JsonProperty("username")
	private String username;

	@JsonProperty("expires_in")
	private Integer expiresIn;

	@JsonProperty("avatar")
	private String avatar;

}
