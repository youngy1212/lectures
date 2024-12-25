package com.lectures.adapters.persistence;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.lectures.domain.lecture.Lecture;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class LectureJpaRepositoryTest {

    @Autowired
    private LectureJpaRepository lectureJpaRepository;


    @DisplayName("특정 날짜의 강의를 조회한다.")
    @Test
    void FindLecturesByDate() {
        // given
        LocalDate targetDate = LocalDate.of(2024, 12, 25);
        Lecture lecture1 = Lecture.of("강의1", "강사1", LocalDate.of(2024, 12, 25),30,20);
        Lecture lecture2 = Lecture.of("강의2", "강사2", LocalDate.of(2024, 12,25),30,30);
        Lecture lecture3 = Lecture.of("강의3", "강사3", LocalDate.of(2024, 12,26),30,30);

        lectureJpaRepository.save(lecture1);
        lectureJpaRepository.save(lecture2);
        lectureJpaRepository.save(lecture3);


        // when
        List<Lecture> lectures = lectureJpaRepository.findLecturesByDate(targetDate);

        // then
        assertThat(lectures).hasSize(2)
                .extracting(Lecture::getTitle)
                .containsExactlyInAnyOrder("강의1", "강의2");


    }

}