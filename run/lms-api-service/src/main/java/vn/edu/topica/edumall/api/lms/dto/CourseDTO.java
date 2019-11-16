package vn.edu.topica.edumall.api.lms.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class CourseDTO {

    @NotEmpty(message = "{course.name.notnull}")
    @Size(max = 70)
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

    @NotNull(message = "{course.step.notnull}")
    private Long step;

    @JsonProperty("thumbnailImage")
    private String thumbnailImg;
}
