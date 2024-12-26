package com.lectures.adapters.persistence;

import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.lecture.LectureRepository;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureJpaRepository extends JpaRepository<Lecture, Long> , LectureRepository {

    @Override
    @Query("SELECT o FROM Lecture o WHERE o.lectureDate =:date")
    List<Lecture> findLecturesByDate(LocalDate date);


    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(name = "javax.persistence.lock.timeout", value = "3000")  // 락 타임아웃 설정
    })
    @Query("SELECT o FROM Lecture o WHERE o.id = :lectureId")
    Optional<Lecture> findByIdWithPessimisticLock(Long lectureId);



}
