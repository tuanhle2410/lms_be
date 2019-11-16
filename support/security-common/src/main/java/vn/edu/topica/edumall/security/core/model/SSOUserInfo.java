package vn.edu.topica.edumall.security.core.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SSOUserInfo {

    @JsonProperty("id")
    private String userId;

    private String name;

    private String avatar;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String email;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("is_social_account")
    private String socialAccount;

    @JsonProperty("verified")
    private String verified;
}
