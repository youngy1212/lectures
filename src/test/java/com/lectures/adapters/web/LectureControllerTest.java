package com.lectures.adapters.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lectures.application.LectureService;
import com.lectures.application.dto.LectureDto;
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

    @DisplayName("특정 날짜의 특강 목록을 조회한다")
    @Test
    void testGetLecturesByDate() throws Exception {
        // Given
        LocalDate date = LocalDate.of(2024, 12, 25);

        LectureDto lecture1 = LectureDto.from(Lecture.create("특강1", "강사1", date, 30, 20));
        LectureDto lecture2 =  LectureDto.from(Lecture.create("특강2", "강사2", date, 30, 30));
        LectureDto lecture3 =  LectureDto.from(Lecture.create("특강3", "강사3", date, 30, 10));


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

}