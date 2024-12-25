package com.lectures.domain.reservation;

import com.lectures.domain.common.BaseEntity;
import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationLecture extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_lecture_id",  nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Enumerated(EnumType.STRING)
    private ReservationStatus reservationStatus;

    private ReservationLecture(User user, Lecture lecture, ReservationStatus reservationStatus) {
        this.user = user;
        this.lecture = lecture ;
        this.reservationStatus = reservationStatus;
    }

    public static ReservationLecture of(User user, Lecture lecture, ReservationStatus reservationStatus) {
        return new ReservationLecture(user, lecture, reservationStatus);
    }

}
