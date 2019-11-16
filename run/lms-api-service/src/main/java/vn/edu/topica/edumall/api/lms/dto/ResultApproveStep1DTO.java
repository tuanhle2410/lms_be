package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.enumtype.ApproveStatusEnum;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class ResultApproveStep1DTO {
    @JsonProperty("chapters")
    List<ResultApproveChapterDTO> chapterDTOList;

    private ApproveStatusEnum frameWidth;

    private ApproveStatusEnum frameHeight;

    private ApproveStatusEnum bitRate;

    private ApproveStatusEnum frameRate;

    private ApproveStatusEnum codeName;

    private ApproveStatusEnum format;

    private ApproveStatusEnum audioBitRate;

    private ApproveStatusEnum audioChannel;

    @JsonProperty("thumbnailImg")
    private String courseVersionThumbnailImg;

    @JsonProperty("courseVersionName")
    private String courseVersionName;
}
