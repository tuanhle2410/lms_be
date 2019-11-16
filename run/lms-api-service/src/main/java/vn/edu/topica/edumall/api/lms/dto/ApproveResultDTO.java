package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.enumtype.ApproveStatusEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class ApproveResultDTO {

    @JsonProperty("approve_status")
    ApproveStatusEnum approveStatusEnum;

    @JsonProperty("approve_result_link")
    String linkApproveResult;

    @JsonProperty("course_code")
    String courseCode;
}
