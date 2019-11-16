package vn.edu.topica.edumall.api.lms.upload.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.bus.Event;
import reactor.fn.Consumer;
import vn.edu.topica.edumall.api.lms.dto.UploadS3DTO;
import vn.edu.topica.edumall.api.lms.service.UploadService;

@Service
@Slf4j
public class UploadConsumer implements Consumer<Event<UploadS3DTO>> {

    @Autowired
    UploadService uploadService;

    @Override
    public void accept(Event<UploadS3DTO> uploadS3DTOEvent) {
        log.info("Consumer starting process upload..................." + uploadS3DTOEvent.getData().getS3FileName());
        uploadService.uploadFileToS3(uploadS3DTOEvent.getData());
        log.info("Consumer finish upload......................" + uploadS3DTOEvent.getData().getS3FileName());
    }
}
