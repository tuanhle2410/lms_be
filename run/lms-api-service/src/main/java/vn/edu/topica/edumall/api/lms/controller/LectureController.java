package vn.edu.topica.edumall.api.lms.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.api.lms.service.LectureService;

import javax.validation.Valid;

import vn.edu.topica.edumall.data.model.Lecture;

import javax.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    @Autowired
    LectureService lectureService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("isLectureOwner(#id)")
    @GetMapping("{id}/detail")
    public ResponseEntity<Object> getLectureDetail(@PathVariable("id") Long id) {
        return new ResponseEntity<>(lectureService.getLectureDetail(id), HttpStatus.OK);
    }

    @PreAuthorize("isLectureOwner(#lectureId)")
    @GetMapping("/{lectureId}")
    @ResponseBody
    public ResponseEntity<Lecture> show(@PathVariable @NotEmpty long lectureId) {
        Lecture lecture = lectureService.detail(lectureId);
        if (lecture != null)
            return new ResponseEntity<Lecture>(lecture, HttpStatus.OK);
        else
            return new ResponseEntity<Lecture>(HttpStatus.NOT_FOUND);
    }

    @PreAuthorize("isChapterOwner(#lectureDTO.getChapterId())")
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<LectureDTO> create(@RequestBody @Valid CreateLectureDTO lectureDTO) {
        LectureDTO lecture = lectureService.create(lectureDTO);
        return new ResponseEntity<LectureDTO>(lecture, HttpStatus.OK);
    }

    @PreAuthorize("isLectureOwner(#lectureId)")
    @PutMapping("/{lectureId}")
    @ResponseBody
    public ResponseEntity<LectureDTO> update(
            @PathVariable @NotEmpty long lectureId,
            @RequestBody @Valid UpdateLectureDTO lectureDTO
    ) {
        LectureDTO lecture = lectureService.update(lectureId, lectureDTO);
        return new ResponseEntity<LectureDTO>(lecture, HttpStatus.OK);
    }

    @PreAuthorize("isLectureOwner(#id)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLecture(@PathVariable("id") long id) {
        return new ResponseEntity<>(lectureService.deleteLecture(id), HttpStatus.OK);
    }

    @PreAuthorize("isLectureOwner(#assetUploadLectureDTO.getLectureId()) and isCourseVersionOwner(#assetUploadLectureDTO.getCourseId())")
    @PostMapping("/uploadAttachment")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Object> uploadLectureAttachment(@ModelAttribute @Valid AssetUploadLectureDTO assetUploadLectureDTO, BindingResult result) {
        if (result.hasErrors()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    result.getFieldErrors().stream().findFirst().get().getField() + " " + result.getFieldErrors().stream().findFirst().get().getDefaultMessage());
        }
        AssetDetailDTO asset = lectureService.uploadAttachment(assetUploadLectureDTO);
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }

    @PreAuthorize("isLectureOwner(#assetUploadLectureDTO.getLectureId()) and isCourseVersionOwner(#assetUploadLectureDTO.getCourseId())")
    @PostMapping("/uploadAttachmentViaWarehouse")
    public ResponseEntity<Object> uploadAttachmentViaWarehouse(@RequestBody @Valid AssetUploadLectureFromWareHouseDTO assetUploadLectureDTO) {
        AssetDetailDTO asset = lectureService.uploadAttachmentFromWareHouse(assetUploadLectureDTO);
        return new ResponseEntity<>(asset, HttpStatus.OK);
    }
}
