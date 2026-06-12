# DealMate Step 4

회원가입 입력 검증 강화 버전입니다.

## 추가된 검증

1. 필수 입력값 누락
   - 팝업: 미기입된 내용이 있습니다.
2. 비밀번호와 비밀번호 확인 불일치
   - 팝업: 비밀번호가 일치하지 않습니다.
3. 이메일 형식 오류
   - 팝업: 올바른 이메일 형식이 아닙니다.
4. 아이디 중복
   - 팝업: 이미 사용 중인 아이디입니다.

## 실행 방법

```powershell
cd C:\Users\User\Desktop\dealmate
.\build.bat
java -jar dealmate.jar
```

브라우저 접속:

```text
http://localhost:8080/login
```

## 테스트 계정

```text
일반 사용자: demo / demo123
관리자: admin / admin123
```
