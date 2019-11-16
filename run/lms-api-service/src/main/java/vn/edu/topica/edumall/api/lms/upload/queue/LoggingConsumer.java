package vn.edu.topica.edumall.api.lms.upload.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;
import vn.edu.topica.edumall.api.lms.dto.CategoryDTO;
import vn.edu.topica.edumall.api.lms.dto.UserActivityLogDTO;
import vn.edu.topica.edumall.data.model.UserActivityLog;

@Service
public class LoggingConsumer implements Consumer<Event<UserActivityLog>> {

    @Autowired
    UserActivityLogService userActivityLogService;


    @Override
    public void accept(Event<UserActivityLog> userActivityLogEvent) {
        userActivityLogService.save(userActivityLogEvent.getData());
    }

}
