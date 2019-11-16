package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.enumtype.FileStatusEnum;
import vn.edu.topica.edumall.data.enumtype.FileTypeEnum;
import vn.edu.topica.edumall.data.enumtype.UploadFileStatusEnum;
import vn.edu.topica.edumall.data.model.Folder;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class FileDetailDTO {

    private long id;

    private FileTypeEnum fileType;

    private String name;

    private String url;

    private FileStatusEnum status;

    private String objectKey;

    private String fileExtension;

    private Long fileSize;

    private Date deletedAt;

    private Long duration;

    private Long createdBy;

    private UploadFileStatusEnum uploadStatus;

    @JsonIgnoreProperties(value = {"files", "parentFolder", "childFolders", "course"})
    private Folder folder;
}
