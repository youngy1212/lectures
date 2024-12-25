package com.lectures.adapters.web;

import com.lectures.application.LectureService;
import com.lectures.application.ReservationFacade;
import com.lectures.application.ReservationLectureService;
import com.lectures.application.dto.LectureDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LectureController {

    private final LectureService lectureService;
    private final ReservationLectureService reservationLectureService;
    private final ReservationFacade reservationFacade;

    public LectureController(LectureService lectureService, ReservationLectureService reservationLectureService,
                             ReservationFacade reservationFacade) {
        this.lectureService = lectureService;
        this.reservationLectureService = reservationLectureService;
        this.reservationFacade = reservationFacade;
    }

    // 특강 목록 조회 API
    @GetMapping("/lectures")
    public List<LectureDto> getLecturesByDate(
            @RequestParam LocalDate date) {
        return lectureService.getLecturesByDate(date);
    }

    // 나의 특강 목록 조회 API
    @GetMapping("/reservations/lectures")
    public List<LectureDto> getActiveLecturesByUserId(
            @RequestParam Long userId) {
        return reservationLectureService.getActiveLecturesByUserId(userId);
    }

    // 특강 신청 API
    @GetMapping("/{lectureId}/reserve")
    public ResponseEntity<String> saveReservationLectureApply(
            @PathVariable Long lectureId,
            @RequestParam Long userId) {
        reservationFacade.ReservationLectureApply(userId,lectureId);
        return ResponseEntity.ok("ok");
    }

}
