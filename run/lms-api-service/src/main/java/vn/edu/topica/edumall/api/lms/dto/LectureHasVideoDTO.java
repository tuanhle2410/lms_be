package vn.edu.topica.edumall.api.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class LectureHasVideoDTO {

    private Long id;

    private String title;

    private Integer lectureOrder;

    private Long createdBy;

    private boolean hasVideo;
}
