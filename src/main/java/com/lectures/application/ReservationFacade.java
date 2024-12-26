package com.lectures.application;

import com.lectures.adapters.persistence.LectureJpaRepository;
import com.lectures.adapters.persistence.ReservationLectureJpaRepository;
import com.lectures.adapters.persistence.UserJpaRepository;
import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.reservation.ReservationLecture;
import com.lectures.domain.reservation.ReservationStatus;
import com.lectures.domain.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ReservationFacade {

    private final ReservationLectureJpaRepository reservationLectureJpaRepository;
    private final LectureJpaRepository lectureJpaRepository;
    private final UserJpaRepository userJpaRepository;

    public ReservationFacade(ReservationLectureJpaRepository reservationLectureJpaRepository,
                             LectureJpaRepository lectureJpaRepository, UserJpaRepository userJpaRepository) {
        this.reservationLectureJpaRepository = reservationLectureJpaRepository;
        this.lectureJpaRepository = lectureJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }

    @Transactional
    public void ReservationLectureApply(Long userId, Long lectureId) {

        User user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));

        //동일유저 동일 체크
        int count = reservationLectureJpaRepository.countByUserIdAndLectureId(userId, lectureId);

        if (count > 0) {
            throw new IllegalArgumentException("이미 신청한 강의입니다.");
        }


        //강의 조회
        Lecture lecture = lectureJpaRepository.findByIdWithPessimisticLock(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));

        lecture.incrementReservationCount();

        ReservationLecture reservationLecture = ReservationLecture.of(
                user,lecture, ReservationStatus.ACTIVE
        );

        reservationLectureJpaRepository.save(reservationLecture);

    }

}
