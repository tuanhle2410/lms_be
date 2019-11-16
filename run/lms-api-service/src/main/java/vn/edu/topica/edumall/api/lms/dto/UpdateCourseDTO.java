package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.data.enumtype.CourseRunMarketingEnum;
import vn.edu.topica.edumall.data.enumtype.CourseStatusEnum;
import vn.edu.topica.edumall.data.model.CourseVersion;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCourseDTO {

    private CourseStatusEnum status;

    private Date publishedAt;

    private CourseVersion courseVersion;

    private Double price;

    private CourseRunMarketingEnum isRunMarketing;
}
