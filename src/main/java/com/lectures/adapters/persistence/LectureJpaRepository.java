package com.lectures.adapters.persistence;

import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.lecture.LectureRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long> , LectureRepository {

    @Override
    @Query("SELECT o FROM Lecture o WHERE o.lectureDate =:date")
    List<Lecture> findLecturesByDate(LocalDate date);


}
