package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@LoggingDTO
public class UserDetailDTO {

    private Long id;

    private String name;

    private String username;

    private String email;

    private String mobile;

    private String avatar;

    private Long rootFolderId;

    private String teacherCode;
}
