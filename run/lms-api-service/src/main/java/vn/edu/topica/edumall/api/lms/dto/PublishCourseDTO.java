package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.data.enumtype.CourseRunMarketingEnum;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublishCourseDTO {

    @NotNull(message = "{courseVersion.id.notnull}")
    Long courseVersionId;

    @NotNull(message = "{courseVersion.price.notnull}")
    Double price;

    @NotNull(message = "{courseVersion.isRunMarketing}")
    CourseRunMarketingEnum isRunMarketing;
}
