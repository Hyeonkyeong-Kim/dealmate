# DealMate Step 7

## 변경 내용

- 동네 인증 화면을 2단계 흐름으로 수정
  1. 현재 위치 확인하기 / 직접 동네 선택
  2. 확인된 위치를 화면에 표시
  3. 사용자가 "이 위치로 인증하기"를 눌러야 최종 인증 완료
- 위치가 비어 있거나 이상하면 Analysis 문서의 오류 메시지 사용
  - 지역 인증에 실패했습니다. 다시 시도해주세요.
- 회원가입 성공 후 동네 인증 화면으로 이동하는 흐름 유지
- 동네 인증 성공 후 로그인 화면으로 이동하는 흐름 유지
- 로그인 성공 후 메인 화면으로 이동하는 흐름 유지

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
