# IT Shop 포트폴리오 프로젝트

Spring Boot와 Mybatis, Oracle DB를 기반으로 한 IT 쇼핑몰 포트폴리오 웹사이트입니다.
회원가입, 이메일 인증, DB 토큰 기반의 안전한 자동 로그인 기능 등이 구현되어 있습니다.

<br>

## 📋 주요 기능

* **회원 관리**
    * 회원가입 (이름, 이메일, 비밀번호, 연락처, 생년월일)
    * 로그인 (세션 방식)
    * 로그아웃
* **보안 및 인증**
    * **이메일 인증:** Gmail(SMTP)을 이용한 인증 코드 발송 및 3분 타이머 기능
    * **AJAX:** 회원가입 시 이메일 중복 실시간 검사
    * **자동 로그인:** DB에 랜덤 토큰을 저장하는 방식의 안전한 자동 로그인 (Remember-Me)
* **쇼핑몰**
    * 상품 목록 메인 페이지 (현재 `index.html`에 정적 데이터로 구성)

<br>

## 🛠️ 사용 기술

| 구분 | 기술 스택 |
| :--- | :--- |
| **Backend** | Java 17, Spring Boot 3.2.4, Spring Mail |
| **Database** | Oracle DB, Mybatis 3.0.3 |
| **Frontend** | HTML5, CSS (Bootstrap), JavaScript (ES6+), Thymeleaf |
| **Build Tool** | Gradle |
| **Server** | Embedded Tomcat |

<br>

## 🚀 시작하기

이 프로젝트를 로컬 환경(집 컴퓨터 등)에서 실행하기 위한 단계입니다.

### 1. Oracle DB 설정

Mybatis를 사용하므로 JPA처럼 테이블이 자동 생성되지 않습니다.
Oracle DB에 접속하여 (`portfolio_user` 계정으로) 다음 SQL 쿼리를 **반드시** 먼저 실행해야 합니다.

#### 1-1. users 테이블 생성
```sql
CREATE TABLE users (
    id NUMBER PRIMARY KEY,
    user_email VARCHAR2(100) UNIQUE NOT NULL,
    user_pw VARCHAR2(100) NOT NULL,
    user_name VARCHAR2(50),
    user_phone VARCHAR2(50),
    user_birth VARCHAR2(20)
);