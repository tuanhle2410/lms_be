package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LectureForMigrateDTO {

    private String title;

    private List<AssetForMigrateDTO> assets;
}
