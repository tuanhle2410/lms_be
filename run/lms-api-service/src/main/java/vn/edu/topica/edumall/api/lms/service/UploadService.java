package vn.edu.topica.edumall.api.lms.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import vn.edu.topica.edumall.api.lms.dto.UploadS3DTO;

public interface UploadService {

    @Retryable(value = {Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 5000))
    void uploadFileToS3(UploadS3DTO uploadS3DTO);

    @Recover
    void recover(Exception e, UploadS3DTO uploadS3DTO);
}
