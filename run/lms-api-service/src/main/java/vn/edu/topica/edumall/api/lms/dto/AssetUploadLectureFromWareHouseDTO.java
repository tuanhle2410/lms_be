package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.enumtype.AssetEnum;

import javax.validation.constraints.NotNull;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@LoggingDTO
public class AssetUploadLectureFromWareHouseDTO {

    @NotNull
    private long lectureId;

    @NotNull
    private long courseId;

    @NotNull
    private long fileId;

    @NotNull
    private AssetEnum assetType;
}
