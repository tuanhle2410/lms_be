package vn.edu.topica.edumall.api.lms.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import vn.edu.topica.edumall.api.lms.dto.*;

import java.util.List;

public interface CourseVersionService {
    List<CourseVersionDTO> getByUserIdAndStatus(long userId, int status, int startIndex);

    List<CourseVersionDTO> getByUserId(long userId, int startIndex);


    int getTotalPage(long userId, Integer status);

    /**
     * Use to count course version by status and user id
     *
     * @param userId
     * @return
     */
    CountByStatusAndUserIdDTO countByStatusAndUserId(long userId);

    /**
     * Used to get list chapters and
     * list lectures of course_version
     *
     * @param id
     * @param step
     * @return
     */
    Object getCourserVersionDetail(Long id, Long step);

    /**
     * Used to validate course_version
     *
     * @param id
     * @return
     */
    ValidateCourseDTO validateCourseVersion(Long id);

    /**
     * Used to update course_version
     *
     * @param id
     * @param createCourseDTO
     * @return
     */
    CourseDetailDTO updateCourseVersion(Long id, CreateCourseDTO createCourseDTO);

    /**
     * Used to send the course to the external system
     * This external service is media tool system.
     * Send list videos to media tool system which use async
     *
     * @param id
     * @return
     */
    CourseForApproveDTO sendApprove(Long id);

    ResultApproveStep1DTO getResultApproveStep1(Long courseVersionId);

    /**
     * Used to update approve result
     *
     * @param approveResultDTO
     * @return
     */
    ApproveResultDTO updateApproveResult(ApproveResultDTO approveResultDTO);

    /**
     * Used for sending list videos to
     * media tool system
     *
     * @param filesAndCourseVerId
     * @return
     */
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    boolean processSendToMediaTool(FilesAndCourseVerIdForMediaToolDTO filesAndCourseVerId);

    @Retryable(value = {Exception.class}, maxAttempts = 5, backoff = @Backoff(delay = 3000))
    boolean processSendEmailWhenFailUploadVideo(FilesAndCourseVerIdForMediaToolDTO filesAndCourseVerId);

    void processAfterMediaToolValidate(String validateResult);

    /**
     * Used to update status official
     *
     * @param statusOfficial
     * @return
     */
    boolean updateStatusOfficial(StatusOfficialDTO statusOfficial);

    /**
     * Publish course version
     *
     * @param publishCourseDTO
     * @return
     */
    PublishCourseDTO publishCourseVersion(PublishCourseDTO publishCourseDTO);

    /**
     * Update course version to kelley
     *
     * @param courseKelleyUpdateDTO
     * @return
     */
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    CourseKelleyUpdateDTO updateCourseKelley(CourseKelleyUpdateDTO courseKelleyUpdateDTO);

    /**
     * Send mail async to teacher
     *
     * @param notifyPublishDTO
     */
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    void sendEmailToTeacher(EmailToTeacherDTO notifyPublishDTO);

    /**
     * Process Send mail to teacher after publish course
     *
     * @param notifyPublishDTO
     */
    void processSendEmailToTeacherAfterPublishCourse(EmailToTeacherDTO notifyPublishDTO);

    void disappearCourse(DisappearCourseDTO disappearCourseDTO);

    void disappearCourseFromKelley(ApproveResultDTO approveResultDTO);
}
