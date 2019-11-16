package vn.edu.topica.edumall.api.lms.upload.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;
import vn.edu.topica.edumall.api.lms.dto.CourseKelleyUpdateDTO;
import vn.edu.topica.edumall.api.lms.service.CourseVersionService;

@Slf4j
@Service
public class UpdateCourseKelleyConsumer implements Consumer<Event<CourseKelleyUpdateDTO>> {

    @Autowired
    CourseVersionService courseVersionService;

    @Override
    public void accept(Event<CourseKelleyUpdateDTO> event) {
        CourseKelleyUpdateDTO courseKelleyUpdateDTO = event.getData();
        log.info("Consumer start: The process update the course in the kelley..................." + courseKelleyUpdateDTO.getCourseCode());
        courseVersionService.updateCourseKelley(courseKelleyUpdateDTO);
        log.info("Consumer finish: The process update the course in the kelley..................");
    }
}
