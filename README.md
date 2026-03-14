# 🛒 JubJub 프로젝트 (Backend)

줍줍 프로젝트의 백엔드 저장소입니다.  
팀원분들은 아래 세팅 가이드를 따라 로컬 환경을 구성해 주세요.

---

## 🛠 1. 개발 환경 (Tech Stack)
* **Language:** Java 21 (Amazon Corretto 21)
* **Framework:** Spring Boot 4.0.3 (Snapshot/Milestone 버전에 따라 유동적)
* **Build Tool:** Gradle
* **Database:** MySQL 8.0
* **Library:** Lombok, Spring Data JPA, Springdoc-openapi (Swagger), P6Spy

---

## ⚙️ 2. 로컬 DB 세팅 (중요)

프로젝트 실행 전, 반드시 로컬 MySQL에 데이터베이스를 생성해야 합니다.

1.  **MySQL 접속** (Workbench 또는 IntelliJ Database 탭 사용)
2.  **데이터베이스 생성:**
    ```sql
    CREATE DATABASE jubjub CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
    ```
3.  **application.yml 수정:**
    * `src/main/resources/application.yml` 파일에서 `password` 부분을 본인의 로컬 MySQL 비밀번호로 수정하세요.

---

## 🚀 3. 실행 및 API 문서 확인

1.  `Jubjub01Application.java` 실행
2.  **Swagger UI (API 명세서):**
    * 주소: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
    * 서버가 켜진 상태에서 위 주소로 접속하면 생성된 API를 테스트할 수 있습니다.

---

## 📂 4. 패키지 구조 규칙

우리는 **`org.example.jubjub`** 패키지를 기본으로 사용합니다.

* `domain/`: 도메인별 비즈니스 로직 (Member, Store 등)
    * `entity/`, `repository/`, `service/`, `controller/`
* `global/`: 공통 설정 및 유틸리티
    * `common/`: `ApiResponse` 등 공통 객체
    * `config/`: Swagger, JPA 등 설정 파일

---

## 🤝 5. 협업 약속 (Convention)

### 📢 공통 응답 형식
모든 API 응답은 `global.common.ApiResponse` 객체를 사용하여 반환합니다.
- **성공 시:** `{ "success": true, "message": "...", "data": { ... } }`
- **실패 시:** `{ "success": false, "message": "에러 내용", "data": null }`

### 💡 DB 테이블 자동 생성
`spring.jpa.hibernate.ddl-auto: update` 설정이 되어 있습니다.  
Entity 클래스만 작성하면 DB 테이블이 자동으로 생성/변경되니, 가급적 SQL을 직접 날리지 말고 코드로 관리해 주세요.

### ⚠️ 주의사항
IntelliJ 설정(Settings > Build, Execution, Deployment > Compiler > Annotation Processors)에서 Enable annotation processing을 반드시 체크해 주세요.

# 🌿 GitFlow & 협업 규칙 (Git & GitHub Convention)

본 프로젝트는 원활한 협업과 코드 충돌 방지를 위해 아래의 브랜치 전략과 커밋 규칙을 따릅니다.

## 1. 브랜치 전략 (Branch Strategy)
- `main` : 최종 운영 서버에 배포되는 안정적인 브랜치
- `develop_junho` : [준호] 개발용 통합 브랜치 (기능 개발 완료 후 병합되는 곳)
- `feature/{도메인명}-{작업내용}` : 개별 기능 개발 브랜치
    - 예시: `feature/store-entity` (매장 엔티티 개발), `feature/auth-login` (로그인 기능 개발)

> **💡 작업 흐름 (Workflow)**
> 1. `develop_junho` 브랜치에서 최신 코드를 받습니다. (`git pull origin develop_junho`)
> 2. 새로운 기능 브랜치를 생성합니다. (`git checkout -b feature/작업명`)
> 3. 기능 개발 완료 후 커밋 & 푸시합니다.
> 4. GitHub에서 `feature/...` ➡️ `develop_junho` 방향으로 Pull Request(PR)를 생성합니다.

## 2. 커밋 메시지 규칙 (Commit Convention)
커밋 메시지는 어떤 작업을 했는지 한눈에 알 수 있도록 아래의 태그를 앞에 붙여 작성합니다.
- `feat:` : 새로운 기능 추가 (엔티티, API 작성 등)
- `fix:` : 버그 수정
- `docs:` : 문서 수정 (HELP.md, README 등)
- `style:` : 코드 포맷팅, 세미콜론 누락 수정 등 (로직 변경 없음)
- `refactor:` : 코드 리팩토링 (기능은 동일하나 구조 개선)
- `chore:` : 빌드 설정, 패키지 매니저 설정, 간단한 수정

> **예시:** `feat: 매장(Store) 및 카테고리(Category) 엔티티 추가`

## 3. Pull Request (PR) 작성 규칙
PR을 올릴 때는 리뷰어가 쉽게 이해할 수 있도록 아래 양식을 본문에 포함합니다.

### 📌 작업 내용 (What)
- 무엇을 개발했는지 항목별로 요약

### 💡 설계 이유 및 고민 (Why & How)
- 왜 이런 구조로 설계했는지, 어떤 점을 고려했는지 작성