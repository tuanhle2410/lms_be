package vn.edu.topica.edumall.api.lms.service;

import vn.edu.topica.edumall.api.lms.dto.UploadS3FinishedEmailDTO;

import java.io.IOException;
import java.net.URISyntaxException;

public interface EmailService {
    void sendEmail(UploadS3FinishedEmailDTO emailDTO);
}
