package com.lectures.application;


import com.lectures.application.dto.LectureDto;
import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.lecture.LectureRepository;
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


}
