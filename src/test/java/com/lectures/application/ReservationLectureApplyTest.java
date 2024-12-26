package com.lectures.application;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.lectures.adapters.persistence.LectureJpaRepository;
import com.lectures.adapters.persistence.ReservationLectureJpaRepository;
import com.lectures.adapters.persistence.UserJpaRepository;
import com.lectures.domain.lecture.Lecture;
import com.lectures.domain.user.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ReservationLectureApplyTest {


    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private LectureJpaRepository lectureJpaRepository;

    @Autowired
    private ReservationLectureJpaRepository lectureLectureJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @AfterEach
    void tearDown(){
        lectureLectureJpaRepository.deleteAllInBatch();
        lectureJpaRepository.deleteAllInBatch();
        userJpaRepository.deleteAllInBatch();
    }

    @DisplayName("동시에 동일한 특강에 대해 40명이 신청했을 때, 30명만 성공한다.")
    @Test
    void testConcurrentLectureReservations() throws InterruptedException {
        // given
        int applyCount = 40;

        AtomicInteger successCount = new AtomicInteger(); //성공 횟수
        AtomicInteger failureCount = new AtomicInteger(); //실패 횟수

        List<User> users = new ArrayList<>();
        for(int i = 0; i < applyCount; i++ ){
            User user = userJpaRepository.save(User.of("유저"+i));
            users.add(user);
        }

        Lecture lecture = lectureJpaRepository.save(Lecture.of("특강1", "강사1", LocalDate.now(), 30, 0));

        ExecutorService executorService = Executors.newFixedThreadPool(applyCount);
        CountDownLatch latch = new CountDownLatch(applyCount);

        // when
        for(int i = 0; i < applyCount; i++) {

            final Long userId = users.get(i).getId();

            executorService.execute(() -> {
                try {
                    reservationFacade.ReservationLectureApply(userId, lecture.getId());
                    successCount.incrementAndGet();
                }catch (Exception e) {
                    failureCount.incrementAndGet();
                }finally {
                    latch.countDown();
                }
            });

        }

        latch.await();
        executorService.shutdown();

        // then
        Lecture updatedLecture = lectureJpaRepository.findById(lecture.getId()).orElseThrow();
        assertThat(updatedLecture.getCurrentReservationCount()).isEqualTo(30);

        long successfulReservations = lectureLectureJpaRepository.count();
        assertThat(successfulReservations).isEqualTo(30);
        assertThat(successCount.get()).isEqualTo(30);
        assertThat(failureCount.get()).isEqualTo(applyCount - 30);

    }

    @DisplayName("동일한 유저 정보로 같은 특강을 5번 신청했을 때, 1번만 성공한다.")
    @Test
    void testReservationBySameUser() throws InterruptedException {
        // given
        User user = userJpaRepository.save(User.of("중복유저"));
        Lecture lecture = lectureJpaRepository.save(Lecture.of("특강1", "강사1", LocalDate.now(), 30, 0));

        int applyCount = 5;
        AtomicInteger successCount = new AtomicInteger(); //성공 횟수
        AtomicInteger failureCount = new AtomicInteger(); //실패 횟수

        ExecutorService executorService = Executors.newFixedThreadPool(applyCount);
        CountDownLatch latch = new CountDownLatch(applyCount);

        // when
        for(int i = 0; i < applyCount; i++) {

            executorService.execute(() -> {
                try {
                    reservationFacade.ReservationLectureApply(user.getId(), lecture.getId());
                    successCount.incrementAndGet();
                }catch (Exception e) {
                    failureCount.incrementAndGet();
                }finally {
                    latch.countDown();
                }
            });

        }

        latch.await();
        executorService.shutdown();

        // then
        Lecture updatedLecture = lectureJpaRepository.findById(lecture.getId()).orElseThrow();
        assertThat(updatedLecture.getCurrentReservationCount()).isEqualTo(1); //수강생 1명

        long successfulReservations = lectureLectureJpaRepository.count(); //강의 히스토리 1개
        assertThat(successfulReservations).isEqualTo(1);
        assertThat(successCount.get()).isEqualTo(1);
        assertThat(failureCount.get()).isEqualTo(4);

    }

}