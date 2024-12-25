package com.lectures.application;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.lectures.adapters.persistence.LectureJpaRepository;
import com.lectures.adapters.persistence.ReservationLectureJpaRepository;
import com.lectures.adapters.persistence.UserJpaRepository;
import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.reservation.ReservationLecture;
import com.lectures.domain.user.User;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ReservationFacadeTest {

    @InjectMocks
    private ReservationFacade reservationFacade;

    @Mock
    private ReservationLectureJpaRepository reservationLectureJpaRepository;

    @Mock
    private LectureJpaRepository lectureJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @DisplayName("강의 신청이 정상적으로 이루어진다.")
    @Test
    void testReservationLectureApplySuccess() {
        // given
        Long userId = 1L;
        Long lectureId = 2L;

        User user = User.of("신청자1");
        Lecture lecture = Lecture.of("특강1", "강사1", LocalDate.now(), 30, 20);

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(user));
        when(lectureJpaRepository.findById(lectureId)).thenReturn(Optional.of(lecture));

        // when
        reservationFacade.ReservationLectureApply(userId, lectureId);

        // then
        assertEquals(21, lecture.getCurrentReservationCount()); //신청자 수 증가
        verify(reservationLectureJpaRepository, times(1)).save(any(ReservationLecture.class));
    }

    @DisplayName("존재하지 않는 유저로 신청 시 예외가 발생한다.")
    @Test
    void testReservationLectureApplyNotUser() {
        // given
        Long userId = 1L;
        Long lectureId = 2L;


        // when // then
        assertThatThrownBy(() -> reservationFacade.ReservationLectureApply(userId, lectureId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 유저입니다.");

    }

    @DisplayName("존재하지 않는 강의로 신청 시 예외가 발생한다.")
    @Test
    void testReservationLectureApplyNotLecture() {
        // given
        Long userId = 1L;
        Long lectureId = 2L;

        User user = User.of("신청자1");
        Lecture lecture = Lecture.of("특강1", "강사1", LocalDate.now(), 30, 20);

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(user));

        // then
        assertThatThrownBy(() -> reservationFacade.ReservationLectureApply(userId, lectureId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 강의입니다.");
    }

    @DisplayName("강의 신청 정원 초과로 실패")
    @Test
    void testReservationLectureApplyFullCapacity() {
        // given
        Long userId = 1L;
        Long lectureId = 2L;

        User user = User.of("신청자1");
        Lecture lecture = Lecture.of("특강1", "강사1", LocalDate.now(), 30, 30);

        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(user));
        when(lectureJpaRepository.findById(lectureId)).thenReturn(Optional.of(lecture));

        // when // then
        assertThatThrownBy(() -> reservationFacade.ReservationLectureApply(userId, lectureId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("정원이 초과되어 신청할 수 없습니다.");
    }


}