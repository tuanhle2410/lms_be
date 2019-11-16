package vn.edu.topica.edumall.api.lms.upload.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;
import vn.edu.topica.edumall.api.lms.dto.FilesAndCourseVerIdForMediaToolDTO;
import vn.edu.topica.edumall.api.lms.service.CourseVersionService;

@Slf4j
@Service
public class ProcessSendToMediaToolConsumer implements Consumer<Event<FilesAndCourseVerIdForMediaToolDTO>> {

    @Autowired
    CourseVersionService courseVersionService;

    @Override
    public void accept(Event<FilesAndCourseVerIdForMediaToolDTO> event) {
        FilesAndCourseVerIdForMediaToolDTO filesAndCourseVerId = event.getData();
        log.info("Consumer start: The process send to media tool..................." + filesAndCourseVerId);
        courseVersionService.processSendToMediaTool(filesAndCourseVerId);
        log.info("Consumer finish: The process send to media tool..................");
    }
}
