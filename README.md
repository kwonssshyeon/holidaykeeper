## Holiday Keeper

### 작업 내용
| Branch                      | Issue                                                          | PR                                                           | 내용(요약)                  |
| --------------------------- |----------------------------------------------------------------|--------------------------------------------------------------| ----------------------- |
| **develop**                 | —                                                              | —                                                            | 통합 개발 브랜치               |
| **feature/data-search**     | [#12](https://github.com/kwonssshyeon/holidaykeeper/issues/12) | [#13](https://github.com/kwonssshyeon/holidaykeeper/pull/13) | 공휴일 검색 기능 구현            |
| **feature/common-for-api**  | [#10](https://github.com/kwonssshyeon/holidaykeeper/issues/10) | [#11](https://github.com/kwonssshyeon/holidaykeeper/pull/11) | API 공통 모듈(응답, 예외 처리) 구성 |
| **feature/data-initialize** | [#8](https://github.com/kwonssshyeon/holidaykeeper/issues/8)   | [#9](https://github.com/kwonssshyeon/holidaykeeper/pull/9)   | 초기 데이터 적재 로직 구현         |
| **feature/data-load**       | [#6](https://github.com/kwonssshyeon/holidaykeeper/issues/6)   | [#7](https://github.com/kwonssshyeon/holidaykeeper/pull/7)   | 공휴일 API 데이터 로드 기능       |
| **feature/data-modeling**   | [#4](https://github.com/kwonssshyeon/holidaykeeper/issues/4)   | [#5](https://github.com/kwonssshyeon/holidaykeeper/pull/5)   | 공휴일/국가/카운티 모델링          |
| **chore/ci**                | [#2](https://github.com/kwonssshyeon/holidaykeeper/issues/2)   | [#3](https://github.com/kwonssshyeon/holidaykeeper/pull/3)   | CI 설정, 빌드/테스트 파이프라인 구성  |


## 실행 방법
로컬 실행
```html
./gradlew clean build
./gradlew bootRun
```

## REST API 명세 요약
### 📘 휴일 조회 API

### **GET `/api/holidays`**
휴일 목록을 조회합니다.  
국가 코드(`countryCode`), 연도(`year`)로 필터링할 수 있으며 페이징을 지원합니다.

---

### 🔍 Query Parameters

| 이름 | 타입 | 필수 | 기본값 | 설명 |
|------|------|------|--------|------|
| `countryCode` | string | ❌ | — | ISO 국가 코드 |
| `year` | int | ❌ | 현재 연도 | 조회 연도 |
| `page` | int | ❌ | 0 | 페이지 번호 |
| `size` | int | ❌ | 20 | 페이지 크기 |

---

### 📦 Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "data": [
      {
        "id": 123456789,
        "date": "2025-01-01",
        "name": "New Year's Day",
        "localName": "New Year's Day",
        "countryCode": "US",
        "fixed": true
      }
    ],
    "page": 0,
    "totalPage": 12,
    "hasNext": true
  }
}
```
### 📘 휴일 상세 조회 API

### **GET `/api/holidays/{holidayId}`**
특정 휴일의 상세 정보를 조회합니다.

---

### 🔍 Path Parameters

| 이름 | 타입 | 필수 | 설명 |
|------|------|------|------|
| `holidayId` | int64 | ✅ | 휴일 ID |

---

### 📦 Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "data": {
    "id": 123456789,
    "date": "2025-01-01",
    "name": "New Year's Day",
    "localName": "New Year's Day",
    "countryCode": "US",
    "fixed": true,
    "global": true,
    "counties": [
      "CA",
      "NY"
    ],
    "types": [
      "Public",
      "Bank"
    ]
  }
}
```
### 📘 휴일 유형 목록 조회 API

### **GET `/api/holiday-types`**
지원하는 휴일 유형 목록을 조회합니다.

---

### 🔍 Query / Path Parameters
없음

---

### 📦 Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "data": [
    "Public",
    "Bank",
    "School",
    "Optional"
  ]
}
```
### 📘 국가 목록 조회 API

### **GET `/api/countries`**
지원하는 국가 목록을 조회합니다.

---

### 🔍 Query / Path Parameters
없음

---

### 📦 Response (200 OK)

```json
{
  "status": 200,
  "message": "OK",
  "data": [
    {
      "code": "US",
      "name": "United States"
    },
    {
      "code": "KR",
      "name": "Korea"
    }
  ]
}
```

## Swagger UI 확인 방법
```html
http://localhost:8080/swagger-ui/index.html
```

## 브랜치 전략
```html
main
└── develop
    └── feature/*
```

## 커밋 컨벤션
- `feat` : 새로운 기능 추가
- `fix` : 버그 수정
- `test` : 테스트 코드 작성 및 수정
- `chore` : 설정, 빌드, CI 등 비즈니스 로직과 무관한 작업
- `docs` : 문서 수정
- `refactor` : 기능 변화 없는 코드 개선

