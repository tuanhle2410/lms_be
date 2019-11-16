package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.enumtype.AssetEnum;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AssetUploadLectureDTO {

    @NotNull
    private Long lectureId;

    @NotNull
    private Long courseId;

    @NotNull
    private MultipartFile file;

    @NotNull
    private AssetEnum assetType;

    private long duration;
}
