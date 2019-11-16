package vn.edu.topica.edumall.api.lms.upload.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;
import vn.edu.topica.edumall.api.lms.dto.FilesAndCourseVerIdForMediaToolDTO;
import vn.edu.topica.edumall.api.lms.service.CourseVersionService;

@Service
@Slf4j
public class SendEmailConsumer implements Consumer<Event<FilesAndCourseVerIdForMediaToolDTO>> {

    @Autowired
    CourseVersionService courseVersionService;

    @Override
    public void accept(Event<FilesAndCourseVerIdForMediaToolDTO> event) {
        FilesAndCourseVerIdForMediaToolDTO filesAndCourseVerId = event.getData();
        log.info("Consumer start: The process check upload video for sending email..................." + filesAndCourseVerId);
        courseVersionService.processSendEmailWhenFailUploadVideo(filesAndCourseVerId);
        log.info("Consumer finish: The process check upload video for sending email..................");
    }
}
