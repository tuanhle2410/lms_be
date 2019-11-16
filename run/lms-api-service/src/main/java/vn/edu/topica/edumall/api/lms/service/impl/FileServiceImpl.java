package vn.edu.topica.edumall.api.lms.service.impl;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;
import reactor.bus.Event;
import reactor.bus.EventBus;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.api.lms.repository.FileRepository;
import vn.edu.topica.edumall.api.lms.repository.FolderRepository;
import vn.edu.topica.edumall.api.lms.repository.LMSUserRepository;
import vn.edu.topica.edumall.api.lms.repository.WarehouseRepository;
import vn.edu.topica.edumall.api.lms.service.FileService;
import vn.edu.topica.edumall.api.lms.service.FolderService;
import vn.edu.topica.edumall.api.lms.upload.queue.ConsumerEnum;
import vn.edu.topica.edumall.api.lms.utility.FileUtility;
import vn.edu.topica.edumall.api.lms.utility.UserAuthenticationInfo;
import vn.edu.topica.edumall.api.rest.exception.FileDuplicateException;
import vn.edu.topica.edumall.data.enumtype.FileStatusEnum;
import vn.edu.topica.edumall.data.enumtype.FileTypeEnum;
import vn.edu.topica.edumall.data.enumtype.UploadFileStatusEnum;
import vn.edu.topica.edumall.data.model.File;
import vn.edu.topica.edumall.data.model.Folder;
import vn.edu.topica.edumall.data.model.User;
import vn.edu.topica.edumall.data.model.WareHouse;
import vn.edu.topica.edumall.locale.config.Translator;
import vn.edu.topica.edumall.s3.service.AwsS3ClientService;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

@Service
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {

//    @Value("${upload.folder.path}")
//    String folderPath;

    public static final String avatarName = "Ảnh minh họa";

    @Value("${aws.s3.buckets.user-upload}")
    String bucketName;

    @Value("${aws.s3.urls.user-upload}")
    String s3Url;

    @Value("${aws.s3.urls.app-storage}")
    String s3UrlBackup;

    @Value("${random-key-upload-file.min-random}")
    Long minRandom;

    @Value("${random-key-upload-file.max-random}")
    Long maxRandom;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    FileUtility fileUtility;

    @Autowired
    AwsS3ClientService awsS3ClientService;

    @Autowired
    FolderService folderService;

    @Autowired
    LMSUserRepository userRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    EventBus eventBus;

    @Override
    public ExistObjectDTO checkDuplicateFilename(DuplicatedFileAndFolderDTO payLoad) {
        ExistObjectDTO existObjectDTO = new ExistObjectDTO();
        try {
            Optional<Folder> optionFolder = folderRepository.findById(Long.valueOf(payLoad.getFolderId()));
            if (!optionFolder.isPresent()) {
                throw new RuntimeException("Folder not found");
            }
            List<FileExistDTO> files = fileUtility.getListDuplicateFileName(optionFolder.get(), payLoad.getListFilename());
            if (files != null && files.size() > 0) {
                existObjectDTO.setMessage("File exist");
                existObjectDTO.setIsExist(true);
                existObjectDTO.setFiles(files);
                return existObjectDTO;
            }
            existObjectDTO.setMessage("File not exist");
            existObjectDTO.setIsExist(false);
            existObjectDTO.setFiles(files);
            return existObjectDTO;
        } catch (NumberFormatException ex) {
            throw new RuntimeException("Folder not found");
        }
    }

    @Override
    public File createFile(File file) {
        String fileName = file.getName().trim();
        Optional<Folder> folder = folderRepository.findById(file.getFolder().getId());
        if (!folder.isPresent()) {
            throw new RuntimeException("Folder not found");
        }

        Long isDuplicateFilename = fileUtility.isDuplicateFilename(folder.get(), fileName);
        if (isDuplicateFilename != 0L) {
            throw new FileDuplicateException(fileName);
        }
        file.setName(fileName);
        file.setStatus(FileStatusEnum.AVAILABILITY);

        return fileRepository.save(file);
    }

    @Override
    public File deleteFile(Long id) {
        Optional<File> file = fileRepository.findById(id);
        if (!file.isPresent()) {
            throw new RuntimeException(Translator.toLocale("file.notfound"));
        }
        file.get().setDeletedAt(new Date());
        return file.get();
    }

    public List<File> allFile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = ((UserPrincipal) authentication.getCredentials());
        return fileRepository.findByCreatedBy(user.getId());
    }

    @Override
    public File getFileById(Long id) {
        return fileRepository.findById(id).orElse(null);
    }


    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public File copyFile(CopyFileDTO copyFileDTO) {
        Optional<Folder> optionFolder = folderRepository.findById(copyFileDTO.getFolderId());
        Optional<File> optionCurrentFile = fileRepository.findById(copyFileDTO.getFileId());
        if (!optionFolder.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("folder.notfound"), null);
        }
        if (!optionCurrentFile.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("file.notfound"), null);
        }
        String fileName = optionCurrentFile.get().getName();
        String sourceKey = optionCurrentFile.get().getObjectKey();
        String destinationKey = new Date().getTime() + "_" + fileName;
        if (awsS3ClientService.copyFile(bucketName, sourceKey, bucketName, destinationKey) == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("failed.to.upload.s3"), null);
        }

        File copyFile = File.builder().build();
        copyFile.setFolder(optionFolder.get());
        copyFile.setName("Bản sao của" + " " + fileName);
        copyFile.setObjectKey(destinationKey);
        copyFile.setUrl(s3Url + "/" + destinationKey);
        copyFile.setFileType(optionCurrentFile.get().getFileType());
        copyFile.setFileExtension(optionCurrentFile.get().getFileExtension());
        copyFile.setFileSize(optionCurrentFile.get().getFileSize());
        copyFile.setStatus(optionCurrentFile.get().getStatus());
        copyFile.setDuration(optionCurrentFile.get().getDuration());

        UserPrincipal user = UserAuthenticationInfo.getUserAuthenticationInfo();
        copyFile.setCreatedBy(user.getId());

        return fileRepository.save(copyFile);
    }

    @Override
    public UploadDataDTO<FileExistDTO> uploadFile(FileUploadDTO file) {
        List<MultipartFile> fileInputs = file.getListFile();
        if (file.getFolderId() == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("folder_id.notnull"), null);
        }
        if (fileInputs == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("file.notnull"), null);
        }
        Optional<Folder> folder = folderRepository.findById(Long.valueOf(file.getFolderId()));
        if (!folder.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("folder.notfound"), null);
        }

        Map<String, File> fileMap = createFileHash(folder.get().getFiles());
        List<FileExistDTO> fileUploadErrors = new ArrayList<>();

        for (MultipartFile fileInput : fileInputs) {
            long strRandom = ThreadLocalRandom.current().nextLong(minRandom, maxRandom + 1);
            String s3Filename = new Date().getTime() + "_" + strRandom + "_" + fileInput.getOriginalFilename();

            Long duration = file.getDuration();
            Long fileID = null;
            if (fileMap != null && fileMap.containsKey(fileInput.getOriginalFilename().trim())) {
                String fileNameInput = FilenameUtils.getBaseName(fileInput.getOriginalFilename().trim());
                long latestVersion = getLatestVersionOfFileName(folder.get().getFiles(), fileNameInput) + 1L;
                String fileExtension = FilenameUtils.getExtension(fileInput.getOriginalFilename().trim());
                String fileNameTmp = fileNameInput + "(" + latestVersion + ")";
                String originalFileName = fileExtension.isEmpty() ? fileNameTmp : fileNameTmp + "." + fileExtension;
                File fileSaved = fileRepository.save(creatFileForInsert(fileInput, s3Filename, folder.get(), originalFileName, false, duration));
                fileID = fileSaved.getId();
            } else {
                File fileSaved = fileRepository.save(creatFileForInsert(fileInput, s3Filename, folder.get(), fileInput.getOriginalFilename(), false, duration));
                fileID = fileSaved.getId();
            }

            UploadS3DTO uploadS3DTO = UploadS3DTO.builder()
                    .s3FileName(s3Filename)
                    .bucketName(bucketName)
                    .multipartFile(fileInput)
                    .fileId(fileID)
                    .build();

            log.info("Add to queue............................" + s3Filename);
            eventBus.notify(ConsumerEnum.UPLOADS3, Event.wrap(uploadS3DTO));
            log.info("Finish add to queue......................" + s3Filename);
        }

        UploadDataDTO<FileExistDTO> uploadDataDTO = new UploadDataDTO<>();
        uploadDataDTO.setMessage(Translator.toLocale("files.is.uploaded"));
        uploadDataDTO.setErrorData(fileUploadErrors);
        return uploadDataDTO;
    }

    @Override
    public File changeFileName(Long fileId, String name) {

        File file = fileRepository.findById(fileId).orElse(null);

        if (file.getName().trim().equals(name.trim())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No changes fo");
        }

        if (file == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }

        File checkedDupFile = fileRepository.findByNameAndFolderId(name, file.getFolder().getId());

        String fileName = name;
        if (checkedDupFile != null) {
            String fileNameInput = FilenameUtils.getBaseName(name.trim());
            String fileExtension = FilenameUtils.getExtension(name.trim());
            long latestVersion = getLatestVersionOfFileName(file.getFolder().getFiles(), fileNameInput) + 1L;
            fileName = fileNameInput + "(" + latestVersion + ")." + fileExtension;
        }

        file.setName(fileName);

        return file;
    }

    private Map<String, File> createFileHash(List<File> files) {
        Map<String, File> fileMap = new HashMap<>();
        if (files != null && files.size() > 0) {
            for (File file : files) {
                fileMap.put(file.getName(), file);
            }
            return fileMap;
        }
        return null;
    }

    private FileExistDTO creatFileUploadError(String fileName) {
        FileExistDTO errorFile = FileExistDTO.builder().build();
        errorFile.setName(fileName);
        return errorFile;
    }

    @Override
    public File creatFileForInsert(MultipartFile file, String s3Filename, Folder folder, String fileName, Boolean isBackup, Long fileDuration) {
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename().trim());
        File fileInsert = File.builder().build();
        FileTypeEnum fileTypeEnum = fileUtility.getFileType(fileExtension.toLowerCase());
        if (fileExtension.isEmpty()) {
            fileInsert.setFileType(FileTypeEnum.OTHER);
        } else {
            fileInsert.setFileType(fileTypeEnum);
        }

        fileInsert.setName(fileName.trim());
        fileInsert.setStatus(FileStatusEnum.AVAILABILITY);
        fileInsert.setObjectKey(s3Filename);
        fileInsert.setFolder(folder);
        fileInsert.setUrl((isBackup ? s3UrlBackup : s3Url) + "/" + s3Filename);
        fileInsert.setFileExtension(fileExtension);
        fileInsert.setFileSize(file.getSize());
        fileInsert.setUploadStatus(UploadFileStatusEnum.PENDING);
        if (fileTypeEnum.equals(FileTypeEnum.VIDEO)) {
            fileInsert.setDuration(fileDuration);
        }

        UserPrincipal user = UserAuthenticationInfo.getUserAuthenticationInfo();
        fileInsert.setCreatedBy(user.getId());
        return fileInsert;
    }

    @Override
    public List<File> getListFileByIds(List<Long> fileIdList) {
        return fileRepository.getListFileByIds(fileIdList);
    }

    private Long getLatestVersionOfFileName(List<File> files, String fileNameInput) {
        Set<String> names = files.stream().map(file -> FilenameUtils.getBaseName(file.getName().trim())).collect(Collectors.toSet());
        List<Long> versions = new ArrayList<>();
        versions.add(0L);
        // get list version number
        int inputLength = fileNameInput.length();
        for (String name : names) {
            if (name.startsWith(fileNameInput) && name.length() > fileNameInput.length()) {
                String versionString = name.substring(inputLength);

                // check if version string is invalid format (1)
                if (versionString.charAt(0) == '(' && versionString.charAt(versionString.length() - 1) == ')') {
                    try {
                        Long versionFileNumber = Long.valueOf(versionString.substring(1, versionString.length() - 1));
                        versions.add(versionFileNumber);
                    } catch (NumberFormatException ex) {
                        versions.add(0L);
                    }
                }
            }
        }

        return Collections.max(versions);
    }

    @Override
    public Boolean moveToFolder(MoveItemDTO moveItemDTO) {
        long fileId = moveItemDTO.getFileId();
        long parentFolderId = moveItemDTO.getParentFolderId();

        Optional<File> optionFile = fileRepository.findById(fileId);
        Optional<Folder> optionParentFolder = folderRepository.findById(parentFolderId);

        File file;
        Folder parentFolder;

        if (!optionFile.isPresent() || !optionParentFolder.isPresent())
            return false;
        else {
            file = optionFile.get();
            parentFolder = optionParentFolder.get();

            file.setFolder(parentFolder);

            return true;
        }
    }

    @Override
    public File detail(long fileId) {
        Optional<File> optionFile = fileRepository.findById(fileId);
        if (optionFile.isPresent()) {
            return optionFile.get();
        } else
            return null;
    }

    @Override
    public File uploadAvatar(FileAvatarDTO file) {

        MultipartFile avatar = file.getFile();

        if (avatar == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, Translator.toLocale("file.notnull"), null);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal user = ((UserPrincipal) authentication.getCredentials());

        Folder rootFolder = folderRepository.getFolderByUserId(user.getId());

        Folder avatarFolder = folderRepository.findByNameAndParentFolder_Id(avatarName, rootFolder.getId());

        if (avatarFolder == null) {
            Folder newFolder = Folder.builder().name(avatarName).parentFolder(rootFolder).build();
            avatarFolder = folderService.createFolder(newFolder);
        }

        long strRandom = ThreadLocalRandom.current().nextLong(minRandom, maxRandom + 1);
        String s3Filename = new Date().getTime() + "_" + strRandom + "_" + avatar.getOriginalFilename();

        File uploadedAvatar = creatFileForInsert(avatar, s3Filename, avatarFolder, avatar.getOriginalFilename(), false, null);
        File fileSaved = fileRepository.save(uploadedAvatar);

        UploadS3DTO uploadS3DTO = UploadS3DTO.builder()
                .s3FileName(s3Filename)
                .bucketName(bucketName)
                .multipartFile(avatar)
                .fileId(fileSaved.getId())
                .build();

        log.info("Add to queue............................" + s3Filename);
        eventBus.notify(ConsumerEnum.UPLOADS3, Event.wrap(uploadS3DTO));
        log.info("Finish add to queue......................" + s3Filename);

        return uploadedAvatar;

    }
}