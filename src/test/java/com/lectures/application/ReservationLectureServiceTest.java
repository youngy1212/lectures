package com.lectures.application;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.lectures.adapters.persistence.LectureJpaRepository;
import com.lectures.adapters.persistence.ReservationLectureJpaRepository;
import com.lectures.application.dto.LectureDto;
import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.reservation.ReservationLecture;
import com.lectures.domain.reservation.ReservationStatus;
import com.lectures.domain.user.User;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class ReservationLectureServiceTest {

    @DisplayName("유저의 ACTIVE 상태의 예약 강의 목록을 조회한다")
    @Test
    void testGetActiveLecturesByUserId() {
        // given
        Long userId = 1L;

        LocalDate date = LocalDate.now();
        Lecture lecture1 = Lecture.of("특강1", "강사1", date, 30, 20);
        Lecture lecture2 = Lecture.of("특강2", "강사2", date, 30, 30);
        Lecture lecture3 = Lecture.of("특강3", "강사3", date, 30, 10);

        User user = User.of("수강자1");



        //mocking
        ReservationLectureJpaRepository reservationLectureJpaRepository = Mockito.mock(ReservationLectureJpaRepository.class);

        ReservationLectureService reservationLectureService = new ReservationLectureService(
                reservationLectureJpaRepository
        );
        ReservationLecture reservationLecture1 = ReservationLecture.of(user, lecture1, ReservationStatus.ACTIVE);
        ReservationLecture reservationLecture2 = ReservationLecture.of(user, lecture2, ReservationStatus.INACTIVE);
        ReservationLecture reservationLecture3 = ReservationLecture.of(user, lecture3, ReservationStatus.ACTIVE);


        List<ReservationLecture> reservationLectures = List.of(reservationLecture1, reservationLecture3);
        List<Lecture> lectures = List.of(lecture1, lecture3);

        when(reservationLectureJpaRepository.findByUserIdAndReservationStatus(userId, ReservationStatus.ACTIVE))
                .thenReturn(reservationLectures);

        // when
        List<LectureDto> lectureDtos = reservationLectureService.getActiveLecturesByUserId(userId);

        // then
        assertEquals(2, lectureDtos.size());
        assertEquals("특강1", lectureDtos.get(0).getTitle());
        assertEquals("특강3", lectureDtos.get(1).getTitle());

    }

}