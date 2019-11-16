package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AudioResultDTO {
    @JsonProperty("codec_name")
    String audioCodecName;

    @JsonProperty("sample_rate")
    Long audioSampleRate;

    @JsonProperty("bit_rate")
    Long audioBitRate;

    @JsonProperty("channels")
    Long audioChannels;

}
