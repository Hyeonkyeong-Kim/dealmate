# DealMate Step 16

수정 내용

1. 로그인 성공 후 /home에서 ERR_EMPTY_RESPONSE가 뜨던 문제 수정
   - WebComponents.head() 내부 CSS의 % 문자가 String.formatted()와 충돌하던 문제 해결
   - 모든 로그인 이후 화면이 정상 렌더링되도록 수정

2. Step15의 화면 고정 구조 유지
   - 앱 프레임 중앙 고정
   - 하단 메뉴 위치 통일
   - 화면 내용만 내부 스크롤

3. 정산 직접 입력 금액 반영 기능 유지
   - OCR 오류 시 직접 입력 금액을 기준으로 총 결제 금액과 1인당 정산 금액 변경

실행 방법

```powershell
cd C:\Users\User\Desktop\dealmate
.\build.bat
java -jar dealmate.jar
```

접속 주소

```text
http://localhost:8080/login
```
