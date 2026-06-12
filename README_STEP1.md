# DealMate Step 1 - Login 화면 실행

## 실행 목표
브라우저에서 `http://localhost:8080/login` 접속 시 DealMate 로그인 화면이 표시된다.

## 빌드
```powershell
.\build.bat
```

## 실행
```powershell
java -jar dealmate.jar
```

## 테스트 계정
- 일반 사용자: `demo` / `demo123`
- 관리자: `admin` / `admin123`

## 현재 구현된 것
- Java 내장 `HttpServer` 사용
- `/login` 로그인 화면
- `/api/login` 로그인 검증
- 로그인 실패 팝업 메시지: `아이디 또는 비밀번호가 틀렸습니다.`
- Design 문서의 주요 클래스명/메서드명 skeleton 생성
