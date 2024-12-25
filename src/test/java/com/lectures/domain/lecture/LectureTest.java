package com.lectures.domain.lecture;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class LectureTest {

    @DisplayName("신청인원이 정원보다 작아서 True 반환한다.")
    @Test
    void isAvailable_WhenCurrentApplicantCountLessThanCapacity_ReturnsTrue() {
        // given
        Lecture lecture = Lecture.of(
                "특강이름",
                "강사이름",
                LocalDate.now(),
                30,
                29
        );

        // when
        boolean isAvailable = lecture.isAvailable();

        // then
        assertTrue(isAvailable);
    }

    @DisplayName("신청인원이 정원이랑 동일하여 false 반환한다.")
    @Test
    void isAvailable_WhenCurrentApplicantCountEqualsCapacity_ReturnsFalse() {
        // given
        Lecture lecture = Lecture.of(
                "특강이름",
                "강사이름",
                LocalDate.now(),
                30,
                30
        );

        // when
        boolean isAvailable = lecture.isAvailable();

        // then
        assertFalse(isAvailable);
    }

    @DisplayName("신청 인원이 정상적으로 증가한다.")
    @Test
    void incrementReservationCount() {
        // given
        Lecture lecture = Lecture.of(
                "특강이름",
                "강사이름",
                LocalDate.now(),
                30,
                29
        );

        // when
        lecture.incrementReservationCount();

        // then
        assertEquals(30, lecture.getCurrentReservationCount());
    }

    @DisplayName("신청 인원을 초과하여 실패한다.")
    @Test
    void incrementReservationCount_Full_ThrowsException() {
        // given
        Lecture lecture = Lecture.of(
                "특강이름",
                "강사이름",
                LocalDate.now(),
                30,
                30
        );

        // when //then
        assertThatThrownBy(lecture::incrementReservationCount)
                .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("정원이 초과되어 신청할 수 없습니다.");
    }
}