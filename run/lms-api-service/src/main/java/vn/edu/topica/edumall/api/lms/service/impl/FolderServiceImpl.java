package vn.edu.topica.edumall.api.lms.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.dto.FolderDTO;
import vn.edu.topica.edumall.api.lms.dto.MoveItemDTO;
import vn.edu.topica.edumall.api.lms.repository.FileRepository;
import vn.edu.topica.edumall.api.lms.repository.FolderRepository;
import vn.edu.topica.edumall.api.lms.service.FileService;
import vn.edu.topica.edumall.api.lms.service.FolderService;
import vn.edu.topica.edumall.api.lms.utility.OffsetBasedPageRequest;
import vn.edu.topica.edumall.api.lms.utility.UserAuthenticationInfo;
import vn.edu.topica.edumall.data.enumtype.FileTypeEnum;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.data.model.Folder;
import vn.edu.topica.edumall.locale.config.Translator;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

@Service
@Transactional
public class FolderServiceImpl implements FolderService {

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FileService fileService;

    @Override
    public Folder getFolderDetail(long folderId) {
        return folderRepository.findById(folderId).orElse(null);
    }

    @Override
    public Folder createFolder(Folder folder) {
        if (folder.getName().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("name.notnull"));
        }
        Optional<Folder> currentFolder = folderRepository.findById(folder.getParentFolder().getId());
        if (!currentFolder.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("folder.notfound"));
        }

        UserPrincipal user = UserAuthenticationInfo.getUserAuthenticationInfo();
        folder.setCreatedBy(user.getId());

        return folderRepository.save(folder);
    }

    @Override
    public Folder save(Folder folder) {
        return folderRepository.save(folder);
    }

    @Override
    public Folder changeFolderName(Long folderId, String folderName) {

        Folder folder = getFolderDetail(folderId);
        if (folder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder Not Found", null);
        }

        folder.setName(folderName);

        return folder;
    }

    @Override
    public Folder detail(long folderId) {
        Optional<Folder> optionFolder = folderRepository.findById(folderId);
        if (optionFolder.isPresent()) {
            return optionFolder.get();
        } else
            return null;
    }

    @Override
    public Boolean moveToFolder(MoveItemDTO moveItemDTO) {
        long folderId = moveItemDTO.getFolderId();
        long parentFolderId = moveItemDTO.getParentFolderId();

        Optional<Folder> optionFolder = folderRepository.findById(folderId);
        Optional<Folder> optionParentFolder = folderRepository.findById(parentFolderId);

        Folder folder;
        Folder parentFolder;

        if (!optionFolder.isPresent() || !optionParentFolder.isPresent())
            return false;
        else {
            folder = optionFolder.get();
            parentFolder = optionParentFolder.get();

            folder.setParentFolder(parentFolder);

            return true;
        }
    }

    @Override
    public FolderDTO detailForAvatarUploading(long folderId, FileTypeEnum fileType) {
        Folder folder = getFolderDetail(folderId);
        if (folder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder Not Found", null);
        }
        FolderDTO folderDTO = FolderDTO.builder()
                .id(folder.getId())
                .name(folder.getName())
                .hasMore(fileType == FileTypeEnum.ATTACHMENT ?
                        fileRepository.countByFileTypeNotAndFolder_Id(FileTypeEnum.VIDEO, folderId) > 8 : fileRepository.countByFileTypeAndFolder_Id(fileType, folderId) > 8)
                .childFolders(folder.getChildFolders())
                .parentFolder(folder.getParentFolder())
                .build();

        List<File> files;
        if(fileType == FileTypeEnum.ATTACHMENT) {
            files = fileRepository.findTop8ByFileTypeNotAndFolder_IdOrderByCreatedAtDesc(FileTypeEnum.VIDEO, folder.getId());
        } else {
            files = fileRepository.findTop8ByFileTypeAndFolder_IdOrderByCreatedAtDesc(fileType, folder.getId());
        }

        folderDTO.setFiles(files);

        return folderDTO;
    }

    @Override
    public Page<File> getFilesForAvatarUploading(long folderId, FileTypeEnum fileType, OffsetBasedPageRequest pageable) {
        Folder folder = getFolderDetail(folderId);
        if (folder == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Folder Not Found", null);
        }

        Page<File> files;
        if(fileType == FileTypeEnum.ATTACHMENT) {
            files = fileRepository.findByFolder_IdAndFileTypeNotOrderByCreatedAtDesc(folder.getId(), FileTypeEnum.VIDEO, pageable);
        } else {
            files = fileRepository.findByFolder_IdAndFileTypeOrderByCreatedAtDesc(folder.getId(), fileType, pageable);
        }

        return files;
    }
}
