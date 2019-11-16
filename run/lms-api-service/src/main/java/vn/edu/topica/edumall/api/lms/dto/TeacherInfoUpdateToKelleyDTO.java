package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;


import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@LoggingDTO
public class TeacherInfoUpdateToKelleyDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String avatar;

    @JsonProperty("teacher_code")
    private String teacherCode;

    @JsonProperty("company_name")
    private String companyName;

    private String representative;

    @JsonProperty("representative_level")
    private String representativeLevel;

    @JsonProperty("full_name")
    private String fullName;

    private String gender;

    @JsonProperty("contact_name")
    private String contactName;

    @JsonProperty("phone_number")
    private String phone;

    @JsonProperty("date_of_birth")
    private String dateOfBirth;

    private String address;

    @JsonProperty("old_email")
    private String oldEmail;

    private String email;

    @JsonProperty("identification_number")
    private String identificationNumber;

    @JsonProperty("identification_date_receive")
    private String licenceDate;

    @JsonProperty("identification_place_receive")
    private String licenceAddress;

    @JsonProperty("tax_identification_number")
    private String taxNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("identification_image_1")
    private String identificationFrontImagePath;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("identification_image_2")
    private String identificationBehindImagePath;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("identification_image_3")
    private String identificationImage3Path;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("bank_account_branch")
    private String branch;

    @JsonProperty("bank_account_number")
    private String bankAccountNumber;

    @JsonProperty("bank_account_name")
    private String bankAccountName;

    private String profile;

    @JsonProperty("contact_email")
    private String contactEmail;
}
