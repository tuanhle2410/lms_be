package vn.edu.topica.edumall.api.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class CategoryForApproveDTO {

    private long id;

    private String name;
}
