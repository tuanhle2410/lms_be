package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.data.enumtype.AssetEnum;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssetForMigrateDTO {

    @JsonProperty("asset_type")
    private AssetEnum assetType;

    @JsonProperty("transcode_url")
    private String transcodeUrl;

    private FileForMigrateDTO file;
}
