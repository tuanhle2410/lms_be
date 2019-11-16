package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChapterForMigrateDTO {

    String title;

    @JsonIgnoreProperties(value = {"chapter", "questions"})
    private List<QuizDTO> quizzes;

    private List<LectureForMigrateDTO> lectures;
}
