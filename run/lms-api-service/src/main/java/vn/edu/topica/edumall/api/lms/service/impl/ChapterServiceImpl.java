package vn.edu.topica.edumall.api.lms.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.dto.ChapterDTO;
import vn.edu.topica.edumall.api.lms.dto.CreateChapterDTO;
import vn.edu.topica.edumall.api.lms.repository.*;
import vn.edu.topica.edumall.api.lms.service.ChapterService;
import vn.edu.topica.edumall.api.lms.utility.UserAuthenticationInfo;
import vn.edu.topica.edumall.data.model.Chapter;
import vn.edu.topica.edumall.data.model.CourseVersion;
import vn.edu.topica.edumall.locale.config.Translator;

import java.util.Optional;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {

    @Autowired
    ChapterRepository chapterRepository;

    @Autowired
    CourseVersionRepository courseVersionRepository;

    @Autowired
    LectureRepository lectureRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    AssetRepository assetRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ChapterDTO createChapter(CreateChapterDTO chapter) {
        long courseVersionId = chapter.getCourseVersionId();
        Optional<CourseVersion> optionalCourseVersion =  courseVersionRepository.findById(courseVersionId);

        if (optionalCourseVersion.isPresent()) {
            Chapter lastChapter = chapterRepository.findFirstByCourseVersionIdOrderByChapterOrderDesc(courseVersionId);

            Chapter.ChapterBuilder chapterBuilder = Chapter.builder();
            chapterBuilder.title(chapter.getTitle());
            chapterBuilder.chapterOrder(lastChapter == null? 1 : lastChapter.getChapterOrder() + 1);
            chapterBuilder.courseVersion(optionalCourseVersion.get());
            Long userId = UserAuthenticationInfo.getUserAuthenticationInfo().getId();
            chapterBuilder.createdBy(userId);
            Chapter newChapter =  chapterRepository.save(chapterBuilder.build());

            ChapterDTO chapterDTO = modelMapper.map(newChapter, ChapterDTO.class);
            return chapterDTO;
        }
        else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[] {"Course version"}));
        }
    }

    @Override
    public ChapterDTO editChapter(long chapterId, Chapter chapter) {
        Optional<Chapter> optionalChapter = chapterRepository.findById(chapterId);
        if (optionalChapter.isPresent()) {
            Chapter oldChapter = optionalChapter.get();

            if (oldChapter.getTitle() != chapter.getTitle()) {
                oldChapter.setTitle(chapter.getTitle());
            }

            ChapterDTO chapterDTO = modelMapper.map(oldChapter, ChapterDTO.class);

            return chapterDTO;
        }
        else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("resource.not_found", new Object[] {"Chapter"}));
        }
    }

    @Override
    public Boolean deleteChapter(long chapterId) {
        Optional<Chapter> chapter = chapterRepository.findById(chapterId);
        if (chapter.isPresent()) {
            Chapter currentChapter = chapter.get();

            //you should delete all asset first
            assetRepository.deleteAllByChapterId(chapterId);

            //then delete all quiz
            quizRepository.deleteByChapterId(chapterId);

            //and delete all lecture
            lectureRepository.deleteByChapterId(chapterId);

            //delete chapter at the end
            chapterRepository.delete(currentChapter);

            return true;
        }
        else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Translator.toLocale("chapter.notfound"));
        }


    }


}
