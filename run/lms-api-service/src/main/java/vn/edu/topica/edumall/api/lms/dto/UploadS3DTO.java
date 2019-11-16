package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@LoggingDTO
public class UploadS3DTO {
    String bucketName;
    String s3FileName;
    MultipartFile multipartFile;
    Long fileId;
}
