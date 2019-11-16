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
public class ResultApproveMediaToolDTO {

    private long id;

    @JsonProperty("frameWidth")
    private ApproveStatusEnum videoFrameWidth;

    @JsonProperty("frameHeight")
    private ApproveStatusEnum videoFrameHeight;

    @JsonProperty("bitRate")
    private ApproveStatusEnum videoBitRate;

    @JsonProperty("frameRate")
    private ApproveStatusEnum videoFrameRate;

    @JsonProperty("codeName")
    private ApproveStatusEnum videoCodeName;

    private ApproveStatusEnum status;

    @JsonProperty("format")
    private ApproveStatusEnum videoFormat;

    private ApproveStatusEnum audioBitRate;

    private ApproveStatusEnum audioChannel;

    private String detailResult;
}






