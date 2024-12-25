package com.lectures.adapters.web;

import com.lectures.application.LectureService;
import com.lectures.application.dto.LectureDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LectureController {

    private final LectureService lectureService;

    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    // 특강 목록 조회 API
    @GetMapping("/lectures")
    public List<LectureDto> getLecturesByDate(
            @RequestParam(required = true) LocalDate date) {
        return lectureService.getLecturesByDate(date);
    }

}
