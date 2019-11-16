package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.model.Chapter;
import vn.edu.topica.edumall.data.model.Lecture;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class LectureDTO {
    private long id;
    private String title;
    private int lectureOrder;
    private Long createdBy;

    @JsonIgnoreProperties(value={"courseVersion", "quizzes", "lectures"})
    private Chapter chapter;
}
