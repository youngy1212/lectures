
## 1. ERD 설계
![image](https://github.com/user-attachments/assets/13d87bc1-5e58-4eb5-8707-3f87914e6e15)


- **`users`** Table :
    - 유저 정보 테이블
    - [ Id, 이름 ] 
- **`lecture`** Table :
    - 특강 정보 테이블
    - [ Id, 강의명, 강사이름, 특강날, 정원, 현재 예약 수]
- **`reservationLecture`** Table :
    - `users`와 `Lecture` 의 중간 테이블. 강의예약 테이블. 
    - [ Id , 강의 Id , 유저 Id , 예약 상태 ]

**모든 테이블은 기본적으로 `등록시간과/ 수정시간`** 을 가지고 있습니다.

<br>

### 해당 테이블로 구성 한 이유
- reservationLecture를 생성하여 n -> 1 <- n 의 관계가 될 수 있도록 만들었습니다.
- reservationLecture을 통해, 예약 상태를 쉽게 조회할 수 있습니다.
- reservationStatus의 경우 차후 취소 등의 기능을 추가 또는, 실패를 기록하기 위해 확장성을 위해 추가하였습니다.
- current_reservation_count를 lecture에 등록함으로써 신청 가능/불가능을 판단하는데 이점 되는 것으로 판단하였습니다. 
- 기본 등록시간/ 수정시간을 등록함으로써, 통계와 스냅샷 기능을 할 수 있습니다.


## 아키텍처 
**도메인 모델 + 레이어 구조** 를 사용

처음에는 도메인 모델을 강조하기 위해 도메인 모델 + 도메인 구조를 사용하였습니다. 
- 도메인 구조에 익숙하지 않다보니 책임이 명확하게 구분이 어려웠습니다.
- 그래서 도메인 모델 + 안쪽으로 레이어 구조로 하니, 간단한 프로젝트 치고 너무 거대한 구조가 되가는 것 같았습니다.

도메인 모델 + 레이어 구조로 변경하였습니다
- import 문 만으로도 다른 레이어의 침범이 있는지 없는지 확인 할 수 있었습니다.
- 핵심 원칙인 책임 분리와 의존성 관리를 준수할 수 있었습니다. 

해서 이러한 구조를 사용하였습니다
```aiignore
L adapters //외부연결
  L web
    L controller
    L Request/Response //아직 refactor 못함! 
  L persistence  
    L repository (구현)
L application
  L service
    L dto  
L domain
  L entity
  L repository 
  L service (필요시)
```


