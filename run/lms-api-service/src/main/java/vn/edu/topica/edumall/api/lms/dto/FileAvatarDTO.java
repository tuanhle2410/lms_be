package vn.edu.topica.edumall.api.lms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@LoggingDTO
public class FileAvatarDTO {
    MultipartFile file;
}
