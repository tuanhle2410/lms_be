package vn.edu.topica.edumall.api.lms.upload.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;
import vn.edu.topica.edumall.api.lms.dto.EmailToTeacherDTO;
import vn.edu.topica.edumall.api.lms.service.CourseVersionService;

@Slf4j
@Service
public class EmailToTeacherConsumer implements Consumer<Event<EmailToTeacherDTO>> {

    @Autowired
    CourseVersionService courseVersionService;

    @Override
    public void accept(Event<EmailToTeacherDTO> event) {
        EmailToTeacherDTO emailToTeacher = event.getData();
        log.info("Consumer start: The process send an email to the teacher..................." + emailToTeacher.getTeacherEmail());
        courseVersionService.sendEmailToTeacher(emailToTeacher);
        log.info("Consumer finish: The process send an email to the teacher..................");
    }
}
