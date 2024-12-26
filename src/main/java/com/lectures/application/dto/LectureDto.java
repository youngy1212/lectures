package com.lectures.application.dto;

import com.lectures.domain.lecture.Lecture;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class LectureDto {

    private Long id;
    private String title;
    private String lecturerName;
    private LocalDate lectureDate;
    private int capacity;
    private int currentReservationCount;

    private LectureDto(Long id, String title, String lecturerName, LocalDate lectureDate, int capacity,
                      int currentReservationCount) {
        this.id = id;
        this.title = title;
        this.lecturerName = lecturerName;
        this.lectureDate = lectureDate;
        this.capacity = capacity;
        this.currentReservationCount = currentReservationCount;
    }
    public static LectureDto from(Lecture lecture) {
        return new LectureDto(
                lecture.getId(),
                lecture.getTitle(),
                lecture.getLecturerName(),
                lecture.getLectureDate(),
                lecture.getCapacity(),
                lecture.getCurrentReservationCount()
        );
    }

}
