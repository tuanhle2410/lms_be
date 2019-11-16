package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaToolResponseDTO {

    @JsonProperty("nb_streams")
    String nbStreams;

    VideoResultDTO video;

    AudioResultDTO audio;

    String failedCriteria;

    @JsonProperty("Result")
    String result;

    String url;

    @JsonProperty("file_name")
    String fileName;
}
