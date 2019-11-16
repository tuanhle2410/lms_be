package vn.edu.topica.edumall.api.lms.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.edu.topica.edumall.api.lms.dto.*;
import vn.edu.topica.edumall.api.lms.service.CourseVersionService;
import vn.edu.topica.edumall.api.lms.utility.UserAuthenticationInfo;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/course-version")
public class CourseVersionController {
    @Autowired
    CourseVersionService courseVersionService;

    @GetMapping("")
    public ResponseEntity<Object> findByStatus(@RequestParam(required = false) Integer status, @RequestParam int page) {
        List<CourseVersionDTO> listCourseVersionDTO;
        Long userId = UserAuthenticationInfo.getUserAuthenticationInfo().getId();

        int startIndex = 0;
        if (status != null) {
            startIndex = (page - 1) * 10;
            listCourseVersionDTO = courseVersionService.getByUserIdAndStatus(userId, status, startIndex);
        } else {
            startIndex = (page - 1) * 10;
            listCourseVersionDTO = courseVersionService.getByUserId(userId, startIndex);
        }

        for (CourseVersionDTO obj : listCourseVersionDTO) {
            obj.setDescriptionStatus(obj.getStatus().getDescription());
        }

        CourseVersionPagingDTO result = CourseVersionPagingDTO.builder().build();
        int totalPage = courseVersionService.getTotalPage(userId, status);
        result.setPerPage(10);
        result.setListCourseVersionDTO(listCourseVersionDTO);
        result.setNextPage(page == totalPage ? page : page + 1);
        result.setCurrentPage(page);
        result.setPreviousPage(page == 1 ? 1 : page - 1);
        result.setTotalPage(totalPage);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("isCourseVersionOwner(#id)")
    @GetMapping("/detail")
    public ResponseEntity<Object> getCourserVersionDetail(@RequestParam @NotEmpty long id, @RequestParam(required = false) Long step) {
        return new ResponseEntity<>(courseVersionService.getCourserVersionDetail(id, step), HttpStatus.OK);
    }

    @PreAuthorize("isCourseVersionOwner(#id)")
    @GetMapping("/{id}/validate")
    public ResponseEntity<Object> validateCourseVersion(@PathVariable(value = "id") Long id) {
        return new ResponseEntity<>(courseVersionService.validateCourseVersion(id), HttpStatus.OK);
    }

    @GetMapping("/countByStatus")
    public ResponseEntity<CountByStatusAndUserIdDTO> countCourseVersionByStatusDTO() {
        Long userId = UserAuthenticationInfo.getUserAuthenticationInfo().getId();
        return new ResponseEntity<>(courseVersionService.countByStatusAndUserId(userId), HttpStatus.OK);
    }

    @PreAuthorize("isCourseVersionOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCourseVersion(@PathVariable(value = "id") long id, @RequestBody CreateCourseDTO createCourseDTO) {
        return new ResponseEntity<>(courseVersionService.updateCourseVersion(id, createCourseDTO), HttpStatus.OK);
    }

    @PreAuthorize("isCourseVersionOwner(#id)")
    @GetMapping("/{id}/send-approve")
    public ResponseEntity<Object> sendApprove(@PathVariable(value = "id") long id) {
        return new ResponseEntity<>(courseVersionService.sendApprove(id), HttpStatus.OK);
    }

    @PostMapping("/process-after-tool-validate")
    public ResponseEntity<Object> processAfterMediaToolValidate(@RequestBody String validateResult) {
        // get data from media tool
        // save to table media_tool_result
        // send to kelly system
        log.info("Result after validate tool" + validateResult);
        courseVersionService.processAfterMediaToolValidate(validateResult);
        return new ResponseEntity<>(validateResult, HttpStatus.OK);
    }

    @GetMapping("{id}/reason-reject")
    public ResponseEntity<Object> getResultFromMediaTool(@PathVariable(value = "id") long courseVersionId) {
        String strJsonFake = "{\n" +
                "    \"frameWidth\": \"FAILED\",\n" +
                "    \"frameHeight\": \"FAILED\",\n" +
                "    \"bitRate\": \"FAILED\",\n" +
                "    \"frameRate\": \"FAILED\",\n" +
                "    \"codeName\": \"FAILED\",\n" +
                "    \"format\": \"FAILED\",\n" +
                "    \"audioBitRate\": \"FAILED\",\n" +
                "    \"audioChannel\": \"PASS\",\n" +
                "    \"chapters\": [\n" +
                "        {\n" +
                "            \"title\": \"Chương 1\",\n" +
                "            \"id\": 1,\n" +
                "            \"lectures\": [\n" +
                "                {\n" +
                "                    \"title\": \"bai giang 1\",\n" +
                "                    \"id\": 1,\n" +
                "                    \"media_tool_result\": {\n" +
                "                        \"id\": 2,\n" +
                "                        \"frameWidth\": \"FAILED\",\n" +
                "                        \"frameHeight\": \"FAILED\",\n" +
                "                        \"bitRate\": \"FAILED\",\n" +
                "                        \"frameRate\": \"FAILED\",\n" +
                "                        \"codeName\": \"FAILED\",\n" +
                "                        \"status\": \"FAILED\",\n" +
                "                        \"format\": \"FAILED\",\n" +
                "                        \"audioBitRate\": \"FAILED\",\n" +
                "                        \"audioChannel\": \"PASS\",\n" +
                "                        \"detailResult\": \"detail failed\"\n" +
                "                    }\n" +
                "                },\n" +
                "                {\n" +
                "                    \"title\": \"bai giang 2\",\n" +
                "                    \"id\": 2,\n" +
                "                    \"media_tool_result\": {\n" +
                "                        \"id\": 4,\n" +
                "                        \"frameWidth\": \"FAILED\",\n" +
                "                        \"frameHeight\": \"PASS\",\n" +
                "                        \"bitRate\": \"FAILED\",\n" +
                "                        \"frameRate\": \"FAILED\",\n" +
                "                        \"codeName\": \"FAILED\",\n" +
                "                        \"status\": \"FAILED\",\n" +
                "                        \"format\": \"FAILED\",\n" +
                "                        \"audioBitRate\": \"PASS\",\n" +
                "                        \"audioChannel\": \"PASS\",\n" +
                "                        \"detailResult\": \"detail failed\"\n" +
                "                    }\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";
//        return new ResponseEntity<Object>(strJsonFake, HttpStatus.OK);
        return new ResponseEntity<Object>(courseVersionService.getResultApproveStep1(courseVersionId), HttpStatus.OK);
    }

    /**
     * Update approve result (round 2)from the Kelley system
     * @param approveResultDTO
     * @return
     */
    @PutMapping("/update-approve-result")
    public ResponseEntity<Object> updateApproveResult(@RequestBody ApproveResultDTO approveResultDTO) {
        return new ResponseEntity<>(courseVersionService.updateApproveResult(approveResultDTO), HttpStatus.OK);
    }

    @PutMapping("/update-status-official")
    public ResponseEntity<Object> updateStatusOfficial(@RequestBody StatusOfficialDTO statusOfficial) {
        return new ResponseEntity<>(courseVersionService.updateStatusOfficial(statusOfficial), HttpStatus.OK);
    }

    @PutMapping("/publish")
    public ResponseEntity<Object> publishCourseVersion(@RequestBody PublishCourseDTO publishCourseDTO) {
        return new ResponseEntity<>(courseVersionService.publishCourseVersion(publishCourseDTO), HttpStatus.OK);
    }

    @PostMapping("/send-email-to-teacher")
    public ResponseEntity<Object> sendMailToTeacherAfterPublishCourse(@RequestBody EmailToTeacherDTO notifyPublishDTO) {
        courseVersionService.processSendEmailToTeacherAfterPublishCourse(notifyPublishDTO);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PostMapping("/disappear")
    public ResponseEntity<Object> disappearCourse(@RequestBody DisappearCourseDTO disappearCourseDTO) {
        courseVersionService.disappearCourse(disappearCourseDTO);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @PutMapping("/disappear-course-from-kelley")
    public ResponseEntity<Object> disappearCourseFromKelley(@RequestBody ApproveResultDTO approveResultDTO) {
        courseVersionService.disappearCourseFromKelley(approveResultDTO);
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

}
