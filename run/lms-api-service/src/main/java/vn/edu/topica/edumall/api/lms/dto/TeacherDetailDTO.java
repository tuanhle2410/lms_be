package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.util.List;

@Setter
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@LoggingDTO
public class TeacherDetailDTO {

    String avatar;

    @JsonProperty("code")
    String teacherCode;

    String type;

    List<String> profile;

    @JsonProperty("created_at")
    String takePartInDate;

    String fullName;

    String gender;

    String phone;

    @JsonProperty("date_of_birth")
    String dateOfBirth;

    String address;

    String email;

    @JsonProperty("identification_number")
    String identificationNumber;

    @JsonProperty("identification_date_receive")
    String licenceDate;

    @JsonProperty("identification_place_receive")
    String licenceAddress;

    @JsonProperty("tax_identification_number")
    String taxNumber;

    @JsonProperty("identification_image_1")
    String identificationImage1;

    @JsonProperty("identification_image_2")
    String identificationImage2;

    @JsonProperty("identification_image_3")
    String identificationImage3;

    @JsonProperty("bank_name")
    String bankName;

    @JsonProperty("bank_account_number")
    String bankAccountNumber;

    @JsonProperty("bank_account_branch")
    String branch;

    @JsonProperty("bank_account_name")
    String bankAccountName;

    @JsonProperty("company_name")
    String companyName;

    String representative;

    @JsonProperty("representative_level")
    String representativeLevel;

    @JsonProperty("contact_name")
    String contactName;

    @JsonProperty("contact_email")
    String contactEmail;
}
