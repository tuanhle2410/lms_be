package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.data.enumtype.FileTypeEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FileForMigrateDTO {
    
    private FileTypeEnum fileType;

    private String name;

    private String url;

    private String objectKey;

    private String fileExtension;

    private Long fileSize;

    private Long duration;
}
