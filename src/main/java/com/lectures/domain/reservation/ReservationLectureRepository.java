package com.lectures.domain.reservation;


import java.util.List;

public interface ReservationLectureRepository {

    List<ReservationLecture> findByUserIdAndReservationStatus(Long userId, ReservationStatus reservationStatus);
}
