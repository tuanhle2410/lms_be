package vn.edu.topica.edumall.api.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserKellyDTO {
    @NotBlank
    private String email;

    private String mobile;

    @NotBlank
    private String name;

    private String username;

    private String avatar;

    @NotBlank
    private String kellyId;
}
