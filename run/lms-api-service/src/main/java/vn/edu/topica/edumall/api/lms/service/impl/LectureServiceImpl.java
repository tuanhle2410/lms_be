package vn.edu.topica.edumall.api.lms.service.impl;

import liquibase.util.file.FilenameUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import reactor.bus.Event;
import reactor.bus.EventBus;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.api.lms.repository.FileRepository;
import vn.edu.topica.edumall.api.lms.repository.LectureRepository;
import vn.edu.topica.edumall.api.lms.service.LectureService;
import vn.edu.topica.edumall.api.lms.repository.*;
import vn.edu.topica.edumall.api.lms.service.*;
import vn.edu.topica.edumall.api.lms.upload.queue.ConsumerEnum;
import vn.edu.topica.edumall.api.lms.utility.FileUtility;
import vn.edu.topica.edumall.api.lms.utility.UserAuthenticationInfo;
import vn.edu.topica.edumall.data.enumtype.AssetEnum;
import vn.edu.topica.edumall.data.model.*;
import vn.edu.topica.edumall.locale.config.Translator;

import javax.transaction.Transactional;
import java.util.ArrayList;

import vn.edu.topica.edumall.data.model.Lecture;
import vn.edu.topica.edumall.s3.service.AwsS3ClientService;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@Transactional
public class LectureServiceImpl implements LectureService {

    private static ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    private static Locale locale = LocaleContextHolder.getLocale();

    public static final String[] VIDEO_EXTENSION = new String[]{"mp4", "flv", "avi", "mov", "war"};

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private FolderRepository folderRepository;

    @Autowired
    private CourseVersionRepository courseVersionRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private FolderService folderService;

    @Value("${aws.s3.buckets.user-upload}")
    String bucketName;

    @Value("${aws.s3.urls.user-upload}")
    String s3Url;

    @Value("${aws.s3.buckets.app-storage}")
    String bucketBackupName;

    @Value("${aws.s3.urls.app-storage}")
    String s3UrlBackup;

    @Value("${random-key-upload-file.min-random}")
    Long minRandom;

    @Value("${random-key-upload-file.max-random}")
    Long maxRandom;

    @Autowired
    AwsS3ClientService awsS3ClientService;

    @Autowired
    FileUtility fileUtility;

    @Autowired
    EventBus eventBus;

    @Override
    public Lecture detail(long id) {
        Optional<Lecture> lecture = lectureRepository.findById(id);
        if (lecture.isPresent()) {
            return lecture.get();
        } else {
            return null;
        }
    }

    @Override
    public LectureDTO create(CreateLectureDTO lectureDTO) {
        long chapterId = lectureDTO.getChapterId();
        Long userId = UserAuthenticationInfo.getUserAuthenticationInfo().getId();
        Optional<Chapter> optionalChapter = chapterRepository.findById(lectureDTO.getChapterId());

        if (!optionalChapter.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    Translator.toLocale("resource.not_found", new Object[]{"Chapter"})
            );
        }

        int lectureOrder = 1;

        Chapter chapter = optionalChapter.get();
        List<Lecture> lectures = chapter.getLectures();
        if (!lectures.isEmpty()) {
            Lecture lastLecture = lectures.get(lectures.size() - 1);
            lectureOrder = lastLecture.getLectureOrder() + 1;
        }

        Lecture.LectureBuilder lectureBuilder = Lecture.builder();
        lectureBuilder.title(lectureDTO.getTitle());
        lectureBuilder.chapter(optionalChapter.get());
        lectureBuilder.lectureOrder(lectureOrder);
        lectureBuilder.createdBy(userId);
        Lecture newLecture = lectureBuilder.build();

        lectureRepository.save(newLecture);
        LectureDTO newLectureDTO = modelMapper.map(newLecture, LectureDTO.class);
        return newLectureDTO;
    }

    @Override
    public LectureDTO update(long lectureId, UpdateLectureDTO lectureDTO) {
        Optional<Lecture> optionalLecture = lectureRepository.findById(lectureId);

        if (!optionalLecture.isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    Translator.toLocale("resource.not_found", new Object[]{"Lecture"})
            );
        }

        Lecture lecture = optionalLecture.get();
        if (lecture.getTitle() != lectureDTO.getTitle()) {
            lecture.setTitle(lectureDTO.getTitle());
        }

        return modelMapper.map(lecture, LectureDTO.class);
    }

    @Override
    public LectureDetailDTO getLectureDetail(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("lecture.notfound"), null);
        }

        List<AssetDetailDTO> assetDetailDTOList = new ArrayList<>();
        List<Asset> assetList = lecture.getAssets();
        for (Asset asset : assetList) {
            AssetDetailDTO assetDetailDTO = modelMapper.map(asset, AssetDetailDTO.class);
            assetDetailDTOList.add(assetDetailDTO);
        }

        LectureDetailDTO lectureDetailDTO = modelMapper.map(lecture, LectureDetailDTO.class);
        lectureDetailDTO.setAssetList(assetDetailDTOList);

        return lectureDetailDTO;
    }

    @Override
    public ObjectDeletedDTO deleteLecture(Long id) {
        Lecture lecture = lectureRepository.findById(id).orElse(null);
        if (lecture == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"Lecture"}), null);
        }
        lectureRepository.delete(lecture);

        ObjectDeletedDTO objectDeletedDTO = ObjectDeletedDTO.builder().build();
        objectDeletedDTO.setStatus(true);
        return objectDeletedDTO;
    }

    @Override
    public AssetDetailDTO uploadAttachment(AssetUploadLectureDTO assetUploadLectureDTO) {
        // check if lecture exist
        Lecture lecture = detail(assetUploadLectureDTO.getLectureId());
        Long userId = UserAuthenticationInfo.getUserAuthenticationInfo().getId();
        CourseVersion courseVersion = courseVersionRepository.findById(assetUploadLectureDTO.getCourseId()).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    Translator.toLocale("resource.not_found", new Object[]{"Course version"})
            );
        }

        //check valid file
        String fileExtension = FilenameUtils.getExtension(assetUploadLectureDTO.getFile().getOriginalFilename());
        MultipartFile attachment = assetUploadLectureDTO.getFile();
        List<String> videoExtList = Arrays.asList(VIDEO_EXTENSION);
        if (assetUploadLectureDTO.getAssetType() == AssetEnum.VIDEO) {
            if (!videoExtList.contains(fileExtension)) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        Translator.toLocale("upload.invalid.video.format")
                );
            }

            if (attachment.getSize() < (15 * Math.pow(10, 6))) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        Translator.toLocale("upload.invalid.video.min.size")
                );
            }

            if (attachment.getSize() > (3 * Math.pow(10, 9))) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        Translator.toLocale("upload.invalid.video.max.size")
                );
            }
        }

        if (assetUploadLectureDTO.getAssetType() == AssetEnum.ATTACHMENT && videoExtList.contains(fileExtension)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    Translator.toLocale("upload.invalid.document.format")
            );
        }

        long strRandom = ThreadLocalRandom.current().nextLong(minRandom, maxRandom + 1);
        String s3Filename = new Date().getTime() + "_" + strRandom + "_" + attachment.getOriginalFilename();
        Long duration = assetUploadLectureDTO.getDuration();

        Folder courseFolder = courseVersion.getCourse().getFolders().stream().findFirst().orElse(null);

        File uploadAttachment = fileService.creatFileForInsert(attachment, s3Filename, courseFolder, attachment.getOriginalFilename(), false, duration);
        File fileAttachment = fileRepository.save(uploadAttachment);

        UploadS3DTO uploadS3DTO = UploadS3DTO.builder()
                .bucketName(bucketName)
                .s3FileName(s3Filename)
                .multipartFile(attachment)
                .fileId(fileAttachment.getId())
                .build();

        log.info("Add attachment to queue............................" + s3Filename);
        eventBus.notify(ConsumerEnum.UPLOADS3, Event.wrap(uploadS3DTO));
        log.info("Finish add attachment to queue......................" + s3Filename);

        File uploadAttachmentBackup = fileService.creatFileForInsert(attachment, s3Filename, null, attachment.getOriginalFilename(), true, duration);
        File fileAttachmentBackup = fileRepository.save(uploadAttachmentBackup);

        Asset asset = Asset.builder()
                .file(uploadAttachmentBackup)
                .asset_type(assetUploadLectureDTO.getAssetType())
                .createdBy(userId)
                .lecture(lecture).build();

        assetRepository.save(asset);

        AssetDetailDTO assetDetailDTO = modelMapper.map(asset, AssetDetailDTO.class);

        UploadS3DTO uploadS3DTOBackup = UploadS3DTO.builder()
                .bucketName(bucketBackupName)
                .s3FileName(s3Filename)
                .multipartFile(attachment)
                .fileId(fileAttachmentBackup.getId())
                .build();

        log.info("Add attachment_backup to queue............................" + s3Filename);
        eventBus.notify(ConsumerEnum.UPLOADS3, Event.wrap(uploadS3DTOBackup));
        log.info("Finish add attachment_backup to queue......................" + s3Filename);

        return assetDetailDTO;
    }

    @Override
    public AssetDetailDTO uploadAttachmentFromWareHouse(AssetUploadLectureFromWareHouseDTO assetUploadLectureDTO) {
        Lecture lecture = detail(assetUploadLectureDTO.getLectureId());
        Long userId = UserAuthenticationInfo.getUserAuthenticationInfo().getId();
        CourseVersion courseVersion = courseVersionRepository.findById(assetUploadLectureDTO.getCourseId()).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    Translator.toLocale("resource.not_found", new Object[]{"Course version"})
            );
        }

        File file = fileRepository.findById(assetUploadLectureDTO.getFileId()).orElse(null);

        if (file == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    Translator.toLocale("resource.not_found", new Object[]{"File"})
            );
        }

        String desName = new Date().getTime() + "_" + file.getName();

        awsS3ClientService.copyFile(bucketName, file.getObjectKey(), bucketBackupName, desName);

        File copyFile = File.builder()
                .folder(null)
                .name(file.getName())
                .objectKey(desName)
                .url(s3UrlBackup + "/" + desName)
                .fileType(file.getFileType())
                .createdBy(file.getCreatedBy())
                .fileExtension(file.getFileExtension())
                .fileSize(file.getFileSize())
                .status(file.getStatus())
                .duration(file.getDuration())
                .uploadStatus(file.getUploadStatus())
                .build();
        fileRepository.save(copyFile);

        Asset asset = Asset.builder()
                .file(copyFile)
                .createdBy(userId)
                .asset_type(assetUploadLectureDTO.getAssetType())
                .lecture(lecture).build();

        assetRepository.save(asset);

        AssetDetailDTO assetDetailDTO = modelMapper.map(asset, AssetDetailDTO.class);

        return assetDetailDTO;
    }
}
