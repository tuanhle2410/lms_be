package vn.edu.topica.edumall.api.lms.dto;
import lombok.*;
import vn.edu.topica.edumall.api.lms.logger.LoggingDTO;
import vn.edu.topica.edumall.api.lms.repository.CountByStatusAndUserIdRepository;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@LoggingDTO
public class CountByStatusAndUserIdDTO {
    private List<CountByStatusAndUserIdRepository> data;
}
