## IT Portfolio Mall

Spring Boot 기반으로 구현한 개인 포트폴리오용 쇼핑몰 웹 애플리케이션입니다.  
회원가입, 이메일 인증, 로그인 및 자동 로그인 기능을 직접 구현하여  
웹 서비스의 인증 흐름과 사용자 상태 관리에 대한 이해를 목표로 개발했습니다.

---

## 프로젝트 개요

IT Portfolio Mall은 기본적인 쇼핑몰 UI를 바탕으로  
회원 인증과 로그인 상태 유지 기능을 중심으로 구현한 프로젝트입니다.

사용자는 이메일 인증을 통해 회원가입을 진행할 수 있으며,  
로그인 이후에는 세션과 쿠키를 활용해 로그인 상태가 유지됩니다.  
자동 로그인 기능을 통해 사용자의 편의성을 고려한 인증 흐름을 설계했습니다.

---

## 주요 기능

- 회원가입
- 이메일 인증 (인증 코드 + 유효시간 제한)
- 로그인 / 로그아웃
- 자동 로그인 (쿠키 기반)
- 세션 기반 로그인 유지
- 로그인 상태에 따른 상단 메뉴 변경
- 상품 목록 메인 페이지 출력

---

## 화면 미리보기

### 메인 페이지
![메인 페이지](/images/main.png)

### 로그인 모달
![로그인 모달](/images/login_modal.png)

### 회원가입 페이지
![회원가입 페이지](/images/register.png)

### 이메일 인증
![이메일 인증](/images/email_verify.png)

### SMTP 이메일 전송
![이메일](/images/email.png)

### 이메일 인증 완료
![이메일 인증 완료](/images/email_ok.png)

### 로그인 완료
![로그인 완료](/images/login_ok.png)

---

## 기술 스택

### Backend
- Java
- Spring Boot
- Spring MVC
- MyBatis
- MySQL
- JavaMailSender
- HTTP Session
- Cookie

### Frontend
- HTML
- CSS
- JavaScript
- Bootstrap

---

## 백엔드 핵심 구현

### 자동 로그인 필터 (AutoLoginFilter)
```java
    @Component
    @Order(1)
    public class AutoLoginFilter implements Filter {

        private final UserService userService;

        public AutoLoginFilter(UserService userService) {
            this.userService = userService;
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                throws IOException, ServletException {

            HttpServletRequest req = (HttpServletRequest) request;
            Cookie[] cookies = req.getCookies();

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("autoLogin".equals(cookie.getName())) {
                        UserDTO user = userService.autoLogin(cookie.getValue());
                        if (user != null) {
                            req.getSession().setAttribute("loginEmail", user.getUserEmail());
                            break;
                        }
                    }
                }
            }
            chain.doFilter(request, response);
        }
    }
```
---

### 로그인 처리 로직
```java
    @PostMapping("/login")
    public String login(UserDTO userDTO, HttpSession session, HttpServletResponse response) {
        UserDTO loginUser = userService.login(userDTO);

        if (loginUser != null) {
            session.setAttribute("loginEmail", loginUser.getUserEmail());
            session.setAttribute("loginName", loginUser.getUserName());

            Cookie cookie = new Cookie("autoLogin", loginUser.getUserEmail());
            cookie.setPath("/");
            cookie.setMaxAge(7 * 24 * 60 * 60);
            response.addCookie(cookie);

            return "redirect:/";
        }
        return "redirect:/";
    }
```
---

### 이메일 인증 코드 발송
```java
    public void sendAndSaveVerificationCode(String email) {
        String code = generateVerificationCode();
        verificationCodes.put(email, code);
        expirationTimes.put(email, LocalDateTime.now().plusMinutes(3));
        emailService.sendVerificationEmail(email, code);
    }
```
---

### 인증 코드 검증
```java
    public boolean verifyCode(String email, String code) {
        String validCode = verificationCodes.get(email);
        LocalDateTime expire = expirationTimes.get(email);

        if (validCode != null && validCode.equals(code)
                && expire != null && LocalDateTime.now().isBefore(expire)) {
            verificationCodes.remove(email);
            expirationTimes.remove(email);
            return true;
        }
        return false;
    }
```
---

### MyBatis Mapper
```java
    @Mapper
    public interface UserMapper {
        void save(UserDTO userDTO);
        UserDTO findByUserEmail(String email);
        boolean existsByUserEmail(String userEmail);
    }
```
---

## 데이터 흐름 요약

1. 회원가입 요청
2. 이메일 중복 검사
3. 인증 코드 이메일 발송
4. 인증 코드 검증
5. 사용자 정보 저장
6. 로그인 처리
7. 세션 생성
8. 자동 로그인 쿠키 생성

---

## 프로젝트 목적

- Spring Boot 기반 웹 애플리케이션 구조 이해
- MyBatis를 활용한 데이터 접근 로직 구현
- 세션과 쿠키를 활용한 인증 처리 경험
- 이메일 인증 시스템 직접 구현
- 실제 서비스 흐름을 고려한 기능 설계 경험

---

## 향후 개선 사항

- 비밀번호 암호화 적용
- 상품 상세 페이지 구현
- 장바구니 기능 고도화
- 주문 및 결제 기능 추가
- 관리자 페이지 구현
- 보안 강화

---

## 비고

본 프로젝트는 개인 학습 및 포트폴리오 목적의 프로젝트입니다.
