package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.model.Chapter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class ChapterAndLectureDTO {

    Long courseVersionId;

    Long courseFolderId;

    List<ChapterDTO> chapters;
}
