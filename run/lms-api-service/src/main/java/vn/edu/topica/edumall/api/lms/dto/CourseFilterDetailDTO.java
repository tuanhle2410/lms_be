package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.enumtype.ApproveStatusEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class CourseFilterDetailDTO {
    Long currentStep;

    ApproveStatusEnum approveStatus;

    CourseDetailDTO dataStep1;

    ChapterAndLectureDTO dataStep2;
}
