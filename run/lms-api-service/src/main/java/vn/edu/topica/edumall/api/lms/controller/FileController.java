package vn.edu.topica.edumall.api.lms.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import reactor.bus.EventBus;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.api.lms.service.FileService;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.s3.service.AwsS3ClientService;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    FileService fileService;

    @Autowired
    AwsS3ClientService s3ClientService;

    @Value("${aws.s3.buckets.user-upload}")
    private String userUploadBucketName;

    @PreAuthorize("isFolderOwner(#file.getFolderId())")
    @PostMapping("")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> uploadFile(@ModelAttribute FileUploadDTO file) {
        return new ResponseEntity<>(fileService.uploadFile(file), HttpStatus.CREATED);
    }

    @PreAuthorize("isFileOwner(#moveItemDTO.getFileId()) and isFolderOwner(#moveItemDTO.getParentFolderId())")
    @PostMapping("/moveToFolder")
    public ResponseEntity<Boolean> moveToFolder(@RequestBody MoveItemDTO moveItemDTO) {
        Boolean result = fileService.moveToFolder(moveItemDTO);

        return new ResponseEntity<Boolean>(result, HttpStatus.OK);
    }

    @PreAuthorize("isFileOwner(#fileId)")
    @GetMapping("/detail")
    public ResponseEntity<File> detail(@RequestParam @NotEmpty long fileId) {
        File file = fileService.detail(fileId);
        if (file != null)
            return new ResponseEntity<File>(file, HttpStatus.OK);
        else
            return new ResponseEntity<File>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("")
    public ResponseEntity<Object> allFile() {
        return new ResponseEntity<>(fileService.allFile(), HttpStatus.OK);
    }

    @PreAuthorize("isFileOwner(#fileId)")
    @PutMapping("/{fileId}")
    public ResponseEntity<String> changeFileName(@RequestBody @Valid ItemNameDTO fileNameDTO,
                                                 @PathVariable Long fileId) {

        fileService.changeFileName(fileId, fileNameDTO.getName());

        return new ResponseEntity<>("Successfully updated file name", HttpStatus.OK);

    }

    @PostMapping("/check-duplicate-filename")
    public ResponseEntity<Object> checkDuplicateFilename(@RequestBody @Valid DuplicatedFileAndFolderDTO payLoad) {
        return new ResponseEntity<>(fileService.checkDuplicateFilename(payLoad), HttpStatus.OK);
    }

    @PreAuthorize("isFileOwner(#id)")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable("id") Long id) {
        return new ResponseEntity<>(fileService.deleteFile(id), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("isFileOwner(#copyFileDTO.getFileId()) and isFolderOwner(#copyFileDTO.getFolderId())")
    @PostMapping("/copy-file")
    public ResponseEntity<Object> copyFile(@RequestBody @Valid CopyFileDTO copyFileDTO) {
        return new ResponseEntity<>(fileService.copyFile(copyFileDTO), HttpStatus.CREATED);
    }

    //	@PreAuthorize("isFileOwner(#fileId)")
    @GetMapping("/{fileId}/download")
    public ResponseEntity<byte[]> download(@PathVariable Long fileId) throws IOException, NoSuchAlgorithmException {
        File fileDownload = fileService.getFileById(fileId);
        byte[] file = this.s3ClientService.downloadFile(userUploadBucketName, fileDownload.getObjectKey());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentLength(file.length);
        httpHeaders.setContentDispositionFormData("attachment", fileDownload.getName());
        return new ResponseEntity<>(file, httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/avatar")
    public ResponseEntity<Object> uploadFileAvatar(@ModelAttribute @Valid FileAvatarDTO file) {
        return new ResponseEntity<>(fileService.uploadAvatar(file), HttpStatus.CREATED);
    }
}
