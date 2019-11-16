package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class LectureDetailDTO {

    private Long id;

    private String title;

    private Integer lectureOrder;

    private List<AssetDetailDTO> assetList;
}
