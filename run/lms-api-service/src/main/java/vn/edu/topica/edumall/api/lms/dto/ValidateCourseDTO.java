package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class ValidateCourseDTO {

    boolean isError;

    Long courseVersionId;

    boolean notHasChapter;

    boolean notEnoughVideoNumber;

    boolean notEnoughVideoDuration;

    @JsonProperty("chapterErrorDetail")
    List<ChapterErrorDetailDTO> chapterErrorDetailDTOList;

    @JsonProperty("lectureErrorDetail")
    List<LectureErrorDetailDTO> lectureErrorDetailDTOList;
}
