package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@LoggingDTO
public class CreateChapterDTO {
    private String title;

    @NotNull(message = "Course version id is required")
    private long courseVersionId;
}
