package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class FileExistDTO {
    String name;
    Long id;
}
