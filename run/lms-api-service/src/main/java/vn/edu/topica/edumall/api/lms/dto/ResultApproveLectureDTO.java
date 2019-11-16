package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class ResultApproveLectureDTO {

    @JsonProperty("id")
    Long lectureId;

    String title;

    @JsonProperty("media_tool_result")
    ResultApproveMediaToolDTO mediaToolResultDTO;
}
