package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class ObjectDeletedDTO {
    Boolean status;
}
