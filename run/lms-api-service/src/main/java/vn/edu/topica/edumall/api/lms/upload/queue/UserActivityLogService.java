package vn.edu.topica.edumall.api.lms.upload.queue;

import vn.edu.topica.edumall.data.model.UserActivityLog;

public interface UserActivityLogService {

    void save(UserActivityLog userActivityLog);
}
