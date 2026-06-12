# DealMate Step 13

수정 내용

1. 로그인/회원가입 화면처럼 로그인 이후 화면도 고정 모바일 프레임으로 통일
   - 흰색 앱 화면
   - 둥근 모서리
   - 고정 크기
   - 내부 스크롤 숨김 처리

2. 공구 상세페이지 흐름 수정
   - 정산 상세 확인하기 버튼 제거
   - 상단 탭에서 `공구 상세페이지`를 누르면 바로 정산 UI 표시
   - 호스트 정산 요청 / 참여자 정산 확인 UI는 공구 상세페이지 안에 포함

3. 정산 상세페이지 UI 통일
   - 회색/하늘색 배경 제거
   - 흰색 배경, 카드, 파란 버튼 스타일 유지

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
