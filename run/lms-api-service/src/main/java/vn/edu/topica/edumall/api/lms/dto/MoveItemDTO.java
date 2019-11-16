package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class MoveItemDTO {
    private long fileId;
    private long folderId;
    private long parentFolderId;
}
