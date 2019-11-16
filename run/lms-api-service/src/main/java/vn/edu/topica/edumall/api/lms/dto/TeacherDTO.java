package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@LoggingDTO
public class TeacherDTO {
    private long id;

    private String academicRank;

    private String major;

    private String function;

    private String workUnit;

    private String shortDescription;

    private String longDescription;

    private String email;
}
