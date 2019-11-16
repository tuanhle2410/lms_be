package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.data.enumtype.ApproveStatusEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class MediaToolResultDTO {
    private ApproveStatusEnum videoFrameWidth;

    private ApproveStatusEnum videoFrameHeight;

    private ApproveStatusEnum videoBitRate;

    private ApproveStatusEnum videoFrameRate;

    private ApproveStatusEnum videoCodeName;

    private ApproveStatusEnum status;

    private ApproveStatusEnum videoFormat;

    private ApproveStatusEnum audioBitRate;

    private ApproveStatusEnum audioChannel;

    private String detailResult;

    private Long fileId;
}
