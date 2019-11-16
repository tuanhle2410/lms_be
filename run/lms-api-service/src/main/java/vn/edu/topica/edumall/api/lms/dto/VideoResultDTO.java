package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoResultDTO {

    @JsonProperty("codec_name")
    String videoCodecName;

    @JsonProperty("coded_width")
    Long videoCodeWidth;

    @JsonProperty("coded_height")
    Long videoCodeHeight;

    @JsonProperty("avg_frame_rate")
    Long videoAvgFrameRate;

    @JsonProperty("bit_rate")
    Long videoBitRate;
}
