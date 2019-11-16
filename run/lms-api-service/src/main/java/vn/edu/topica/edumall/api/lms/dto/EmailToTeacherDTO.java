package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailToTeacherDTO {
    String teacherEmail;
    String courseLink;
    String templateCode;
    String subject;
    String teacherName;
    String teacherCode;
    String courseCode;
}
