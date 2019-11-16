package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@LoggingDTO
public class SubCategoryForApproveDTO extends CategoryForApproveDTO {
    private CategoryForApproveDTO parentCategory;
}
