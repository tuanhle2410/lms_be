package vn.edu.topica.edumall.api.lms.utility;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import vn.edu.topica.edumall.api.lms.repository.FileRepository;
import vn.edu.topica.edumall.api.lms.service.FileService;
import vn.edu.topica.edumall.data.enumtype.UploadFileStatusEnum;
import vn.edu.topica.edumall.data.model.File;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class ThreadCheckFileUploadSuccess extends Thread {
    private List<Long> fileIdList = new ArrayList<>();

    private boolean exit = false;

    private List<File> fileErrorList = new ArrayList<>();

    private FileRepository fileRepository;

    private Long sleepTime;

    public ThreadCheckFileUploadSuccess(List<Long> fileIdList, FileRepository fileRepository, Long sleepTime) {
        this.fileIdList = fileIdList;
        this.fileRepository = fileRepository;
        this.sleepTime = sleepTime;
    }

    /**
     * Used to get list files which upload is error
     */
    public void run() {
        long startTime = System.currentTimeMillis();
        int i = 0;
        while (!exit) {
            log.info(this.getName() + "Thread check file upload success in database..." + i++);

            List<File> fileList = fileRepository.getListFileByIds(fileIdList);
            List<UploadFileStatusEnum> statusList = fileList.stream()
                    .map(File::getUploadStatus)
                    .collect(Collectors.toList());
            try {
                if (!statusList.contains(UploadFileStatusEnum.PENDING)) {
                    exit = true;
                    for (File file : fileList) {
                        if (file.getUploadStatus() == UploadFileStatusEnum.FAILED) {
                            fileErrorList.add(file);
                        }
                    }
                }
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Long> getFileIdList() {
        return fileIdList;
    }

    public void setFileIdList(List<Long> fileIdList) {
        this.fileIdList = fileIdList;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public List<File> getFileErrorList() {
        return fileErrorList;
    }

    public void setFileErrorList(List<File> fileErrorList) {
        this.fileErrorList = fileErrorList;
    }

    public FileRepository getFileRepository() {
        return fileRepository;
    }

    public void setFileRepository(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }
}
