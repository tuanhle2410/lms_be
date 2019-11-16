package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.model.Lecture;
import vn.edu.topica.edumall.data.model.Quiz;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class ChapterDTO {

    private long id;

    private Date createdAt;

    private Date updatedAt;

    private String title;

    private int chapterOrder;

    private Long createdBy;

    @JsonIgnoreProperties(value = {"chapter", "questions"})
    private List<Quiz> quizzes;

    private List<LectureHasVideoDTO> lectures;
}
