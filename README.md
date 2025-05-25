# BE 2차 - 일정 관리 앱 만들기

## Structure
```
📂src
├── 📂main
│   ├── 📂java
│   │   └── 📂com.example.be_assignment_02
│   │       ├── 📂controller
│   │       │   └── ScheduleController.java
│   │       ├── 📂service
│   │       │   └── ScheduleService.java
│   │       ├── 📂repository
│   │       │   └── ScheduleRepository.java
│   │       ├── 📂dto
│   │       │   ├── ScheduleRequestDto.java
│   │       │   ├── ScheduleResponseDto.java
│   │       │   └── ScheduleQueryConditionDto.java
│   │       ├── 📂model
│   │       |    └── Schedule.java
|   |       └── ▶️ BeAssignment02Application.java(실행파일)
│   └── 📂resources
│       ├── application.properties
│       └── schema.sql (Spring Boot 자동 초기화용)
├── 📂test (테스트 코드, 따로 코딩을 진행하지는 않음)
└── schedule.sql (루트 폴더, 수동 초기화용)
```

## ERD
![image](https://github.com/user-attachments/assets/78e4a671-8170-45c7-9dd4-ae9a9c5625e2)

## 📖 API 명세서
- **base url**: http://localhost:8080
- **데이터 전송 형식**: application/json
- **데이터 반환 형식**: application/json
- 인증/인가: None (with password parameter)

### 1. 일정 등록
- **URL**: POST /scheules
- **요청 값**: Body(JSON)
- **반환 값**: 생성된 일정 ID
- **Request Body**
```
  {
    "todo": "아침 기상",
    "writer": "김아무개",
    "password": "0000"
  }
```
- **Response Body**
```
{
  1
}
```

### 2. 전체 일정 조회
- **URL**: GET /scheules
- **요청 값**: Query Params(선택)
- **파라미터**: writer(string), updatedDate(YYYY-MM-DD)
- **반환 값**: 일정 목록
- **Response Body**
```
[
  {
    "id": 1,
    "todo": "아침 기상",
    "writer": "김아무개",
    "createdAt": "2025-05-24T10:00:00",
    "updatedAt": "2025-05-24T10:00:00"
  }
]
```

### 3. 선택 일정 조회
- **URL**: GET /schedules/{id}
- **요청 값**: Path Variable: id(int)
- **반환 값**: 선택 일정 정보
- **Response Body**
```
[
  {
    "id": 1,
    "todo": "아침 기상",
    "writer": "김아무개",
    "createdAt": "2025-05-24T10:00:00",
    "updatedAt": "2025-05-24T10:00:00"
  }
]
```

### 4. 일정 수정
- **URL**: PUT /schedules/{id}
- **요청 값**: Path Variable: id(int) / 
Body(JSON): todo(string), writer(string), password(string)
- **반환 값**: 상태 메시지
- **Request Body**
```
{
  "todo": "자료 정리",
  "writer": "김아무개",
  "password": "0000"
}
```
- **Response Message**:
  - 성공: 200 OK "일정이 수정되었습니다."
  - 실패: 403 Forbidden "비밀번호가 일치하지 않습니다." / 404 Not Found "해당 일정 없음"

### 5. 일정 삭제
- **URL**: DELETE /schedules/{id}
- **요청 값**: Path Variable: id(int) / 
Query Param: password(string)
- **반환 값**: 상태 메시지
- **인증**: 비밀번호 일치 여부
- **Response Message**:
  - 성공: 200 OK "일정이 삭제되었습니다."
  - 실패: 403 Forbidden "비밀번호가 일치하지 않습니다." / 404 Not Found "해당 일정 없음"


