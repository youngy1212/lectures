package com.lectures.domain.lecture;

import com.lectures.domain.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    private String title;

    private String lecturerName;

    private LocalDate lectureDate;

    private int capacity;

    private int currentReservationCount;

    private Lecture(String title, String lecturerName, LocalDate lectureDate, int capacity,
                    int currentReservationCount) {
        this.title = title;
        this.lecturerName = lecturerName;
        this.lectureDate = lectureDate;
        this.capacity = capacity;
        this.currentReservationCount = currentReservationCount;
    }

    public static Lecture create(String title, String lecturerName, LocalDate lectureDate, int capacity,
                                 int currentReservationCount) {
        return new Lecture(title, lecturerName, lectureDate, capacity, currentReservationCount);
    }

    //신청 가능 여부 체크
    public boolean isAvailable() {
        return this.currentReservationCount < this.capacity;
    }

    //신청자 수 증가
    public void incrementReservationCount() {
        if (!isAvailable()) {
            throw new IllegalArgumentException("정원이 초과되어 신청할 수 없습니다.");
        }
        this.currentReservationCount++;
    }


}
