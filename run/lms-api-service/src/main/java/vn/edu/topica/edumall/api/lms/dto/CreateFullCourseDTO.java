package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.data.enumtype.CourseVersionStatusEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFullCourseDTO {
    //    @NotEmpty(message = "{course.name.notnull}")
//    @Size(max = 70)
    private String name;

    @NotEmpty(message = "{course.benefit.notnull}")
    @Size(max = 4500)
    private String benefit;

    @NotEmpty(message = "{course.target.notnull}")
    @Size(max = 4500)
    private String target;

    @NotEmpty(message = "{course.requirement.notnull}")
    @Size(max = 4500)
    private String requirement;

    @Size(max = 500)
    @JsonProperty("shortDes")
    @NotEmpty(message = "{course.shortDes.notnull}")
    private String shortDescription;

    @JsonProperty("longDes")
    private String longDescription;

    private Long step;

    @JsonProperty("thumbnailImage")
    private String thumbnailImg;

    @NotNull(message = "{course.subcategories.notnull}")
    private Long subCategoryId;

    private Long userId;

    private Double price;

    private CourseVersionStatusEnum status;

    private String courseCode;

    @JsonProperty("kelly_id")
    private String kelleyId;

    List<ChapterForMigrateDTO> chapters;
}
