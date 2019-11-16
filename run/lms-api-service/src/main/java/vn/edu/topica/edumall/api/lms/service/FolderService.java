package vn.edu.topica.edumall.api.lms.service;

import org.springframework.data.domain.Page;
import vn.edu.topica.edumall.api.lms.dto.FolderDTO;
import vn.edu.topica.edumall.api.lms.dto.MoveItemDTO;
import vn.edu.topica.edumall.api.lms.utility.OffsetBasedPageRequest;
import vn.edu.topica.edumall.data.enumtype.FileTypeEnum;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.data.model.Folder;

public interface FolderService {

    /**
     * Used to get detail folder by folderId
     *
     * @param folderId
     * @return
     */
    Folder getFolderDetail(long folderId);

    /**
     * Used to create folder
     *
     * @param folder
     * @return
     */
    Folder createFolder(Folder folder);

    /**
     * Used to save folder
     *
     * @param folder
     * @return
     */
    Folder save(Folder folder);

    /**
     * Used to change folder name
     *
     * @param folderId
     * @param folderName
     * @return
     */
    Folder changeFolderName(Long folderId, String folderName);

    /**
     * Purpose: Move folder to folder
     *
     * @param folderId:       id of folder you want to move
     * @param parentFolderId: destination folder id
     * @return true if move success, false if fail
     */
    Boolean moveToFolder(MoveItemDTO moveItemDTO);

    /**
     * Purpose: return detail of a folder
     *
     * @param folderId: folder id
     * @return folder detail if it's existed. else null
     */
    Folder detail(long folderId);

    /**
     * Purpose: return detail of a folder for uploading avatar
     *
     * @param folderId: folder id
     * @param fileType: file type
     * @return folder detail if it's existed. else null
     */
    FolderDTO detailForAvatarUploading(long folderId, FileTypeEnum fileType);

    /**
     * Purpose: return files of a folder for uploading avatar
     *
     * @param folderId: folder id
     * @param fileType: file type
     * @param OffsetBasedPageRequest: pageable
     * @return folder detail if it's existed. else null
     */
    Page<File> getFilesForAvatarUploading(long folderId, FileTypeEnum fileType, OffsetBasedPageRequest pageable);

}