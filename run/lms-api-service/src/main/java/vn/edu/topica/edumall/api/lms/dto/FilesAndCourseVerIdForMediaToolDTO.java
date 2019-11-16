package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.data.model.File;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class FilesAndCourseVerIdForMediaToolDTO {
    List<File> fileList;
    Long courseVersionId;
}
