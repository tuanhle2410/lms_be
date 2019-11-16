package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class UpdateLectureDTO {
    @Size(max = 70, message = "Lecture title length less than 70")
    private String title;

    private int lectureOrder;
    private long chapterId;
}
