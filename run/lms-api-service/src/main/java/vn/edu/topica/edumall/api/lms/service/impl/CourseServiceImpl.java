package vn.edu.topica.edumall.api.lms.service.impl;

import net.bytebuddy.asm.Advice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.api.lms.repository.*;
import vn.edu.topica.edumall.api.lms.service.CourseService;
import vn.edu.topica.edumall.api.lms.utility.NormalizeStringUtility;
import vn.edu.topica.edumall.api.lms.utility.UserAuthenticationInfo;
import vn.edu.topica.edumall.data.enumtype.*;
import vn.edu.topica.edumall.data.model.*;
import vn.edu.topica.edumall.locale.config.Translator;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    NormalizeStringUtility normalizeStringUtility;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    CourseVersionRepository courseVersionRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    FolderServiceImpl folderService;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    AssetRepository assetRepository;

    @Override
    public CourseDetailDTO createCourse(CreateCourseDTO createCourseDTO) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(createCourseDTO.getSubCategoryId());
        if (!subCategory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("subcategories.notfound"));
        }

        UserPrincipal user = UserAuthenticationInfo.getUserAuthenticationInfo();
        Long userId = user.getId();

        Teacher teacher = teacherRepository.getTeacherByUserId(userId);
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("teacher.notfound"));
        }

        Course course = saveCourse(createCourseDTO, userId);

        CourseVersion courseVersion = saveCourseVersion(createCourseDTO, course, userId);

        Chapter chapter = createChapterForCourse(courseVersion, userId);

        subCategory.get().addCourseVersionToSubCategory(courseVersion);

        teacher.addCourseToTeacher(course);

        Folder rootFolder = folderRepository.getFolderByUserId(userId);
        if (rootFolder == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("folder.root.of.user.notfound"), null);
        }

        Folder folderOfCourse = createFolderForCourse(rootFolder, createCourseDTO, course);

        SubCategoryDTO subCategoryDTO = modelMapper.map(subCategory.get(), SubCategoryDTO.class);
        List<SubCategoryDTO> subCategoryDTOList = new ArrayList<>();
        subCategoryDTOList.add(subCategoryDTO);

        CourseDetailDTO courseDetailDTO = modelMapper.map(createCourseDTO, CourseDetailDTO.class);
        courseDetailDTO.setId(course.getId());
        courseDetailDTO.setCourseVersionId(courseVersion.getId());
        courseDetailDTO.setChapterDefaultId(chapter.getId());
        courseDetailDTO.setSubCategoryDTOList(subCategoryDTOList);
        courseDetailDTO.setCourseFolderId(folderOfCourse.getId());
        return courseDetailDTO;
    }

    private Course saveCourse(CourseDTO courseDTO, Long userId) {
        Course course = Course.builder().build();

        course.setAliasName(normalizeStringUtility.normalize(courseDTO.getName()));
        course.setCreatedBy(userId);
        return courseRepository.save(course);

    }

    public CourseVersion saveCourseVersion(CreateCourseDTO createCourseDTO, Course course, Long userId) {
        CourseVersion courseVersion = modelMapper.map(createCourseDTO, CourseVersion.class);
        courseVersion.setId(0L);
        courseVersion.setStatus(CourseVersionStatusEnum.CREATING);
        courseVersion.setCourse(course);
        courseVersion.setCreatedBy(userId);
        courseVersion.setStatusOfficial(CourseVersionStatusOfficialEnum.OFFICIAL);

        return courseVersionRepository.save(courseVersion);
    }

    public Folder createFolderForCourse(Folder rootFolder, CreateCourseDTO createCourseDTO, Course course) {
        Folder folderDb = Folder.builder().build();
        folderDb.setParentFolder(rootFolder);
        folderDb.setName(createCourseDTO.getName());
        folderDb.setCourse(course);
        return folderService.createFolder(folderDb);
    }

    public Folder createFolderForCourse(Folder rootFolder, String courseName, Course course, Long userId) {
        Folder folder = Folder.builder().build();
        folder.setParentFolder(rootFolder);
        folder.setName(courseName);
        folder.setCourse(course);

        Optional<Folder> currentFolder = folderRepository.findById(folder.getParentFolder().getId());
        if (!currentFolder.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("folder.notfound"));
        }

        folder.setCreatedBy(userId);

        return folderRepository.save(folder);
    }

    public Chapter createChapterForCourse(CourseVersion courseVersion, Long userId) {
        Chapter chapter = Chapter.builder().build();
        chapter.setCourseVersion(courseVersion);
        chapter.setChapterOrder(1);
        chapter.setCreatedBy(userId);
        return chapterRepository.save(chapter);
    }

    @Override
    public boolean updateCourse(Course course, UpdateCourseDTO updateCourseDTO) {
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }

        if (updateCourseDTO.getStatus() != null) {
            course.setStatus(updateCourseDTO.getStatus());
        }

        if (updateCourseDTO.getPublishedAt() != null) {
            course.setPublishedAt(updateCourseDTO.getPublishedAt());
        }

        if (updateCourseDTO.getCourseVersion() != null) {
            course.setCourseVersion(updateCourseDTO.getCourseVersion());
        }

        if (updateCourseDTO.getPrice() != null) {
            course.setPrice(updateCourseDTO.getPrice());
        }
        if (updateCourseDTO.getIsRunMarketing() != null) {
            course.setIsRunMarketing(updateCourseDTO.getIsRunMarketing());
        }

        return true;
    }

    @Override
    public CourseDetailDTO createFullCourse(CreateFullCourseDTO createFullCourseDTO) {
        Optional<SubCategory> subCategory = subCategoryRepository.findById(createFullCourseDTO.getSubCategoryId());
        if (!subCategory.isPresent()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("subcategories.notfound"));
        }
        Long userId = createFullCourseDTO.getUserId();

        Teacher teacher = teacherRepository.getTeacherByUserId(userId);
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("teacher.notfound"));
        }

        CreateCourseDTO createCourseDTO = new CreateCourseDTO();
        createCourseDTO.setName(createFullCourseDTO.getName());

        Course course = saveCourse(createCourseDTO, userId);
        course.setKelleyId(createFullCourseDTO.getKelleyId());

        CourseVersion courseVersion = CourseVersion.builder()
                .course(course)
                .createdBy(userId)
                .statusOfficial(CourseVersionStatusOfficialEnum.OFFICIAL)
                .name(createFullCourseDTO.getName())
                .benefit(createFullCourseDTO.getBenefit())
                .target(createFullCourseDTO.getTarget())
                .requirement(createFullCourseDTO.getRequirement())
                .shortDescription(createFullCourseDTO.getShortDescription())
                .longDescription(createFullCourseDTO.getLongDescription())
                .step(2L)
                .courseCode(createFullCourseDTO.getCourseCode())
                .thumbnailImg(createFullCourseDTO.getThumbnailImg())
                .status(createFullCourseDTO.getStatus())
                .statusApprove(ApproveStatusEnum.PASS)
                .build();

        CourseVersion courseVersionSaved = courseVersionRepository.save(courseVersion);


        subCategory.get().addCourseVersionToSubCategory(courseVersionSaved);

        teacher.addCourseToTeacher(course);

        Folder rootFolder = folderRepository.getFolderByUserId(userId);
        if (rootFolder == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("folder.root.of.user.notfound"), null);
        }

        createFolderForCourse(rootFolder, createFullCourseDTO.getName(), course, userId);

        List<ChapterForMigrateDTO> chapterForMigrateDTOList = createFullCourseDTO.getChapters();

        int chapterOrder = 0;
        for (ChapterForMigrateDTO chapterDTO : chapterForMigrateDTOList) {
            chapterOrder++;
            Chapter chapter = Chapter.builder()
                    .title(chapterDTO.getTitle())
                    .courseVersion(courseVersionSaved)
                    .chapterOrder(chapterOrder)
                    .createdBy(userId)
                    .build();
            Chapter chapterSaved = chapterRepository.save(chapter);

            List<QuizDTO> quizDTOList = chapterDTO.getQuizzes();

            List<LectureForMigrateDTO> lectureForMigrateDTOList = chapterDTO.getLectures();

            for (LectureForMigrateDTO lectureDTO : lectureForMigrateDTOList) {
                Lecture lecture = Lecture.builder()
                        .title(lectureDTO.getTitle())
                        .createdBy(userId)
                        .chapter(chapterSaved)
                        .build();
                Lecture lectureSaved = lectureRepository.save(lecture);

                List<AssetForMigrateDTO> assetForMigrateDTOList = lectureDTO.getAssets();
                for (AssetForMigrateDTO assetDTO : assetForMigrateDTOList) {
                    FileForMigrateDTO fileForMigrateDTO = assetDTO.getFile();
                    File file = File.builder()
                            .fileType(fileForMigrateDTO.getFileType())
                            .name(fileForMigrateDTO.getName())
                            .url(fileForMigrateDTO.getUrl())
                            .objectKey(fileForMigrateDTO.getObjectKey())
                            .fileExtension(fileForMigrateDTO.getFileExtension())
                            .fileSize(fileForMigrateDTO.getFileSize())
                            .duration(fileForMigrateDTO.getDuration())
                            .createdBy(userId)
                            .status(FileStatusEnum.AVAILABILITY)
                            .uploadStatus(UploadFileStatusEnum.SUCCESS)
                            .build();
                    File fileSaved = fileRepository.save(file);
                    Asset asset = Asset.builder()
                            .asset_type(assetDTO.getAssetType())
                            .transcode_url(assetDTO.getTranscodeUrl())
                            .file(fileSaved)
                            .lecture(lectureSaved)
                            .build();
                    assetRepository.save(asset);
                }
            }
        }

        CourseDetailDTO courseDetailDTO = new CourseDetailDTO();
        courseDetailDTO.setId(course.getId());
        courseDetailDTO.setCourseVersionId(courseVersionSaved.getId());
        return courseDetailDTO;
    }

}
