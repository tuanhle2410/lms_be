package vn.edu.topica.edumall.api.lms.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.model.SubCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class CategoryDTO {


    private long id;

    private String name;

    private String alias;

    private String url;

    @JsonIgnoreProperties(value={"courseVersions", "parentCategory"})
    private List<SubCategory> subCategories;
}
