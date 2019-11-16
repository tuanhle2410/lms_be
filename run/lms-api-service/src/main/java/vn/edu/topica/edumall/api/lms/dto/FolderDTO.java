package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.data.model.Folder;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@LoggingDTO
public class FolderDTO {

    private long id;

    private String name;

    @JsonIgnoreProperties(value={"parentFolder", "files", "warehouse", "childFolders", "course"})
    private Folder parentFolder;

    @JsonIgnoreProperties(value={"folder", "assets"})
    private List<File> files;

    private Boolean hasMore = false;

    @JsonIgnoreProperties(value={"parentFolder", "files", "warehouse", "childFolders", "course"})
    private List<Folder> childFolders;
}
