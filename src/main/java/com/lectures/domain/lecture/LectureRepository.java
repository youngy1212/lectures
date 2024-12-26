package com.lectures.domain.lecture;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface LectureRepository {

    // 해당 날짜에 신청 가능한 특강 목록 조회
    List<Lecture> findLecturesByDate(LocalDate date);

    Optional<Lecture> findByIdWithPessimisticLock(Long lectureId);

}
