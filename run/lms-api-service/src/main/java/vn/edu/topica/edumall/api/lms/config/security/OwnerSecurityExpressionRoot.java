package vn.edu.topica.edumall.api.lms.config.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.repository.*;
import vn.edu.topica.edumall.data.model.*;
import vn.edu.topica.edumall.locale.config.Translator;
import vn.edu.topica.edumall.security.core.model.UserPrincipal;

public class OwnerSecurityExpressionRoot
        extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {

    public static final String UNAUTHORIZED_MSG_CODE = "not.authorized";

    FileRepository fileRepository;

    FolderRepository folderRepository;

    CourseVersionRepository courseVersionRepository;

    ChapterRepository chapterRepository;

    LectureRepository lectureRepository;

    public OwnerSecurityExpressionRoot(Authentication authentication,
                                       FileRepository fileRepository, FolderRepository folderRepository,
                                        CourseVersionRepository courseVersionRepository,
                                       ChapterRepository chapterRepository, LectureRepository lectureRepository) {
        super(authentication);
        this.fileRepository = fileRepository;
        this.folderRepository = folderRepository;
        this.courseVersionRepository = courseVersionRepository;
        this.chapterRepository = chapterRepository;
        this.lectureRepository = lectureRepository;
    }

    public boolean isFileOwner(Long fileId) {
        Long userId = getUserId();
        File file = fileRepository.findByIdAndCreatedBy(fileId, userId);
        return isResourceValid(file);
    }

    public boolean isFolderOwner(Long folderId) {
        Long userId = getUserId();
        Folder folder = folderRepository.findByIdAndCreatedBy(folderId, userId);
        return isResourceValid(folder);
    }

    public boolean isCourseVersionOwner(Long courseVersionId) {
        Long userId = getUserId();
        CourseVersion courseVersion = courseVersionRepository.findByIdAndCreatedBy(courseVersionId, userId);
        return isResourceValid(courseVersion);
    }

    public boolean isChapterOwner(Long chapterId) {
        Long userId = getUserId();
        Chapter chapter = chapterRepository.findByIdAndCreatedBy(chapterId, userId);
        return isResourceValid(chapter);
    }

    public boolean isLectureOwner(Long lectureId) {
        Long userId = getUserId();
        Lecture lecture = lectureRepository.findByIdAndCreatedBy(lectureId, userId);
        return isResourceValid(lecture);
    }

    private Long getUserId() {
        UserPrincipal user = ((UserPrincipal)this.authentication.getCredentials());
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Translator.toLocale(UNAUTHORIZED_MSG_CODE));
        }
        return user.getId();
    }

    private boolean isResourceValid(Object resource) {
        if(resource == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Translator.toLocale(UNAUTHORIZED_MSG_CODE));
        }
        return true;
    }

    @Override
    public void setFilterObject(Object o) {

    }

    @Override
    public Object getFilterObject() {
        return null;
    }

    @Override
    public void setReturnObject(Object o) {

    }

    @Override
    public Object getReturnObject() {
        return null;
    }

    @Override
    public Object getThis() {
        return null;
    }
}
