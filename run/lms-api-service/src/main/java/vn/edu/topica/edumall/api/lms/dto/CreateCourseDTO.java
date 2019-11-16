package vn.edu.topica.edumall.api.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class CreateCourseDTO extends CourseDTO {
    @NotNull(message = "{course.subcategories.notnull}")
    private Long subCategoryId;

    private Long createdBy;
}
