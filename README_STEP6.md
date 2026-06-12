# DealMate Step6

## 이번 단계 목표
- 로그인 성공 시 `/home` 메인화면으로 이동
- 회원가입 성공 시 `/neighborhood` 동네 인증 화면으로 이동
- 동네 인증 성공 시 `/login` 로그인 화면으로 이동
- 기본 검증을 기능 구현 전에 먼저 반영

## 자동 검증 목록
### Register
- 빈칸 검사
- 비밀번호 확인 불일치 검사
- 이메일 형식 검사
- 중복 아이디 검사

### Login
- 빈칸 검사
- 아이디 또는 비밀번호 불일치 검사

### Neighborhood Certification
- 지역 입력값 빈칸 검사
- 인증 실패 시 팝업 표시

## 실행 방법
```powershell
cd C:\Users\User\Desktop\dealmate
.\build.bat
java -jar dealmate.jar
```

브라우저에서 접속:
```text
http://localhost:8080/login
```
