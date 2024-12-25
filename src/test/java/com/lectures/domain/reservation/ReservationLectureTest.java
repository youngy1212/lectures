package com.lectures.domain.reservation;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.user.User;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReservationLectureTest {

    @DisplayName("ReservationLecture 객체를 생성한다")
    @Test
    void testReservationLecture() {

        Lecture lecture = Lecture.of("특강3", "강사3", LocalDate.now(), 30, 10);
        User user = User.of("수강자1");


        // when
        ReservationLecture reservationLecture = ReservationLecture.of(
                user,
                lecture,
                ReservationStatus.ACTIVE
        );

        // then
        assertNotNull(reservationLecture);
        assertEquals(user, reservationLecture.getUser());
        assertEquals(lecture, reservationLecture.getLecture());
        assertEquals( ReservationStatus.ACTIVE, reservationLecture.getReservationStatus());
    }
}