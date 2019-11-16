package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class TeacherInfoUpdateDTO {

    MultipartFile avatar;

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

    private String email;

    @JsonProperty("identification_number")
    private String identificationNumber;

    @JsonProperty("identification_date_receive")
    private String licenceDate;

    @JsonProperty("identification_place_receive")
    private String licenceAddress;

    @JsonProperty("tax_identification_number")
    private String taxNumber;

    @JsonProperty("identification_image_1")
    private MultipartFile identificationFrontImage;

    @JsonProperty("identification_image_2")
    private MultipartFile identificationBehindImage;

    @JsonProperty("identification_image_3")
    private MultipartFile identificationImage3;

    @JsonProperty("bank_name")
    private String bankName;

    @JsonProperty("bank_account_branch")
    private String branch;

    @JsonProperty("bank_account_number")
    private String bankAccountNumber;

    @JsonProperty("bank_account_name")
    private String bankAccountName;

    @JsonProperty("contact_email")
    private String contactEmail;

    private String profile;

    //map to data from FE
    public void setPhone_number(String phone) {
        setPhone(phone);
    }

    public void setContact_email(String contactEmail) {
        setContactEmail(contactEmail);
    }

    public void setFull_name(String fullName) {
        setFullName(fullName);
    }

    public void setCompany_name(String companyName) {
        setCompanyName(companyName);
    }

    public void setTeacher_code(String teacherCode) {
        setTeacherCode(teacherCode);
    }

    public void setRepresentative_level(String representativeLevel) {
        setRepresentativeLevel(representativeLevel);
    }

    public void setDate_of_birth(String dateOfBirth) {
        setDateOfBirth(dateOfBirth);
    }

    public void setContact_name(String contactName) {
        setContactName(contactName);
    }

    public void setIdentification_number(String identificationNumber) {
        setIdentificationNumber(identificationNumber);
    }

    public void setIdentification_date_receive(String identificationDateReceive) {
        setLicenceDate(identificationDateReceive);
    }

    public void setIdentification_place_receive(String identificationPlaceReceive) {
        setLicenceAddress(identificationPlaceReceive);
    }

    public void setIdentification_image_1(MultipartFile identificationImage1) {
        setIdentificationFrontImage(identificationImage1);
    }

    public void setIdentification_image_2(MultipartFile identificationImage2) {
        setIdentificationBehindImage(identificationImage2);
    }

    public void setIdentification_image_3(MultipartFile identificationImage3) {
        setIdentificationImage3(identificationImage3);
    }

    public void setTax_identification_number(String taxIdentificationNumber) {
        setTaxNumber(taxIdentificationNumber);
    }

    public void setBank_name(String bankName) {
        setBankName(bankName);
    }

    public void setBank_account_branch(String branch) {
        setBranch(branch);
    }

    public void setBank_account_number(String bankAccountNumber) {
        setBankAccountNumber(bankAccountNumber);
    }

    public void setBank_account_name(String bankAccountName) {
        setBankAccountName(bankAccountName);
    }

}
