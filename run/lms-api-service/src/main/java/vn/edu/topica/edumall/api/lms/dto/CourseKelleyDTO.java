package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.data.enumtype.CourseRunMarketingEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CourseKelleyDTO {
    String code;
    CourseRunMarketingEnum isRunMarketing;
    Double price;
}

