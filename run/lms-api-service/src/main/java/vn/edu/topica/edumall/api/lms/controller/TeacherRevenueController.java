package vn.edu.topica.edumall.api.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vn.edu.topica.edumall.api.lms.service.TeacherRevenueService;
import vn.edu.topica.edumall.data.model.TeacherRevenue;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/teacher/revenue")
public class TeacherRevenueController {

    @Autowired
    TeacherRevenueService teacherRevenueService;

    @Value("${athena.token}")
    String athenaToken;

    @PostMapping("")
    public ResponseEntity<Object> addNewRevenueRecord(@RequestBody TeacherRevenue teacherRevenue,
                                                      @RequestHeader(value="Athena-Key") String athenaKey) {
        if(!athenaToken.equals(athenaKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized");
        }
        return new ResponseEntity<>(teacherRevenueService.addRevenueRecord(teacherRevenue), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<Page<TeacherRevenue>> getListRevenue(@RequestParam("pageNum") @NotNull int pageNum, @RequestParam("pageSize") @NotNull int pageSize) {
        Pageable teacherRevenuePageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        Page<TeacherRevenue> teacherRevenues = teacherRevenueService.getListRevenue(teacherRevenuePageable);
        return new ResponseEntity<>(teacherRevenues, HttpStatus.OK);
    }

    @PutMapping("")
    public ResponseEntity<Object> updateRevenueApprovalStatus(@RequestBody @Valid TeacherRevenue teacherRevenue) {
        TeacherRevenue revenue = teacherRevenueService.updateRevenueRecord(teacherRevenue);
        return new ResponseEntity<>(revenue, HttpStatus.OK);
    }

}
