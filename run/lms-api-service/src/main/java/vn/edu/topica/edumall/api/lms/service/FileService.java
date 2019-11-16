package vn.edu.topica.edumall.api.lms.service;

import org.springframework.web.multipart.MultipartFile;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.data.model.Folder;

import java.util.List;

public interface FileService {

    /**
     * Used to create file
     *
     * @param file
     * @return
     */
    File createFile(File file);

    /**
     * Used to delete file
     *
     * @param id
     * @return
     */
    File deleteFile(Long id);

    /**
     * Used to check duplicate filename
     *
     * @param payLoad
     * @return
     */
    ExistObjectDTO checkDuplicateFilename(DuplicatedFileAndFolderDTO payLoad);

    /**
     * Used to get all file
     *
     * @return
     */
    List<File> allFile();

    /**
     * Used to get file byId
     *
     * @param id
     * @return
     */
    File getFileById(Long id);

    /**
     * Used to change filename
     *
     * @param id
     * @param name
     * @return
     */
    File changeFileName(Long id, String name);

    /**
     * Used to save file
     *
     * @param file
     * @return
     */
    File save(File file);

    /**
     * Used to copy file
     *
     * @param copyFileDTO
     * @return
     */
    File copyFile(CopyFileDTO copyFileDTO);

    /**
     * Used to upload files. Create files in current folder
     *
     * @param file
     * @return
     */
    UploadDataDTO<FileExistDTO> uploadFile(FileUploadDTO file);

    /**
     * Purpose: Move file to another folder
     *
     * @param fileId:         file id
     * @param parentFolderId: destination folder id
     * @return if success return true, else false
     */
    Boolean moveToFolder(MoveItemDTO moveItemDTO);

    /**
     * Purpose: return detail of file
     *
     * @param fileId: file id
     * @return detail of the file if file is existed. return null if it's not existed
     */
    File detail(long fileId);

    /**
     * Used to upload avatar
     *
     * @param file
     * @return
     */
    File uploadAvatar(FileAvatarDTO file);

    /**
     * Used to create file to S3
     *
     * @param file
     * @return
     */
    File creatFileForInsert(MultipartFile file, String s3Filename, Folder folder, String fileName, Boolean isBackup, Long fileDuration);

    /**
     * Used to get list files by list ids
     *
     * @param fileIdList
     * @return
     */
    List<File> getListFileByIds(List<Long> fileIdList);
}
