package vn.edu.topica.edumall.api.lms.dto;

import lombok.Data;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@LoggingDTO
public class ItemNameDTO {

    @NotBlank( message = "Name field can not be empty")
    @Size(max=300)
    private String name;
}
