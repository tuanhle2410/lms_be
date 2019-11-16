package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@LoggingDTO
public class CourseForApproveDTO {

    @JsonProperty("course_version")
    CourseDetailForApproveDTO courseDetailDTO;

    List<ChapterDTO> chapters;

    @JsonProperty("assets")
    List<AssetDetailForApproveDTO> assetDetailDTO;

    TeacherDTO teacher;
}
