package com.lectures.domain.lecture;


import com.lectures.domain.dto.LectureDto;
import java.time.LocalDate;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LectureService  {

    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    //해당 날짜에 신청 가능한 특강 조회
    public List<LectureDto> getLecturesByDate(LocalDate date){
        List<Lecture> lectures = lectureRepository.findLecturesByDate(date);
        return lectures.stream()
                .map(LectureDto::from).toList();
    }

    public Lecture findLectureById(Long lectureId) {
        return lectureRepository.findByIdWithPessimisticLock(lectureId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 강의입니다."));
    }


}
