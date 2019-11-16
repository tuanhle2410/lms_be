package vn.edu.topica.edumall.api.lms.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.topica.edumall.api.lms.dto.UploadS3DTO;
import vn.edu.topica.edumall.api.lms.repository.FileRepository;
import vn.edu.topica.edumall.api.lms.service.UploadService;
import vn.edu.topica.edumall.data.enumtype.UploadFileStatusEnum;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.s3.service.AwsS3ClientService;


@Slf4j
@Service
public class UploadServiceImpl implements UploadService {

    @Autowired
    AwsS3ClientService awsS3ClientService;

    @Autowired
    FileRepository fileRepository;

    @Override
    public void uploadFileToS3(UploadS3DTO uploadS3DTO) {
        log.info("retry................");
        String fileDuration = awsS3ClientService.uploadFile(uploadS3DTO.getBucketName(), uploadS3DTO.getMultipartFile(), uploadS3DTO.getS3FileName());
        Long fileId = uploadS3DTO.getFileId();
        File file = fileRepository.findById(fileId).orElse(null);
        if (file != null) {
            file.setUploadStatus(UploadFileStatusEnum.SUCCESS);
            file.setDuration(Long.valueOf(fileDuration));
            fileRepository.save(file);
        }
    }

    @Override
    public void recover(Exception e, UploadS3DTO uploadS3DTO) {
        log.info("Recover when upload file..................." + uploadS3DTO.getFileId());
        log.info("Exception when upload file: " + e);
        Long fileId = uploadS3DTO.getFileId();
        File file = fileRepository.findById(fileId).orElse(null);
        if (file != null) {
            file.setUploadStatus(UploadFileStatusEnum.FAILED);
            fileRepository.save(file);
        }
    }
}
