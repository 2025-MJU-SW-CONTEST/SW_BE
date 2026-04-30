# 🎬 FillMate - Backend

> **AI 기반 영화 해석 커뮤니티 서비스**  
> 2025 MJU SW Contest 출품작 | 교내 창의적SW프로그램경진대회 **우수상** 수상

FillMate는 사용자가 영화의 숨겨진 의미를 탐구하고, AI 챗봇 **"필리"** 와 대화하며 영화 해석을 나눌 수 있는 커뮤니티 서비스입니다.

---

## 📌 프로젝트 소개

영화를 본 후 궁금했던 장면, 감독의 의도, 숨겨진 메시지를 AI와 함께 탐구할 수 있습니다.  
사용자들은 자신만의 해석을 공유하고, 다른 사람들의 관점을 통해 영화를 더 깊이 이해할 수 있습니다.

### 기대 효과

1. **사용자 참여와 콘텐츠 생성** — 영화에 대한 자신의 해석을 공유하고, 다양한 관점의 해석을 접하며 참여감과 만족감을 증대
2. **AI 기반 대화형 서비스** — 궁금한 영화 장면에 대해 AI가 즉각적인 해석을 제공하며, 개인 맞춤형 해석 추천 및 자동 요약 기능 제공
3. **영화의 의미 탐구** — 다양한 관점에서 영화의 숨겨진 메시지를 찾아내는 해석의 재미와 깊이 제공

---

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| **Application Framework** | Spring Boot, FastAPI |
| **Security & Auth** | Spring Security, JWT |
| **Data & Relational DB** | MySQL, Amazon RDS |
| **Cache & Session** | Redis |
| **Streaming & Messaging** | Kafka |
| **AI/LLM Orchestration** | LangChain |
| **Container & Registry** | Docker, Amazon ECR |
| **Compute & Hosting** | Amazon EC2 |
| **CI/CD** | GitHub Actions |

---

## 🏗 시스템 아키텍처

### AWS 서버 구축

```
Client → (SSL/TLS) → ALB → EC2 (Spring Boot + Kafka) → Amazon RDS
                              ↓
                        EC2 (FastAPI + Redis)
```

- HTTPS 보안 처리를 위해 **ALB(Application Load Balancer)** 사용
- **EC2 2대** 구성: Spring Boot 기반 백엔드 서버 + FastAPI 기반 AI 서버
- 데이터 저장은 **Amazon RDS (MySQL)** 를 통해 관리

### CI/CD 파이프라인

```
Developer → GitHub (push) → GitHub Actions (run) → Docker (build) → ECR (push)
                                                                        ↓
                                                                   EC2 (compose up)
```

- GitHub push 이벤트 발생 시 **GitHub Actions**가 자동으로 트리거
- **Docker** 이미지를 빌드하고 **Amazon ECR**에 푸시
- EC2에서 `docker compose up`으로 자동 배포

---

## 📂 프로젝트 구조

```
src/main/java/com/example/sw_be/
├── domain/
│   ├── aiChat/            # AI 챗봇 (필리) 채팅 기능
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   ├── aiChatRoom/        # AI 채팅방 관리
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   ├── movie/             # 영화 정보 관리
│   │   ├── controller/
│   │   ├── dto/
│   │   ├── entity/
│   │   ├── repository/
│   │   └── service/
│   └── user/              # 사용자 관리
│       ├── entity/
│       ├── repository/
│       └── service/
├── global/
│   ├── common/            # 공통 응답, 페이징 유틸
│   ├── config/            # 설정 클래스
│   └── security/          # Spring Security 설정
└── SwBeApplication.java
```

---

## 🔗 주요 API

### 🎬 Movie API

| Method | Endpoint | 설명 |
|--------|----------|------|
| `GET` | `/api/movie/list` | 영화 리스트 조회 (페이징) |
| `GET` | `/api/movie/{id}` | 영화 상세 조회 |
| `GET` | `/api/movie/search` | 영화 제목 검색 |

### 🤖 AI Chat API

| Method | Endpoint | 설명 |
|--------|----------|------|
| `POST` | `/api/chats/{aiChatRoom-Id}` | AI 채팅 전송 (일반 채팅 / 제목 검색) |
| `POST` | `/api/analysis` | 사용자 해석 데이터 LLM에 추가 |

### 💬 AI ChatRoom API

| Method | Endpoint | 설명 |
|--------|----------|------|
| `POST` | `/api/ai-chatroom` | 새 AI 채팅방 생성 |
| `GET` | `/api/ai-chatroom/{id}/history` | 채팅방 대화 내역 조회 |
| `GET` | `/api/ai-chatroom/recent` | 최근 채팅방 조회 |

---

## ⚙️ 실행 방법

### 사전 요구사항

- Java 17+
- Docker & Docker Compose
- MySQL 8.0+
- Redis
- Kafka

### 로컬 실행

```bash
# 1. 레포지토리 클론
git clone https://github.com/your-org/fillmate-backend.git
cd fillmate-backend

# 2. 환경 변수 설정
cp .env.example .env
# .env 파일에 DB, Redis, Kafka 등 설정 입력

# 3. Docker Compose로 인프라 실행
docker compose up -d

# 4. 애플리케이션 실행
./gradlew bootRun
```

### Docker 빌드 & 실행

```bash
# 이미지 빌드
docker build -t fillmate-backend .

# 컨테이너 실행
docker run -p 8080:8080 fillmate-backend
```

---

## 📋 Git 컨벤션

### Branch 전략

- `main` — 배포 브랜치
- `develop` — 개발 통합 브랜치
- `feat/기능명` — 기능 개발 브랜치

### Issue Template

```markdown
## 📌 Description
- [ ] 구현할 기능 설명
```

### PR Template

```markdown
## 🌱 관련 이슈
- close : #이슈번호

## 📌 작업 내용 및 특이사항
## 📝 참고사항
## 📚 기타
```

---

## 🏆 수상

- **2025 MJU 교내 창의적SW프로그램경진대회 우수상** — 명지대학교
