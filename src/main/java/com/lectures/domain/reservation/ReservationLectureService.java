package com.lectures.domain.reservation;

import com.lectures.domain.dto.LectureDto;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ReservationLectureService {

    private final ReservationLectureRepository reservationLectureRepository;

    public ReservationLectureService(ReservationLectureRepository reservationLectureRepository) {
        this.reservationLectureRepository = reservationLectureRepository;
    }

    public List<LectureDto> getActiveLecturesByUserId(Long userId) {

        List<ReservationLecture> reservationLecturesList = reservationLectureRepository.findByUserIdAndReservationStatus(userId, ReservationStatus.ACTIVE);
        return reservationLecturesList.stream()
                .map(ReservationLecture::getLecture)
                .map(LectureDto::from)
                .collect(Collectors.toList());
    }

    public void findReservationBySameUser(Long userId, Long lectureId) {
        int count = reservationLectureRepository.countByUserIdAndLectureId(userId, lectureId);

        if (count > 0) {
            throw new IllegalArgumentException("이미 신청한 강의입니다.");
        }
    }

}
