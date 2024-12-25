package com.lectures.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.lectures.adapters.persistence.LectureJpaRepository;
import com.lectures.application.dto.LectureDto;
import com.lectures.domain.lecture.Lecture;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class LectureServiceTest {

    @DisplayName("해당날짜의 특강정보를 가져온다.")
    @Test
    void testGeLecturesByDate() {
        // given
        LocalDate date = LocalDate.now();
        Lecture lecture1 = Lecture.of("특강1", "강사1", date, 30, 20);
        Lecture lecture2 = Lecture.of("특강2", "강사2", date, 30, 30);
        Lecture lecture3 = Lecture.of("특강3", "강사3", date, 30, 10);

        LectureJpaRepository lectureJpaRepository = Mockito.mock(LectureJpaRepository.class);
        when(lectureJpaRepository.findLecturesByDate(date)).thenReturn(List.of(
                lecture1, lecture2,lecture3
        ));

        LectureService lectureService = new LectureService(lectureJpaRepository);

        // when
        List<LectureDto> lecturesDto = lectureService.getLecturesByDate(date);

        // then
        assertEquals("특강1", lecturesDto.get(0).getTitle());
        assertEquals("특강2", lecturesDto.get(1).getTitle());
        assertEquals("특강3", lecturesDto.get(2).getTitle());

    }

}