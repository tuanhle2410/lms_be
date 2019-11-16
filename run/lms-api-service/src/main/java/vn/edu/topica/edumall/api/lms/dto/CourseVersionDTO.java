package vn.edu.topica.edumall.api.lms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.data.enumtype.ApproveStatusEnum;
import vn.edu.topica.edumall.data.enumtype.CourseVersionStatusEnum;

import javax.persistence.Entity;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@LoggingDTO
public class CourseVersionDTO {
    private Long id;
    private String name;
    private String thumbnailImg;
    private String backgroundImg;
    private CourseVersionStatusEnum status;
    private String courseCode;
    private String descriptionStatus;
    private String linkApproveResult;
    @JsonProperty("approveStatus")
    private ApproveStatusEnum statusApprove;
}
