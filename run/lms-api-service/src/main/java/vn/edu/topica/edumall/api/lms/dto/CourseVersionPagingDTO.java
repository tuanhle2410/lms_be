package vn.edu.topica.edumall.api.lms.dto;

import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;

import javax.persistence.Entity;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@LoggingDTO
public class CourseVersionPagingDTO {
    List<CourseVersionDTO> listCourseVersionDTO;
    int totalPage;
    int perPage;
    int previousPage;
    int currentPage;
    int nextPage;
}
