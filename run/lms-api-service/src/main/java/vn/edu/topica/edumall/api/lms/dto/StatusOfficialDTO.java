package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.data.enumtype.CourseVersionStatusOfficialEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatusOfficialDTO {
    Long courseVersionId;
    CourseVersionStatusOfficialEnum status;
}
