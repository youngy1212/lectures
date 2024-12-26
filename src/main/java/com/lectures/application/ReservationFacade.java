package com.lectures.application;

import com.lectures.adapters.persistence.LectureJpaRepository;
import com.lectures.adapters.persistence.ReservationLectureJpaRepository;
import com.lectures.adapters.persistence.UserJpaRepository;
import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.lecture.LectureService;
import com.lectures.domain.reservation.ReservationLecture;
import com.lectures.domain.reservation.ReservationLectureService;
import com.lectures.domain.reservation.ReservationStatus;
import com.lectures.domain.user.User;
import com.lectures.domain.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReservationFacade {

    private final ReservationLectureJpaRepository reservationLectureJpaRepository;

    private final UserService userService;
    private final LectureService lectureService;
    private final ReservationLectureService reservationLectureService;


    public ReservationFacade(ReservationLectureJpaRepository reservationLectureJpaRepository, UserService userService,
                             LectureService lectureService, ReservationLectureService reservationLectureService) {
        this.reservationLectureJpaRepository = reservationLectureJpaRepository;
        this.userService = userService;
        this.lectureService = lectureService;
        this.reservationLectureService = reservationLectureService;
    }

    @Transactional
    public void ReservationLectureApply(Long userId, Long lectureId) {

        //유저 체크
        User user = userService.findUserById(userId);

        //동일유저 동일 체크
        reservationLectureService.findReservationBySameUser(userId, lectureId);

        //강의 조회
        Lecture lecture = lectureService.findLectureById(lectureId);

        lecture.incrementReservationCount();

        ReservationLecture reservationLecture = ReservationLecture.of(
                user,lecture, ReservationStatus.ACTIVE
        );

        reservationLectureJpaRepository.save(reservationLecture);

    }

}
