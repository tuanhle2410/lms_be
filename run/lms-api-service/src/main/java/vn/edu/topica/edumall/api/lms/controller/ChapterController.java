package vn.edu.topica.edumall.api.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.topica.edumall.api.lms.dto.ChapterDTO;
import vn.edu.topica.edumall.api.lms.dto.CreateChapterDTO;
import vn.edu.topica.edumall.api.lms.service.ChapterService;
import vn.edu.topica.edumall.data.model.Chapter;

import javax.validation.Valid;

@RestController
@RequestMapping("/chapters")
public class ChapterController {

    @Autowired
    ChapterService chapterService;

    @PreAuthorize("isCourseVersionOwner(#chapter.getCourseVersionId())")
    @PostMapping("")
    public ResponseEntity<ChapterDTO> createChapter(@RequestBody @Valid CreateChapterDTO chapter) {
        return new ResponseEntity<>(chapterService.createChapter(chapter), HttpStatus.CREATED);
    }

    @PreAuthorize("isChapterOwner(#chapterId)")
    @PutMapping("/{chapterId}")
    @ResponseBody
    public ResponseEntity<ChapterDTO> editChapter(@PathVariable(value = "chapterId") Long chapterId,
                                                   @RequestBody @Valid Chapter chapter) {

        ChapterDTO newChapter = chapterService.editChapter(chapterId, chapter);
        if (newChapter != null) {
            return new ResponseEntity<>(newChapter, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("isChapterOwner(#chapterId)")
    @DeleteMapping("/{chapterId}")
    public ResponseEntity<Boolean> deleteChapter(@PathVariable(value = "chapterId") Long chapterId) {
        return new ResponseEntity<>(chapterService.deleteChapter(chapterId), HttpStatus.OK);
    }

}
