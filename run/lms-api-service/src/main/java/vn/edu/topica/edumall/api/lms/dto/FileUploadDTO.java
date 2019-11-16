package vn.edu.topica.edumall.api.lms.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadDTO {

    private List<MultipartFile> listFile;

    @NotEmpty
    private String folderId;

    long duration;
}
