package com.lectures.application;


import com.lectures.adapters.persistence.LectureJpaRepository;
import com.lectures.adapters.persistence.ReservationLectureJpaRepository;
import com.lectures.application.dto.LectureDto;
import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.reservation.ReservationLecture;
import com.lectures.domain.reservation.ReservationStatus;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ReservationLectureService {

    private final ReservationLectureJpaRepository reservationLectureJpaRepository;

    public ReservationLectureService(ReservationLectureJpaRepository reservationLectureJpaRepository) {
        this.reservationLectureJpaRepository = reservationLectureJpaRepository;
    }

    public List<LectureDto> getActiveLecturesByUserId(Long userId) {

        List<ReservationLecture> reservationLecturesList = reservationLectureJpaRepository.findByUserIdAndReservationStatus(userId, ReservationStatus.ACTIVE);
        return reservationLecturesList.stream()
                .map(ReservationLecture::getLecture)
                .map(LectureDto::from)
                .collect(Collectors.toList());
    }


}
