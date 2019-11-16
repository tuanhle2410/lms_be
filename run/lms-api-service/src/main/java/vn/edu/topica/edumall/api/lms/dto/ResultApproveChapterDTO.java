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
public class ResultApproveChapterDTO {

    @JsonProperty("id")
    Long chapterId;

    String title;

    @JsonProperty("lectures")
    List<ResultApproveLectureDTO> lectureDTO;

}
