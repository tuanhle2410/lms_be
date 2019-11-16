package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.model.Category;
import vn.edu.topica.edumall.data.model.SubCategory;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class SubCategoryDTO {


    private long id;

    private String name;

    private String aliasName;

    @JsonIgnoreProperties("subCategories")
    private Category parentCategory;
}
