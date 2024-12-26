package com.lectures.adapters.web;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lectures.domain.lecture.LectureService;
import com.lectures.application.ReservationFacade;
import com.lectures.domain.reservation.ReservationLectureService;
import com.lectures.domain.dto.LectureDto;
import com.lectures.domain.lecture.Lecture;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = LectureController.class)
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LectureService lectureService;

    @MockitoBean
    private ReservationLectureService reservationLectureService;

    @MockitoBean
    private ReservationFacade reservationFacade;

    @DisplayName("특정 날짜의 특강 목록을 조회한다")
    @Test
    void testGetLecturesByDate() throws Exception {
        // Given
        LocalDate date = LocalDate.of(2024, 12, 25);

        LectureDto lecture1 = LectureDto.from(Lecture.of("특강1", "강사1", date, 30, 20));
        LectureDto lecture2 =  LectureDto.from(Lecture.of("특강2", "강사2", date, 30, 30));
        LectureDto lecture3 =  LectureDto.from(Lecture.of("특강3", "강사3", date, 30, 10));


        List<LectureDto> lectureList = List.of(lecture1, lecture2,lecture3);

        when(lectureService.getLecturesByDate(date)).thenReturn(lectureList);

        // When & Then
        mockMvc.perform(get("/lectures")
                        .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("특강1"))
                .andExpect(jsonPath("$[1].title").value("특강2"))
                .andExpect(jsonPath("$[2].title").value("특강3"));

    }

    @DisplayName("사용자가 예약한 강의 목록을 조회한다")
    @Test
    void testGetActiveLecturesByUserId() throws Exception {
        // Given
        Long userId = 1L;

        Lecture lecture = Lecture.of("특강1", "강사1", LocalDate.now(), 30, 20);
        Lecture lecture2 = Lecture.of("특강2", "강사2", LocalDate.now(), 30, 30);

        List<LectureDto> lectureDto = List.of(LectureDto.from(lecture),LectureDto.from(lecture2));

        when(reservationLectureService.getActiveLecturesByUserId(userId)).thenReturn(lectureDto);

        // When & Then
        mockMvc.perform(get("/reservations/lectures")
                        .param("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title").value("특강1"))
                .andExpect(jsonPath("$[1].title").value("특강2"));

    }

    @DisplayName("userId와 lectureId를 받아 특강을 신청한다.")
    @Test
    void saveReservationLectureApply() throws Exception {
        // given
        Long userId = 1L;
        Long lectureId = 2L;

        // reservationFacade의 메서드 호출에 대한 동작 정의
        doNothing().when(reservationFacade).ReservationLectureApply(userId, lectureId);

        // when & then
        mockMvc.perform(get("/{lectureId}/reserve", lectureId)
                        .param("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("ok"));

        verify(reservationFacade, times(1)).ReservationLectureApply(userId, lectureId);
    }

}