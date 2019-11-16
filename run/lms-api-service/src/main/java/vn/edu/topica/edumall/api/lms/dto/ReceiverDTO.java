package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceiverDTO {
    private String name;
    private String email;

    @JsonProperty("Sender_Name")
    private String senderName;

    @JsonProperty("Sender_Address")
    private String senderAddress;

    @JsonProperty("Sender_City")
    private String senderCity;

    @JsonProperty("Sender_State")
    private String senderState;

    @JsonProperty("Sender_Zip")
    private String senderZip;

    private String error;

    private String courseCode;

    private String courseLink;

    private String teacherName;

    private String teacherCode;
}
