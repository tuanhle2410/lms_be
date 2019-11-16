package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class AssetDetailForApproveDTO extends AssetDetailDTO {
    @JsonProperty("lecture_id")
    Long lectureId;
}
