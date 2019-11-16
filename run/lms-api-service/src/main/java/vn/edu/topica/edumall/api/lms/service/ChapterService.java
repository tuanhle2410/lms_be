package vn.edu.topica.edumall.api.lms.service;

import vn.edu.topica.edumall.api.lms.dto.ChapterDTO;
import vn.edu.topica.edumall.api.lms.dto.CreateChapterDTO;
import vn.edu.topica.edumall.data.model.Chapter;

public interface ChapterService {
    /**
     *
     * @param chapter
     * @return
     */
    ChapterDTO createChapter(CreateChapterDTO chapter);

    /**
     *
     * @param chapterId
     * @param chapter
     * @return
     */
    ChapterDTO editChapter(long chapterId, Chapter chapter);

    /**
     *
     * @param chapterId
     * @return
     */
    Boolean deleteChapter(long chapterId);
}
