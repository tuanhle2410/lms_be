package vn.edu.topica.edumall.api.lms.service;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.data.model.Lecture;

public interface LectureService {
    /**
     * Return lecture detail
     *
     * @param id
     * @return
     */
    Lecture detail(long id);

    LectureDTO create(CreateLectureDTO lectureDTO);

    LectureDTO update(long lectureId, UpdateLectureDTO lectureDTO);

    /**
     * Used to get the lecture detail.
     * Get video, attachment of the lecture
     *
     * @param id
     * @return
     */
    LectureDetailDTO getLectureDetail(Long id);

    /**
     * Used delete the lecture.
     * Delete all assets of the lecture.
     * Delete all files (copy file) of the lecture. Only delete file record
     * Not delete the physic file.
     *
     * @param id
     * @return
     */
    ObjectDeletedDTO deleteLecture(Long id);

    /**
     * Upload lecture attachment
     *
     * @param assetUploadLectureDTO
     * @return
     */
    AssetDetailDTO uploadAttachment(AssetUploadLectureDTO assetUploadLectureDTO);

    /**
     * Upload lecture attachment
     *
     * @param assetUploadLectureDTO
     * @return
     */
    AssetDetailDTO uploadAttachmentFromWareHouse(AssetUploadLectureFromWareHouseDTO assetUploadLectureDTO);

}
