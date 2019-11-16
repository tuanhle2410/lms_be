package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.enumtype.AssetEnum;
import vn.edu.topica.edumall.data.model.File;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class AssetDetailDTO {

    private long id;

    private AssetEnum asset_type;

    private String transcode_url;

    @JsonIgnoreProperties(value = {"folder", "assets", "mediaToolResult"})
    private File file;
}
