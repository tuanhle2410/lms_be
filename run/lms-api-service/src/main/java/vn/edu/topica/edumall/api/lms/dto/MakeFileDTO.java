package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.io.File;

@Slf4j
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@LoggingDTO
public class MakeFileDTO {
    File file;
    Long duration;
}
