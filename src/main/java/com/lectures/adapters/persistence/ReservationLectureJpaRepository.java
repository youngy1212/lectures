package com.lectures.adapters.persistence;

import com.lectures.domain.reservation.ReservationLecture;
import com.lectures.domain.reservation.ReservationLectureRepository;
import com.lectures.domain.reservation.ReservationStatus;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationLectureJpaRepository extends JpaRepository<ReservationLecture, Long>,
        ReservationLectureRepository {

    @Override
    List<ReservationLecture> findByUserIdAndReservationStatus(Long userId, ReservationStatus reservationStatus);

    @Override
    int countByUserIdAndLectureId(Long userId, Long lectureId);
}
