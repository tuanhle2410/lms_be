package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class ExistObjectDTO {
    private String message;
    private Boolean isExist;
    private List<FileExistDTO> files;
}
