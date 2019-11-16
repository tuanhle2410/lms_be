package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class CourseDetailDTO extends CourseDTO {

    private Long id;

    private Long courseVersionId;

    private Long courseFolderId;

    private Long chapterDefaultId;

    @JsonProperty("subcategories")
    private List<SubCategoryDTO> subCategoryDTOList;
}
