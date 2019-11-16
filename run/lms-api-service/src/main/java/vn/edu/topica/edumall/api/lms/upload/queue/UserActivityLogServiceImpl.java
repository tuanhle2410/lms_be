package vn.edu.topica.edumall.api.lms.upload.queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.edumall.api.lms.repository.UserActivityLogRepository;
import vn.edu.topica.edumall.data.model.UserActivityLog;

@Service
public class UserActivityLogServiceImpl implements UserActivityLogService {

    @Autowired
    UserActivityLogRepository userActivityLogRepository;

    @Override
    public void save(UserActivityLog userActivityLog) {
        userActivityLogRepository.save(userActivityLog);
    }
}
