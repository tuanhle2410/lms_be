package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@LoggingDTO
public class DuplicatedFileAndFolderDTO {
    @JsonProperty("list_file_name")
    @NotEmpty(message = "Filename can be not empty")
    private List<String> listFilename;

    @JsonProperty("folder_id")
    @NotEmpty(message = "Folder id can be not empty")
    private String folderId;
}
