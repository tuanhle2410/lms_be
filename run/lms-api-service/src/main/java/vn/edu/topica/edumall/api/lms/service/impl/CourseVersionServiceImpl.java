package vn.edu.topica.edumall.api.lms.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.bus.Event;
import reactor.bus.EventBus;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.api.lms.repository.*;
import vn.edu.topica.edumall.api.lms.service.*;
import vn.edu.topica.edumall.api.lms.upload.queue.ConsumerEnum;
import vn.edu.topica.edumall.api.lms.utility.AnalyticStandard;
import vn.edu.topica.edumall.api.lms.utility.NormalizeStringUtility;
import vn.edu.topica.edumall.api.lms.utility.ThreadCheckFileUploadSuccess;
import vn.edu.topica.edumall.api.lms.utility.UserAuthenticationInfo;
import vn.edu.topica.edumall.data.enumtype.*;
import vn.edu.topica.edumall.data.model.*;
import vn.edu.topica.edumall.locale.config.Translator;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@Transactional
public class CourseVersionServiceImpl implements CourseVersionService {
    @Autowired
    CourseVersionRepository courseVersionRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    EmailService emailService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SubCategoryRepository subCategoryRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    AssetRepository assetRepository;

    @Value("${furion.uploadS3Failed.templateCode}")
    String templateCode;

    @Value("${furion.uploadS3Failed.sender.email}")
    String senderEmail;

    @Value("${furion.uploadS3Failed.sender.name}")
    String senderName;

    @Value("${furion.uploadS3Failed.senderAddress}")
    String senderAddress;

    @Value("${furion.uploadS3Failed.senderName}")
    String senderNameFooter;

    @Value("${furion.uploadS3Failed.senderCity}")
    String senderCity;

    @Value("${furion.uploadS3Failed.senderState}")
    String senderState;

    @Value("${furion.uploadS3Failed.senderZip}")
    String senderZip;

    @Value("${furion.uploadS3Failed.cc}")
    String furionCC;

    @Value("${furion.uploadS3Failed.subject}")
    String subjectEmail;

    @Value("${furion.publish-course.subjectNotifyPublishCourse}")
    String subjectEmailNotifyAfterPublishCourse;

    @Value("${furion.publish-course.templateCodeNotifyPublishCourse}")
    String templateCodeNotifyAfterPublishCourse;

    @Value("${furion.disappear-course.subjectDisappearCourse}")
    String subjectEmailDisappearCourse;

    @Value("${furion.disappear-course.templateCodeDisappearCourse}")
    String templateCodeDisappearCourse;

    @Value("${furion.uploadS3Failed.timeSleep}")
    Long timeSleepEmail;

    @Value("${video.duration}")
    Long videoDuration;

    @Value("${video.number}")
    Long videoNumber;

    @Value("${course.version.step1}")
    Long courseVersionStep1;

    @Value("${course.version.step2}")
    Long courseVersionStep2;

    @Value("${course.min-random-course-code}")
    Long minRandomCourseCode;

    @Value("${course.max-random-course-code}")
    Long maxRandomCourseCode;

    @Value("${course.start-course-code}")
    String startCourseCode;

    @Value("${kelley.url.save-course}")
    String kelleyUrlSaveCourse;

    @Value("${kelley.url.publish-course}")
    String kelleyUrlPublishCourse;

    @Value("${kelley.url.disappear-course}")
    String kelleyUrlDisappearCourse;

    @Value("${media-tool.url.analysic-video}")
    String mediaToolUrl;

    @Value("${is.url.process-after-tool-validate}")
    String apiProcessAfterToolValidate;

    @Value("${frontend-react.url.view-approve-result.front}")
    String frontendUrlViewApproveResultFront;

    @Value("${frontend-react.url.view-approve-result.behind}")
    String frontendUrlViewApproveResultBehind;

    @Autowired
    CourseServiceImpl courseServiceImpl;

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    NormalizeStringUtility normalizeStringUtility;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    EventBus eventBus;

    @Value("${thread.check-file-upload.time-sleep}")
    Long timeSleep;

    @Autowired
    MediaToolService mediaToolService;

    @Autowired
    CourseService courseService;

    @Autowired
    TeacherService teacherService;

    @Override
    public List<CourseVersionDTO> getByUserIdAndStatus(long userId, int status, int startIndex) {
        List<CourseVersion> listCv = courseVersionRepository.findAllByStatusAndUserIdGroupByCodeOrderByCreatedAtDesc(userId, status, startIndex, 10);
        List<CourseVersionDTO> listDTO = new ArrayList<>();

        for (CourseVersion courseVersion : listCv) {
            CourseVersionDTO courseVersionDTO = modelMapper.map(courseVersion, CourseVersionDTO.class);
            listDTO.add(courseVersionDTO);
        }

        return listDTO;
    }

    @Override
    public List<CourseVersionDTO> getByUserId(long userId, int page) {
        List<CourseVersion> listCv = courseVersionRepository.findAllByUserIdGroupByCodeOrderByCreatedAtDesc(userId, page, 10);
        List<CourseVersionDTO> listDTO = new ArrayList<>();

        for (CourseVersion courseVersion : listCv) {
            CourseVersionDTO courseVersionDTO = modelMapper.map(courseVersion, CourseVersionDTO.class);
            listDTO.add(courseVersionDTO);
        }

        return listDTO;
    }

    @Override
    public Object getCourserVersionDetail(Long id, Long step) {
        CourseVersion courseVersion = courseVersionRepository.findById(id).orElse(null);

        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }

        if (step == null) {
            CourseFilterDetailDTO courseFilterDetailDTO = CourseFilterDetailDTO.builder().build();
            courseFilterDetailDTO.setCurrentStep(courseVersion.getStep());
            courseFilterDetailDTO.setApproveStatus(courseVersion.getStatusApprove());
            courseFilterDetailDTO.setDataStep1(getCourseDetailStep1(courseVersion));
            if (courseVersion.getStatus().equals(CourseVersionStatusEnum.APPROVED) ||
                    courseVersion.getStatus().equals(CourseVersionStatusEnum.PUBLISHED) ||
                    courseVersion.getStatus().equals(CourseVersionStatusEnum.DISAPPEAR)
            ) {
                CourseVersion newCourseVersionForCourse = makeCopyNewCourseVersion(courseVersion);
                courseFilterDetailDTO.getDataStep1().setCourseVersionId(newCourseVersionForCourse.getId());
                ChapterAndLectureDTO chapterAndLectureDTO = getChapterAndLecture(newCourseVersionForCourse);
                courseFilterDetailDTO.setDataStep2(chapterAndLectureDTO);
            } else {
                courseFilterDetailDTO.setDataStep2(getChapterAndLecture(courseVersion));
            }
            return courseFilterDetailDTO;
        } else {
            if (step.equals(courseVersionStep1)) {
                return getCourseDetailStep1(courseVersion);
            } else if (step.equals(courseVersionStep2)) {
                if (courseVersion.getStatus().equals(CourseVersionStatusEnum.APPROVED) ||
                        courseVersion.getStatus().equals(CourseVersionStatusEnum.PUBLISHED) ||
                        courseVersion.getStatus().equals(CourseVersionStatusEnum.DISAPPEAR)
                ) {
                    CourseVersion newCourseVersionForCourse = makeCopyNewCourseVersion(courseVersion);
                    return getChapterAndLecture(newCourseVersionForCourse);
                }
                return getChapterAndLecture(courseVersion);
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("step.not.define"), null);
    }

    private CourseDetailDTO getCourseDetailStep1(CourseVersion courseVersion) {
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }

        CourseDetailDTO courseDetailDTO = modelMapper.map(courseVersion, CourseDetailDTO.class);
        courseDetailDTO.setStep(courseVersionStep1);
        courseDetailDTO.setCourseVersionId(courseVersion.getId());

        Course course = courseVersion.getCourse();
        if (course != null) {
            courseDetailDTO.setId(course.getId());
        }

        List<SubCategory> subCategoryList = courseVersion.getSubCategories();
        List<SubCategoryDTO> subCategoryDTOList = subCategoryList.stream()
                .map(subCategory -> modelMapper.map(subCategory, SubCategoryDTO.class))
                .collect(Collectors.toList());
        courseDetailDTO.setSubCategoryDTOList(subCategoryDTOList);

        return courseDetailDTO;
    }

    @Override
    public int getTotalPage(long userId, Integer status) {
        if (status == null) {
            int result = (int) Math.ceil((double) courseVersionRepository.countByCourseCodeAndUserId(userId) / 10);
            return result;
        } else {
            int result = (int) Math.ceil((double) courseVersionRepository.countByCourseCodeAndUserIdAndStatus(userId, status) / 10);
            return result;
        }
    }

    @Override
    public CountByStatusAndUserIdDTO countByStatusAndUserId(long userId) {
        List<CountByStatusAndUserIdRepository> listCount = courseVersionRepository.countByStatusAndUserId(userId);
        CountByStatusAndUserIdDTO result = CountByStatusAndUserIdDTO.builder().data(listCount).build();
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public CourseVersion makeCopyNewCourseVersion(CourseVersion courseVersion) {
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }

        Course course = courseVersion.getCourse();
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"Course"}), null);
        }
        List<SubCategory> subCategoryList = courseVersion.getSubCategories();
        if (subCategoryList == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"Subcategories"}), null);
        }

        CourseVersion newCourseVersion = createNewCourseVersion(courseVersion, course);

        addNewCourseVersionToSubCate(newCourseVersion, subCategoryList);

        Map<Long, Chapter> mapChapterIdOldAndChapterNew = addNewChapterForCourse(courseVersion, newCourseVersion);

        Map<Long, Lecture> mapLectureIdOldAndLectureNew = addNewLectureForChapter(mapChapterIdOldAndChapterNew);

        addNewAssetForLecture(mapLectureIdOldAndLectureNew);

        return newCourseVersion;
    }

    public CourseVersion createNewCourseVersion(CourseVersion courseVersion, Course course) {
        CreateCourseDTO createCourseDTO = modelMapper.map(courseVersion, CreateCourseDTO.class);
        CourseVersion courseVersionDb = modelMapper.map(createCourseDTO, CourseVersion.class);
        courseVersionDb.setStatus(CourseVersionStatusEnum.CREATING);
        courseVersionDb.setStatusOfficial(CourseVersionStatusOfficialEnum.TEMP);
        courseVersionDb.setCourseCode(courseVersion.getCourseCode());
        courseVersionDb.setCourse(course);
        return courseVersionRepository.save(courseVersionDb);
    }

    public void addNewCourseVersionToSubCate(CourseVersion courseVersion, List<SubCategory> subCategoryList) {
        for (SubCategory subCate : subCategoryList) {
            subCate.addCourseVersionToSubCategory(courseVersion);
        }
    }

    public Map<Long, Chapter> addNewChapterForCourse(CourseVersion oldCourseVer, CourseVersion newCourseVer) {
        List<Chapter> chapterList = oldCourseVer.getChapters();
        Map<Long, Chapter> mapChapterIdOldAndChapterNew = new HashMap<>();
        List<Chapter> chapterListSaved = new ArrayList<>();
        if (chapterList != null && !chapterList.isEmpty()) {
            for (Chapter chapter : chapterList) {
                ChapterDTO chapterDTO = modelMapper.map(chapter, ChapterDTO.class);
                Chapter chapterDb = modelMapper.map(chapterDTO, Chapter.class);
                chapterDb.setId(0L);
                Chapter chapterSaved = chapterRepository.save(chapterDb);
                chapterSaved.setCourseVersion(newCourseVer);
                chapterListSaved.add(chapterSaved);
                mapChapterIdOldAndChapterNew.put(chapter.getId(), chapterSaved);
            }
        }
        newCourseVer.setChapters(chapterListSaved);
        return mapChapterIdOldAndChapterNew;
    }

    public Map<Long, Lecture> addNewLectureForChapter(Map<Long, Chapter> mapChapterIdOldAndChapterNew) {
        List<Long> chapterIdOldList = new ArrayList<>(mapChapterIdOldAndChapterNew.keySet());
        Map<Long, Lecture> mapIdLectureOldAndLectureNew = new HashMap<>();
        Map<Long, Lecture> mapIdLectureOldAndLectureOld = new HashMap<>();
        if (!chapterIdOldList.isEmpty()) {
            List<Chapter> chapterOldList = chapterRepository.getLectureByIdList(chapterIdOldList);
            List<Lecture> lectureList = lectureRepository.getLectureByChapter(chapterOldList);
            if (!lectureList.isEmpty()) {
                for (Lecture lecture : lectureList) {
                    LectureDTO lectureDTO = modelMapper.map(lecture, LectureDTO.class);
                    Lecture lectureDb = modelMapper.map(lectureDTO, Lecture.class);
                    lectureDb.setId(0L);
                    Lecture lectureSaved = lectureRepository.save(lectureDb);

                    Chapter chapterNew = mapChapterIdOldAndChapterNew.get(lecture.getChapter().getId());
                    lectureSaved.setChapter(chapterNew);
                    mapIdLectureOldAndLectureNew.put(lecture.getId(), lectureSaved);
                    mapIdLectureOldAndLectureOld.put(lecture.getId(), lecture);
                }

                //set lecture into chapter
                for (Long chapterId : mapChapterIdOldAndChapterNew.keySet()) {
                    List<Lecture> lectureNewList = new ArrayList<>();
                    for (Long lectureId : mapIdLectureOldAndLectureOld.keySet()) {
                        if (mapIdLectureOldAndLectureOld.get(lectureId).getChapter().getId().equals(chapterId)) {
                            lectureNewList.add(mapIdLectureOldAndLectureNew.get(lectureId));
                        }
                    }
                    Chapter chapterNew = mapChapterIdOldAndChapterNew.get(chapterId);
                    chapterNew.setLectures(lectureNewList);
                }
            }
        }
        return mapIdLectureOldAndLectureNew;
    }

    public void addNewAssetForLecture(Map<Long, Lecture> mapLectureIdOldAndLectureNew) {
        List<Long> lectureIdOldList = new ArrayList<>(mapLectureIdOldAndLectureNew.keySet());
        Long userId = UserAuthenticationInfo.getUserAuthenticationInfo().getId();

        Map<Long, Asset> mapIdAssetOldAndAssetNew = new HashMap<>();
        Map<Long, Asset> mapIdAssetOldAndAssetOld = new HashMap<>();

        if (!lectureIdOldList.isEmpty()) {
            List<Lecture> lectureList = lectureRepository.getLectureByListId(lectureIdOldList);
            List<Asset> assetList = assetRepository.getAssetByLectureList(lectureList);
            if (!assetList.isEmpty()) {
                for (Asset asset : assetList) {
                    File file = asset.getFile();
                    FileDetailDTO fileDTO = modelMapper.map(file, FileDetailDTO.class);
                    File fileDb = modelMapper.map(fileDTO, File.class);
                    fileDb.setId(0L);
                    File fileSaved = fileRepository.save(fileDb);
                    Lecture lectureNew = mapLectureIdOldAndLectureNew.get(asset.getLecture().getId());

                    Asset assetDb = Asset.builder().build();
                    assetDb.setTranscode_url(asset.getTranscode_url());
                    assetDb.setAsset_type(asset.getAsset_type());
                    assetDb.setFile(fileSaved);
                    assetDb.setLecture(lectureNew);
                    assetDb.setCreatedBy(userId);
                    Asset assetSaved = assetRepository.save(assetDb);

                    mapIdAssetOldAndAssetNew.put(asset.getId(), assetSaved);
                    mapIdAssetOldAndAssetOld.put(asset.getId(), asset);

                }

                for (Long lectureId : mapLectureIdOldAndLectureNew.keySet()) {
                    List<Asset> assetNewList = new ArrayList<>();
                    for (Long assetId : mapIdAssetOldAndAssetOld.keySet()) {
                        if (mapIdAssetOldAndAssetOld.get(assetId).getLecture().getId().equals(lectureId)) {
                            assetNewList.add(mapIdAssetOldAndAssetNew.get(assetId));
                        }
                    }
                    Lecture lectureNew = mapLectureIdOldAndLectureNew.get(lectureId);
                    lectureNew.setAssets(assetNewList);
                }
            }
        }
    }

    private ChapterAndLectureDTO getChapterAndLecture(CourseVersion courseVersion) {
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("course.version.notfound"), null);
        }

        Course course = courseVersion.getCourse();
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"Course"}), null);
        }
        List<Folder> folderList = course.getFolders();
        if (folderList == null || folderList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"Folder"}), null);
        }

        List<ChapterDTO> chapterDTOList = new ArrayList<>();
        List<Chapter> chapterList = courseVersion.getChapters();
        for (Chapter chapter : chapterList) {
            ChapterDTO chapterDTO = modelMapper.map(chapter, ChapterDTO.class);

            List<LectureHasVideoDTO> lectureHasVideoDTOList = chapterDTO.getLectures();
            Map<Long, LectureHasVideoDTO> mapLectureHasVideoDTO = new HashMap<>();
            for (LectureHasVideoDTO lecHasVideo : lectureHasVideoDTOList) {
                mapLectureHasVideoDTO.put(lecHasVideo.getId(), lecHasVideo);
            }

            List<Lecture> lectureList = chapter.getLectures();
            for (Lecture lecture : lectureList) {
                List<Asset> assetListOfLecture = lecture.getAssets();
                LectureHasVideoDTO lectureHasVideoDTO = mapLectureHasVideoDTO.get(lecture.getId());
                lectureHasVideoDTO.setHasVideo(!isEmptyVideoInLecture(assetListOfLecture));
            }
            chapterDTOList.add(chapterDTO);
        }

        ChapterAndLectureDTO chapterAndLectureDTO = ChapterAndLectureDTO.builder().build();
        chapterAndLectureDTO.setCourseVersionId(courseVersion.getId());
        chapterAndLectureDTO.setCourseFolderId(folderList.get(0).getId());
        chapterAndLectureDTO.setChapters(chapterDTOList);

        return chapterAndLectureDTO;
    }

    @Override
    public ValidateCourseDTO validateCourseVersion(Long id) {

        StatusOfficialDTO statusOfficialDTO = StatusOfficialDTO.builder()
                .courseVersionId(id)
                .status(CourseVersionStatusOfficialEnum.OFFICIAL)
                .build();
        updateStatusOfficial(statusOfficialDTO);

        CourseVersion courseVersion = courseVersionRepository.findById(id).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("course.version.notfound"), null);
        }

        ValidateCourseDTO validateCourseDTO = ValidateCourseDTO.builder().build();
        validateCourseDTO.setCourseVersionId(id);

        List<Chapter> chapterList = courseVersion.getChapters();
        if (chapterList.isEmpty()) {
            validateCourseDTO.setNotHasChapter(true);
        }
        DurationAndNumberVideoRepository durationAndNumberVideoRepository = courseVersionRepository.getDurationAndNumberVideo(id);
        if (durationAndNumberVideoRepository == null) {
            validateCourseDTO.setNotEnoughVideoNumber(true);
            validateCourseDTO.setNotEnoughVideoDuration(true);
        } else {
            if (durationAndNumberVideoRepository.getDuration() / 60 < videoDuration) {
                validateCourseDTO.setNotEnoughVideoDuration(true);
            }
            if (durationAndNumberVideoRepository.getFileNumber() < videoNumber) {
                validateCourseDTO.setNotEnoughVideoNumber(true);
            }
        }

        List<Lecture> lectureList = lectureRepository.getLectureByChapter(chapterList);
        validateCourseDTO.setLectureErrorDetailDTOList(getLectureErrorDetail(lectureList));
        validateCourseDTO.setChapterErrorDetailDTOList(getChapterErrorDetail(chapterList));

        if (validateCourseDTO.isNotHasChapter() ||
                validateCourseDTO.isNotEnoughVideoNumber() ||
                validateCourseDTO.isNotEnoughVideoDuration() ||
                !validateCourseDTO.getChapterErrorDetailDTOList().isEmpty() ||
                !validateCourseDTO.getLectureErrorDetailDTOList().isEmpty()) {
            validateCourseDTO.setError(true);
        }

        return validateCourseDTO;
    }

    public void updateCourseVersionStatus(Long id) {
        CourseVersion courseVersion = courseVersionRepository.findById(id).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }
        courseVersion.setStatus(CourseVersionStatusEnum.WAITING_APPROVE);
    }

    private List<ChapterErrorDetailDTO> getChapterErrorDetail(List<Chapter> chapterList) {
        List<ChapterErrorDetailDTO> chapterErrorDetailDTOList = new ArrayList<>();
        int countError = 0;
        for (Chapter chapter : chapterList) {
            ChapterErrorDetailDTO chapterErrorDetailDTO = ChapterErrorDetailDTO.builder().build();
            if (chapter.getTitle() == null || chapter.getTitle().trim().isEmpty()) {
                countError++;
                chapterErrorDetailDTO.setChapterNameIsNull(true);
            }
            if (chapter.getLectures().isEmpty()) {
                countError++;
                chapterErrorDetailDTO.setNotHasLecture(true);
            }
            if (countError != 0) {
                chapterErrorDetailDTO.setChapterId(chapter.getId());
                chapterErrorDetailDTOList.add(chapterErrorDetailDTO);
            }
        }
        return chapterErrorDetailDTOList;
    }

    private List<LectureErrorDetailDTO> getLectureErrorDetail(List<Lecture> lectureList) {
        List<LectureErrorDetailDTO> lectureErrorDetailDTOList = new ArrayList<>();
        int countError = 0;
        for (Lecture lecture : lectureList) {
            LectureErrorDetailDTO lectureErrorDetailDTO = LectureErrorDetailDTO.builder().build();
            if (lecture.getTitle() == null || lecture.getTitle().trim().isEmpty()) {
                countError++;
                lectureErrorDetailDTO.setLectureNameIsNull(true);
            }
            if (isEmptyVideoInLecture(lecture.getAssets())) {
                countError++;
                lectureErrorDetailDTO.setNotHasVideo(true);
            }
            if (countError != 0) {
                lectureErrorDetailDTO.setLectureId(lecture.getId());
                lectureErrorDetailDTOList.add(lectureErrorDetailDTO);
            }
        }
        return lectureErrorDetailDTOList;
    }

    private boolean isEmptyVideoInLecture(List<Asset> assetList) {
        if (assetList.isEmpty()) {
            return true;
        }
        Map<AssetEnum, Boolean> assetTypeMap = new HashMap<>();
        for (Asset asset : assetList) {
            assetTypeMap.put(asset.getAsset_type(), true);
        }
        return !assetTypeMap.containsKey(AssetEnum.VIDEO);
    }

    @Override
    public CourseDetailDTO updateCourseVersion(Long id, CreateCourseDTO createCourseDTO) {
        CourseVersion courseVersion = courseVersionRepository.findById(id).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }

        SubCategory subCategory = subCategoryRepository.findById(createCourseDTO.getSubCategoryId()).orElse(null);
        if (subCategory == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"Subcategory"}), null);
        }

        Course course = courseVersion.getCourse();
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"Course"}), null);
        }
        course.setAliasName(normalizeStringUtility.normalize(createCourseDTO.getName().trim()));
        List<Folder> folderList = course.getFolders();
        if (folderList == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[]{"Folders"}), null);
        }
        Folder folder = folderList.get(0);
        folder.setName(createCourseDTO.getName().trim());

        courseVersion.setName(createCourseDTO.getName().trim());
        courseVersion.setBenefit(createCourseDTO.getBenefit());
        courseVersion.setTarget(createCourseDTO.getTarget());
        courseVersion.setRequirement(createCourseDTO.getRequirement());
        courseVersion.setShortDescription(createCourseDTO.getShortDescription());
        courseVersion.setLongDescription(createCourseDTO.getLongDescription());
        courseVersion.setStep(createCourseDTO.getStep());
        courseVersion.setThumbnailImg(createCourseDTO.getThumbnailImg());
        courseVersion.setStatus(CourseVersionStatusEnum.CREATING);

        List<Long> subCategoryIdList = new ArrayList<>();
        List<SubCategory> subCategoryList = courseVersion.getSubCategories();
        for (SubCategory subCate : subCategoryList) {
            subCategoryIdList.add(subCate.getId());
        }
        if (!subCategoryIdList.contains(createCourseDTO.getSubCategoryId())) {
            subCategory.addCourseVersionToSubCategory(courseVersion);
        }

        List<SubCategoryDTO> subCategoryDTOList = new ArrayList<>();
        subCategoryDTOList.add(modelMapper.map(subCategory, SubCategoryDTO.class));

        CourseDetailDTO courseDetailDTO = modelMapper.map(createCourseDTO, CourseDetailDTO.class);
        courseDetailDTO.setCourseVersionId(courseVersion.getId());
        courseDetailDTO.setCourseFolderId(folder.getId());
        courseDetailDTO.setSubCategoryDTOList(subCategoryDTOList);
        courseDetailDTO.setId(course.getId());
        return courseDetailDTO;
    }

    @Override
    public CourseForApproveDTO sendApprove(Long id) {
        CourseVersion courseVersion = courseVersionRepository.findById(id).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }
        courseVersion.setStatus(CourseVersionStatusEnum.WAITING_APPROVE);

        CourseForApproveDTO courseForApproveDTO = prepareCourseDTO(id);
        List<AssetDetailForApproveDTO> assetDetailDTOList = courseForApproveDTO.getAssetDetailDTO();

        List<File> videoList = new ArrayList<>();
        for (AssetDetailForApproveDTO asset : assetDetailDTOList) {
            if (asset.getAsset_type() == AssetEnum.VIDEO) {
                File file = asset.getFile();
                videoList.add(file);
            }
        }
        FilesAndCourseVerIdForMediaToolDTO fileAndCourseId = FilesAndCourseVerIdForMediaToolDTO.builder()
                .fileList(videoList)
                .courseVersionId(id)
                .build();

        //turn off when has media tool
        sendCourseToExternalService(courseForApproveDTO, id);
        //turn off when has media tool

//        turn on when has media tool
//        eventBus.notify(ConsumerEnum.PROCESS_SEND_TO_MEDIA_TOOL, Event.wrap(fileAndCourseId));
//        turn on when has media tool

        // check uploading s3 for sending email
        eventBus.notify(ConsumerEnum.PROCESS_SEND_EMAIL_WHEN_FAIL_UPLOAD_S3, Event.wrap(fileAndCourseId));

        return courseForApproveDTO;
    }

    @Override
    public ResultApproveStep1DTO getResultApproveStep1(Long courseVersionId) {
        CourseVersion courseVersion = courseVersionRepository.findById(courseVersionId).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }

        List<Chapter> chapterList = courseVersion.getChapters();

        List<ResultApproveChapterDTO> resultApproveChapterDTOList = new ArrayList<>();

        Map<MediaToolFieldEnum, ApproveStatusEnum> mapStatusSum = Stream.of(new Object[][]{
                {MediaToolFieldEnum.FRAME_WIDTH, ApproveStatusEnum.PASS},
                {MediaToolFieldEnum.FRAME_HEIGHT, ApproveStatusEnum.PASS},
                {MediaToolFieldEnum.BITRATE, ApproveStatusEnum.PASS},
                {MediaToolFieldEnum.FRAMERATE, ApproveStatusEnum.PASS},
                {MediaToolFieldEnum.CODENAME, ApproveStatusEnum.PASS},
                {MediaToolFieldEnum.FORMAT, ApproveStatusEnum.PASS},
                {MediaToolFieldEnum.AUDIO_BITRATE, ApproveStatusEnum.PASS},
                {MediaToolFieldEnum.AUDIO_CHANNEL, ApproveStatusEnum.PASS}
        }).collect(Collectors.toMap(data -> (MediaToolFieldEnum) data[0], data -> (ApproveStatusEnum) data[1]));

        for (Chapter chapter : chapterList) {
            List<Lecture> lectureList = chapter.getLectures();
            List<ResultApproveLectureDTO> resultApproveLectureDTOList = new ArrayList<>();
            for (Lecture lecture : lectureList) {
                List<Asset> assetList = lecture.getAssets();
                File file = null;
                for (Asset asset : assetList) {
                    if (asset.getAsset_type() == AssetEnum.VIDEO) {
                        file = asset.getFile();
                    }
                }

                ResultApproveLectureDTO resultApproveLectureDTO = modelMapper.map(lecture, ResultApproveLectureDTO.class);
                if (file != null) {
                    MediaToolResult mediaToolResult = file.getMediaToolResult();
                    ResultApproveMediaToolDTO resultApproveMediaToolDTO = modelMapper.map(mediaToolResult, ResultApproveMediaToolDTO.class);
                    setStatusSum(resultApproveMediaToolDTO, mapStatusSum);
                    resultApproveLectureDTO.setMediaToolResultDTO(resultApproveMediaToolDTO);
                }
                resultApproveLectureDTOList.add(resultApproveLectureDTO);
            }

            ResultApproveChapterDTO resultApproveChapterDTO = modelMapper.map(chapter, ResultApproveChapterDTO.class);
            resultApproveChapterDTO.setLectureDTO(resultApproveLectureDTOList);
            resultApproveChapterDTOList.add(resultApproveChapterDTO);

        }

        ResultApproveStep1DTO resultApproveStep1 = ResultApproveStep1DTO.builder()
                .chapterDTOList(resultApproveChapterDTOList)
                .frameWidth(mapStatusSum.get(MediaToolFieldEnum.FRAME_WIDTH))
                .frameHeight(mapStatusSum.get(MediaToolFieldEnum.FRAME_HEIGHT))
                .bitRate(mapStatusSum.get(MediaToolFieldEnum.BITRATE))
                .frameRate(mapStatusSum.get(MediaToolFieldEnum.FRAMERATE))
                .codeName(mapStatusSum.get(MediaToolFieldEnum.CODENAME))
                .format(mapStatusSum.get(MediaToolFieldEnum.FORMAT))
                .audioBitRate(mapStatusSum.get(MediaToolFieldEnum.AUDIO_BITRATE))
                .audioChannel(mapStatusSum.get(MediaToolFieldEnum.AUDIO_CHANNEL))
                .courseVersionThumbnailImg(courseVersion.getThumbnailImg())
                .courseVersionName(courseVersion.getName())
                .build();
        return resultApproveStep1;
    }

    @Override
    public ApproveResultDTO updateApproveResult(ApproveResultDTO approveResultDTO) {
        String courseCode = approveResultDTO.getCourseCode();
        List<CourseVersion> courseVersionList = courseVersionRepository.findByCourseCodeOrderByCreatedAtAsc(courseCode);

        if (courseVersionList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"List_Course_version"}), null);
        }
        CourseVersion courseVersion = courseVersionList.stream().reduce((first, second) -> second).get();

        courseVersion.setStatusApprove(approveResultDTO.getApproveStatusEnum());
        courseVersion.setLinkApproveResult(approveResultDTO.getLinkApproveResult());
        courseVersion.setStatus(CourseVersionStatusEnum.APPROVED);
        return approveResultDTO;
    }

    public void setStatusSum(ResultApproveMediaToolDTO resultApproveMediaToolDTO, Map<MediaToolFieldEnum, ApproveStatusEnum> mapStatusSum) {
        if (resultApproveMediaToolDTO.getVideoFrameWidth() == ApproveStatusEnum.FAILED) {
            mapStatusSum.put(MediaToolFieldEnum.FRAME_WIDTH, ApproveStatusEnum.FAILED);
        }
        if (resultApproveMediaToolDTO.getVideoFrameHeight() == ApproveStatusEnum.FAILED) {
            mapStatusSum.put(MediaToolFieldEnum.FRAME_HEIGHT, ApproveStatusEnum.FAILED);
        }
        if (resultApproveMediaToolDTO.getVideoBitRate() == ApproveStatusEnum.FAILED) {
            mapStatusSum.put(MediaToolFieldEnum.BITRATE, ApproveStatusEnum.FAILED);
        }
        if (resultApproveMediaToolDTO.getVideoFrameRate() == ApproveStatusEnum.FAILED) {
            mapStatusSum.put(MediaToolFieldEnum.FRAMERATE, ApproveStatusEnum.FAILED);
        }
        if (resultApproveMediaToolDTO.getVideoCodeName() == ApproveStatusEnum.FAILED) {
            mapStatusSum.put(MediaToolFieldEnum.CODENAME, ApproveStatusEnum.FAILED);
        }
        if (resultApproveMediaToolDTO.getVideoFormat() == ApproveStatusEnum.FAILED) {
            mapStatusSum.put(MediaToolFieldEnum.FORMAT, ApproveStatusEnum.FAILED);
        }
        if (resultApproveMediaToolDTO.getAudioBitRate() == ApproveStatusEnum.FAILED) {
            mapStatusSum.put(MediaToolFieldEnum.AUDIO_BITRATE, ApproveStatusEnum.FAILED);
        }

        if (resultApproveMediaToolDTO.getAudioChannel() == ApproveStatusEnum.FAILED) {
            mapStatusSum.put(MediaToolFieldEnum.AUDIO_CHANNEL, ApproveStatusEnum.FAILED);
        }
    }

    public CourseForApproveDTO prepareCourseDTO(Long courseId) {
        Long id = courseId;
        CourseVersion courseVersion = courseVersionRepository.findById(id).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }

        genCourseCode(courseVersion);

        CourseDetailForApproveDTO courseDetailForApproveDTO = modelMapper.map(courseVersion, CourseDetailForApproveDTO.class);
        String aliasName = courseVersion.getCourse().getAliasName();
        courseDetailForApproveDTO.setAliasName(aliasName);

        setSubcateForCourse(courseDetailForApproveDTO, courseVersion);

        List<Chapter> chapterList = courseVersion.getChapters();
        List<ChapterDTO> chapterDTOList = chapterList.stream()
                .map(chapter -> modelMapper.map(chapter, ChapterDTO.class))
                .collect(Collectors.toList());

        List<AssetDetailForApproveDTO> assetDTOList = getAssetListOfAllChapter(chapterList);

        TeacherDTO teacherDTO = getTeacherOfCourse(courseVersion);

        CourseForApproveDTO courseForApproveDTO = CourseForApproveDTO.builder()
                .courseDetailDTO(courseDetailForApproveDTO)
                .chapters(chapterDTOList)
                .assetDetailDTO(assetDTOList)
                .teacher(teacherDTO)
                .build();
        return courseForApproveDTO;
    }

    @Override
    public boolean processSendToMediaTool(FilesAndCourseVerIdForMediaToolDTO filesAndCourseVerId) {
        List<File> fileList = filesAndCourseVerId.getFileList();
        List<Long> idList = fileList.stream()
                .map(File::getId)
                .collect(Collectors.toList());

        ThreadCheckFileUploadSuccess threadCheckFileUploadSuccess = new ThreadCheckFileUploadSuccess(idList, fileRepository, timeSleep);
        threadCheckFileUploadSuccess.start();
        try {
            threadCheckFileUploadSuccess.join();
            List<File> fileErrorList = threadCheckFileUploadSuccess.getFileErrorList();
            if (fileErrorList.isEmpty()) {
                sendToMediaTool(fileList, filesAndCourseVerId.getCourseVersionId());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException: " + e);
        }
        return false;
    }

    @Override
    public boolean processSendEmailWhenFailUploadVideo(FilesAndCourseVerIdForMediaToolDTO filesAndCourseVerId) {
        List<File> fileList = filesAndCourseVerId.getFileList();
        List<Long> idList = fileList.stream()
                .map(File::getId)
                .collect(Collectors.toList());

        Long courseVersionId = filesAndCourseVerId.getCourseVersionId();
        CourseVersion courseVersion = courseVersionRepository.findById(courseVersionId).orElse(null);
        Long userId = courseVersion.getCreatedBy();
        User user = userRepository.findById(userId).orElse(null);

        ThreadCheckFileUploadSuccess threadCheckFileUploadSuccess = new ThreadCheckFileUploadSuccess(idList, fileRepository, timeSleepEmail);
        threadCheckFileUploadSuccess.start();
        try {
            threadCheckFileUploadSuccess.join();
            List<File> fileErrorList = threadCheckFileUploadSuccess.getFileErrorList();
            if (!fileErrorList.isEmpty()) {

                //update course_version_status = CREATING for teacher edit this course_version
                courseVersion.setStatus(CourseVersionStatusEnum.CREATING);
                courseVersionRepository.save(courseVersion);

                // receiver list
                List<Long> fileIds = fileErrorList.stream().map(file -> file.getId()).collect(Collectors.toList());
                List<Asset> assets = assetRepository.findByFileIdIn(fileIds);

                List<Long> lectureIds = assets.stream().map(asset -> asset.getLecture().getId()).collect(Collectors.toList());
                List<Lecture> lectures = lectureRepository.getLectureByListId(lectureIds);
                String error = "";
                for (Lecture lecture : lectures) {
                    error += lecture.getChapter().getTitle() + " - " + lecture.getTitle() + ",";
                }

                TeacherDetailDTO teacherDetailDTO = teacherService.getTeacherDetail(user.getEmail());
                String contactEmail = teacherDetailDTO.getContactEmail();

                if (contactEmail == null) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error get the teacher from kelley", null);
                }

                List<ReceiverDTO> receivers = new ArrayList<>();
                ReceiverDTO receiver = ReceiverDTO.builder()
                        .email(contactEmail)
                        .name(user.getName())
                        .senderAddress(senderAddress)
                        .senderCity(senderCity)
                        .senderName(senderName)
                        .senderState(senderState)
                        .senderZip(senderZip).error(error)
                        .courseCode(courseVersion.getCourseCode())
                        .build();
                receivers.add(receiver);

                // cc list
                CCSDTO ccs = CCSDTO.builder().email(furionCC).build();
                List<CCSDTO> ccss = new ArrayList<>();
                ccss.add(ccs);

                // sender
                EmailDTO sender = EmailDTO.builder().email(senderEmail).name(senderName).build();

                List<String> substitutionTags = new ArrayList<>(Arrays.asList("Sender_Name", "Sender_Address", "Sender_City", "Sender_State", "Sender_Zip", "courseCode", "error"));

                UploadS3FinishedEmailDTO emailDTO = UploadS3FinishedEmailDTO.builder()
                        .attachments(new ArrayList<>())
                        .ccs(ccss)
                        .content("")
                        .receivers(receivers)
                        .sender(sender)
                        .substitutionTags(substitutionTags)
                        .subject(subjectEmail)
                        .templateCode(templateCode).build();
                emailService.sendEmail(emailDTO);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException: " + e);
        }
        return false;
    }

    public boolean sendToMediaTool(List<File> fileList, Long courseVerId) {
        try {
            List<String> videoLinkList = new ArrayList<>();
            for (File file : fileList) {
                //Only send the file is uploaded successfully to media system
                if (file.getUploadStatus() == UploadFileStatusEnum.SUCCESS) {
                    String videoLink = file.getUrl() + "?fileId=" + file.getId() + "&courseVerId=" + courseVerId;
                    videoLinkList.add(videoLink);
                }
            }

            VideoMediaToolDTO videoMediaToolDTO = VideoMediaToolDTO.builder()
                    .urls(videoLinkList)
                    .callback(apiProcessAfterToolValidate)
                    .jtype("async")
                    .build();
            ObjectMapper objectMapper = new ObjectMapper();
            String payLoad = objectMapper.writeValueAsString(videoMediaToolDTO);

            HttpClient httpClient = HttpClientBuilder.create().build();

            URIBuilder uriBuilder = new URIBuilder(mediaToolUrl);

            HttpPost postRequest = new HttpPost(uriBuilder.build());
            StringEntity entity = new StringEntity(payLoad, "UTF-8");
            postRequest.setEntity(entity);
            postRequest.setHeader("Accept", "application/json");

            HttpResponse httpResponse = httpClient.execute(postRequest);

            CourseVersion courseVersion = courseVersionRepository.findById(courseVerId).orElse(null);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                log.info("Media tool Response accept: " + EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
                if (courseVersion != null) {
                    courseVersion.setStatusSendExternalService(StatusSendExternalServiceEnum.SEND_MEDIA_TOOL_SUCCESS);
                }
                return true;
            } else {
                if (courseVersion != null) {
                    courseVersion.setStatusSendExternalService(StatusSendExternalServiceEnum.SEND_MEDIA_TOOL_FAILED);
                }
                log.info("Media tool Response error: " + EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
            }
        } catch (URISyntaxException e) {
            log.error("Error build url send to media tool: " + e.toString());
            throw new RuntimeException("URISyntaxException: " + e);
        } catch (IOException e) {
            log.error("Error build url send to media tool: " + e.toString());
            throw new RuntimeException("IOException: " + e);
        }
        return false;
    }

    public CourseVersion genCourseCode(CourseVersion courseVersion) {
        List<CourseVersion> courseVersionList = courseVersionRepository.findAll();
        List<String> courseCodeList = courseVersionList.stream()
                .map(courseVer -> courseVer.getCourseCode())
                .collect(Collectors.toList());
        if (courseVersion.getCourseCode() == null) {
            while (true) {
                long numCode = ThreadLocalRandom.current().nextLong(minRandomCourseCode, maxRandomCourseCode + 1);
                String formattedString = String.format("%05d", numCode);
                String courseCode = startCourseCode + formattedString;
                if (!courseCodeList.contains(courseCode)) {
                    courseVersion.setCourseCode(courseCode);
                    return courseVersion;
                }
            }
        }
        return courseVersion;
    }

    private CourseDetailForApproveDTO setSubcateForCourse(CourseDetailForApproveDTO courseDetailForApproveDTO, CourseVersion courseVersion) {
        List<SubCategory> subCategoryList = courseVersion.getSubCategories();
        List<SubCategoryForApproveDTO> subCategoryDTOList = subCategoryList.stream()
                .map(subCategory -> modelMapper.map(subCategory, SubCategoryForApproveDTO.class))
                .collect(Collectors.toList());

        courseDetailForApproveDTO.setSubCategoryDTOList(subCategoryDTOList);
        return courseDetailForApproveDTO;
    }

    private List<AssetDetailForApproveDTO> getAssetListOfAllChapter(List<Chapter> chapterList) {
        List<Lecture> lectureList = new ArrayList<>();
        for (Chapter chapter : chapterList) {
            List<Lecture> lectureListOfChapter = chapter.getLectures();
            for (Lecture lecture : lectureListOfChapter) {
                lectureList.add(lecture);
            }
        }
        List<Asset> assetList = new ArrayList<>();
        if (!lectureList.isEmpty()) {
            assetList = assetRepository.getAssetByLectureList(lectureList);
        }
        List<AssetDetailForApproveDTO> assetDTOList = assetList.stream()
                .map(asset -> modelMapper.map(asset, AssetDetailForApproveDTO.class))
                .collect(Collectors.toList());

        return assetDTOList;
    }

    private TeacherDTO getTeacherOfCourse(CourseVersion courseVersion) {

        List<Teacher> teacherList = courseVersion.getCourse().getTeachers();

        if (!teacherList.isEmpty()) {
            Optional<Teacher> teacherOptional = teacherList.stream().findFirst();
            User user = teacherOptional.get().getUser();
            TeacherDTO teacherDTO = modelMapper.map(teacherOptional.get(), TeacherDTO.class);
            teacherDTO.setEmail(user.getEmail());
            return teacherDTO;
        }

        return null;
    }

    private boolean sendCourseToExternalService(CourseForApproveDTO courseForApproveDTO, Long courseVerId) {
        try {
            URIBuilder uriBuilder = new URIBuilder(kelleyUrlSaveCourse);
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost clientPost = new HttpPost(uriBuilder.build());

            ObjectMapper objectMapper = new ObjectMapper();
            String courseJson = objectMapper.writeValueAsString(courseForApproveDTO);
            StringEntity entity = new StringEntity(courseJson, "UTF-8");
            clientPost.setEntity(entity);
            clientPost.setHeader("Accept", "application/json");
            clientPost.setHeader("Content-type", "application/json");

            log.info("Course payload send to kelley system-------------------------------------: " + courseJson);

            HttpResponse httpResponse = httpClient.execute(clientPost);
            CourseVersion courseVersion = courseVersionRepository.findById(courseVerId).orElse(null);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                if (courseVersion != null) {
                    courseVersion.setStatusSendExternalService(StatusSendExternalServiceEnum.SEND_KELLEY_SUCCESS);
                }
                return true;
            }
            if (httpResponse.getStatusLine().getStatusCode() == 422 || httpResponse.getStatusLine().getStatusCode() == 500) {
                log.info("Error from kelley system: " + "status: " + httpResponse.getStatusLine().getStatusCode() +
                        "message: " + EntityUtils.toString(httpResponse.getEntity()));
                if (courseVersion != null) {
                    courseVersion.setStatusSendExternalService(StatusSendExternalServiceEnum.SEND_KELLEY_FAILED);
                }
            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void processAfterMediaToolValidate(String validateResult) {
        List<MediaToolResponseDTO> mediaToolResponseList = getMediaToolResponse(validateResult);
        List<MediaToolResultDTO> mediaToolResultList = new ArrayList<>();
        List<ApproveStatusEnum> approveStatusEnumList = new ArrayList<>();
        Long courseVerId = null;
        for (MediaToolResponseDTO mediaResponse : mediaToolResponseList) {
            MediaToolResultDTO mediaToolResultDTO = MediaToolResultDTO.builder().build();

            String strFileName = mediaResponse.getFileName();
            String[] arrFileName = strFileName.split("\\?");

            if (arrFileName.length >= 2) {
                String fileExtension = FilenameUtils.getExtension(arrFileName[0]);
                mediaToolResultDTO.setVideoFormat(AnalyticStandard.checkVideoFormatStandard(fileExtension));
                approveStatusEnumList.add(AnalyticStandard.checkVideoFormatStandard(fileExtension));
                String strFileIdAndCourseVerId = arrFileName[1];
                String[] arrFileIdAndCourseVerId = strFileIdAndCourseVerId.split("&");
                if (arrFileIdAndCourseVerId.length >= 2) {
                    String strFileId = arrFileIdAndCourseVerId[0];
                    String[] arrFileId = strFileId.split("=");
                    if (arrFileId.length >= 2) {
                        Long fileId = Long.valueOf(arrFileId[1]);
                        mediaToolResultDTO.setFileId(fileId);
                    }

                    String strCourseId = arrFileIdAndCourseVerId[1];
                    String[] arrCourseId = strCourseId.split("=");
                    if (arrCourseId.length >= 2) {
                        courseVerId = Long.valueOf(arrCourseId[1]);
                    }
                }
            }

            VideoResultDTO videoResultDTO = mediaResponse.getVideo();
            mediaToolResultDTO.setVideoCodeName(AnalyticStandard.checkVideoCodeNameStandard(videoResultDTO.getVideoCodecName()));
            mediaToolResultDTO.setVideoFrameWidth(AnalyticStandard.checkVideoFrameWidthStandard(videoResultDTO.getVideoCodeWidth()));
            mediaToolResultDTO.setVideoFrameHeight(AnalyticStandard.checkVideoFrameHeightStandard(videoResultDTO.getVideoCodeHeight()));
            mediaToolResultDTO.setVideoFrameRate(AnalyticStandard.checkVideoFrameRateStandard(videoResultDTO.getVideoAvgFrameRate()));
            mediaToolResultDTO.setVideoBitRate(AnalyticStandard.checkVideoBitRateStandard(videoResultDTO.getVideoBitRate()));

            AudioResultDTO audioResultDTO = mediaResponse.getAudio();
            mediaToolResultDTO.setAudioBitRate(AnalyticStandard.checkAudioBitRateStandard(audioResultDTO.getAudioBitRate()));
            mediaToolResultDTO.setAudioChannel(AnalyticStandard.checkAudioChannelStandard(audioResultDTO.getAudioChannels()));
            mediaToolResultDTO.setDetailResult(mediaResponse.getFailedCriteria().toString());

            mediaToolResultList.add(mediaToolResultDTO);

            approveStatusEnumList.add(AnalyticStandard.checkVideoCodeNameStandard(videoResultDTO.getVideoCodecName()));
            approveStatusEnumList.add(AnalyticStandard.checkVideoFrameWidthStandard(videoResultDTO.getVideoCodeWidth()));
            approveStatusEnumList.add(AnalyticStandard.checkVideoFrameHeightStandard(videoResultDTO.getVideoCodeHeight()));
            approveStatusEnumList.add(AnalyticStandard.checkVideoFrameRateStandard(videoResultDTO.getVideoAvgFrameRate()));
            approveStatusEnumList.add(AnalyticStandard.checkVideoBitRateStandard(videoResultDTO.getVideoBitRate()));
            approveStatusEnumList.add(AnalyticStandard.checkAudioBitRateStandard(audioResultDTO.getAudioBitRate()));
            approveStatusEnumList.add(AnalyticStandard.checkAudioChannelStandard(audioResultDTO.getAudioChannels()));
        }

        mediaToolService.insertOrUpdate(mediaToolResultList);

        if (courseVerId != null) {
            //update link result approve
            CourseVersion courseVersion = courseVersionRepository.findById(courseVerId).orElse(null);
            if (approveStatusEnumList.contains(ApproveStatusEnum.FAILED)) {
                if (courseVersion != null) {
                    String linkApproveResult = frontendUrlViewApproveResultFront + courseVerId + frontendUrlViewApproveResultBehind;
                    courseVersion.setLinkApproveResult(linkApproveResult);
                    courseVersion.setStatusApprove(ApproveStatusEnum.FAILED);
//                    courseVersion.setStatus(CourseVersionStatusEnum.WAITING_APPROVE);
                }
            } else {
                if (courseVersion != null) {
                    courseVersion.setLinkApproveResult(null);
                    courseVersion.setStatusApprove(ApproveStatusEnum.PASS);
//                    courseVersion.setStatus(CourseVersionStatusEnum.WAITING_APPROVE);
                }
                //send to kelley system
                CourseForApproveDTO courseForApproveDTO = prepareCourseDTO(courseVerId);
                sendCourseToExternalService(courseForApproveDTO, courseVerId);
            }
        }
    }

    @Override
    public boolean updateStatusOfficial(StatusOfficialDTO statusOfficial) {
        Long courseVersionId = statusOfficial.getCourseVersionId();
        CourseVersion courseVersion = courseVersionRepository.getCourseVersionById(courseVersionId);
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }
        courseVersion.setStatusOfficial(statusOfficial.getStatus());
        return true;
    }

    public List<MediaToolResponseDTO> getMediaToolResponse(String validateResult) {
        List<MediaToolResponseDTO> mediaToolResponseList = new ArrayList<>();
        JSONObject jsonObject = null;
        if (validateResult != null) {
            jsonObject = new JSONObject(validateResult);
        }
        if (jsonObject != null) {
            JSONArray jsonArray = (JSONArray) jsonObject.get("results");
            ObjectMapper objectMapper = new ObjectMapper();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                try {
                    String failedCriteria = jsonObj.get("Failed criteria").toString();
                    MediaToolResponseDTO mediaToolResponseDTO = objectMapper.readValue(jsonObj.toString(), MediaToolResponseDTO.class);
                    mediaToolResponseDTO.setFailedCriteria(jsonObj.get("Failed criteria").toString());
                    mediaToolResponseList.add(mediaToolResponseDTO);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return mediaToolResponseList;
    }

    @Override
    public PublishCourseDTO publishCourseVersion(PublishCourseDTO publishCourseDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!teacherService.validateTeacherInfo(authentication)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("teacher.info.not.enough", null));
        }

        Long courseVersionId = publishCourseDTO.getCourseVersionId();

        CourseVersion courseVersion = courseVersionRepository.findById(courseVersionId).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }
        courseVersion.setStatus(CourseVersionStatusEnum.PUBLISHED);

        Course course = courseVersion.getCourse();
        if (course == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"Course"}), null);
        }
        UpdateCourseDTO updateCourseDTO = UpdateCourseDTO.builder()
                .price(publishCourseDTO.getPrice())
                .isRunMarketing(publishCourseDTO.getIsRunMarketing())
                .publishedAt(new Date())
                .courseVersion(courseVersion)
                .build();

        if (!courseService.updateCourse(course, updateCourseDTO)) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("course.update.fail", null));
        }

        CourseKelleyUpdateDTO courseToKelleyDTO = CourseKelleyUpdateDTO.builder()
                .courseCode(courseVersion.getCourseCode())
                .kelleyUrl(kelleyUrlPublishCourse)
                .isRunMarketing(publishCourseDTO.getIsRunMarketing())
                .price(publishCourseDTO.getPrice())
                .build();

        eventBus.notify(ConsumerEnum.UPDATE_COURSE_KELLEY, Event.wrap(courseToKelleyDTO));
        return publishCourseDTO;
    }

    @Override
    public CourseKelleyUpdateDTO updateCourseKelley(CourseKelleyUpdateDTO courseToKelleyDTO) {
        log.info("retry................");
        try {
            URIBuilder uriBuilder = new URIBuilder(courseToKelleyDTO.getKelleyUrl());
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost clientPost = new HttpPost(uriBuilder.build());

            ObjectMapper objectMapper = new ObjectMapper();

            CourseKelleyDTO courseKelleyDTO = CourseKelleyDTO.builder()
                    .code(courseToKelleyDTO.getCourseCode())
                    .isRunMarketing(courseToKelleyDTO.getIsRunMarketing())
                    .price(courseToKelleyDTO.getPrice())
                    .build();
            String payload = objectMapper.writeValueAsString(courseKelleyDTO);

            StringEntity entity = new StringEntity(payload, "UTF-8");
            clientPost.setEntity(entity);
            clientPost.setHeader("Accept", "application/json");
            clientPost.setHeader("Content-type", "application/json");

            log.info("CourseCode-------------------------------------: " + payload);

            HttpResponse httpResponse = httpClient.execute(clientPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                return courseToKelleyDTO;
            }
            if (httpResponse.getStatusLine().getStatusCode() == 422 || httpResponse.getStatusLine().getStatusCode() == 500) {
                log.info("Error from kelley system: "
                        + "status: " + httpResponse.getStatusLine().getStatusCode()
                        + "message: " + EntityUtils.toString(httpResponse.getEntity())
                );
                throw new RuntimeException("Error from kelley system");
            }

        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
            throw new RuntimeException("URISyntaxException | IOException", e);
        }
        return null;
    }

    @Override
    public void sendEmailToTeacher(EmailToTeacherDTO emailToTeacherDTO) {
        log.info("retry........................");
        // receivers
        List<ReceiverDTO> receivers = new ArrayList<>();
        ReceiverDTO receiver = ReceiverDTO.builder()
                .email(emailToTeacherDTO.getTeacherEmail())
                .senderAddress(senderAddress)
                .senderCity(senderCity)
                .senderName(senderName)
                .senderState(senderState)
                .senderZip(senderZip)
                .courseLink(emailToTeacherDTO.getCourseLink())
                .teacherCode(emailToTeacherDTO.getTeacherCode())
                .teacherName(emailToTeacherDTO.getTeacherName())
                .courseCode(emailToTeacherDTO.getCourseCode())
                .build();
        receivers.add(receiver);

        // cc list
        CCSDTO ccs = CCSDTO.builder().email(furionCC).build();
        List<CCSDTO> ccss = new ArrayList<>();
        ccss.add(ccs);

        // sender
        EmailDTO sender = EmailDTO.builder().email(senderEmail).name(senderName).build();

        List<String> substitutionTags = new ArrayList<>(Arrays.asList("Sender_Name",
                "Sender_Address",
                "Sender_City",
                "Sender_State",
                "Sender_Zip",
                "courseCode",
                "courseLink",
                "teacherCode",
                "teacherName"));

        UploadS3FinishedEmailDTO emailDTO = UploadS3FinishedEmailDTO.builder()
                .attachments(new ArrayList<>())
                .ccs(ccss)
                .content("")
                .receivers(receivers)
                .sender(sender)
                .substitutionTags(substitutionTags)
                .subject(emailToTeacherDTO.getSubject())
                .templateCode(emailToTeacherDTO.getTemplateCode()).build();
        emailService.sendEmail(emailDTO);
    }

    @Override
    public void processSendEmailToTeacherAfterPublishCourse(EmailToTeacherDTO emailToTeacherDTO) {
        emailToTeacherDTO.setTemplateCode(templateCodeNotifyAfterPublishCourse);
        emailToTeacherDTO.setSubject(subjectEmailNotifyAfterPublishCourse);
        eventBus.notify(ConsumerEnum.SEND_EMAIL_TO_TEACHER, Event.wrap(emailToTeacherDTO));
    }

    @Override
    public void disappearCourse(DisappearCourseDTO disappearCourseDTO) {

        CourseVersion courseVersion = courseVersionRepository.findById(disappearCourseDTO.getCourseVersionId()).orElse(null);
        if (courseVersion == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"CourseVersion"}), null);
        }

        UserPrincipal userPrincipal = UserAuthenticationInfo.getUserAuthenticationInfo();
        String email = userPrincipal.getEmail();

        User user = userRepository.findFirstByEmail(email);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"User"}), null);
        }
        Teacher teacher = user.getTeacher();
        if (teacher == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"Teacher"}), null);
        }

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TeacherDetailDTO teacherDetailDTO = teacherService.getTeacherDetail(email);
        String contactEmail = teacherDetailDTO.getContactEmail();
        if (contactEmail == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error get the teacher from kelley system", null);
        }

        EmailToTeacherDTO emailToTeacherDTO = EmailToTeacherDTO.builder()
                .subject(subjectEmailDisappearCourse)
                .templateCode(templateCodeDisappearCourse)
                .teacherEmail(contactEmail)
                .teacherName(user.getName())
                .teacherCode(teacher.getTeacherCode())
                .courseCode(courseVersion.getCourseCode())
                .build();

        eventBus.notify(ConsumerEnum.SEND_EMAIL_TO_TEACHER, Event.wrap(emailToTeacherDTO));
    }

    @Override
    public void disappearCourseFromKelley(ApproveResultDTO approveResultDTO) {
        String courseCode = approveResultDTO.getCourseCode();
        List<CourseVersion> courseVersionList = courseVersionRepository.findByCourseCodeOrderByCreatedAtAsc(courseCode);

        if (courseVersionList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    Translator.toLocale("resource.not_found", new Object[]{"List_Course_version"}), null);
        }
        CourseVersion courseVersion = courseVersionList.stream().reduce((first, second) -> second).get();

        courseVersion.setStatus(CourseVersionStatusEnum.DISAPPEAR);
        Course course = courseVersion.getCourse();
        course.setStatus(CourseStatusEnum.DISAPPEAR);
    }

}


