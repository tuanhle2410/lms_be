package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadS3FinishedEmailDTO {
    List<ReceiverDTO> receivers;

    List<CCSDTO> ccs;

    @JsonProperty("substitution_tags")
    List<String> substitutionTags;

    EmailDTO sender;

    @JsonProperty("reply_to")
    EmailDTO replyTo;

    List<AttachmentEmailDTO> attachments;

    @JsonProperty("template_code")
    private String templateCode;

    private String content;

    private String subject;
}
