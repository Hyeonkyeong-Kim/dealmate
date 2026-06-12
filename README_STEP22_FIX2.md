# DealMate Step22 Fix2

## 수정 내용

- 특가 정보글 상세 화면의 이미지 첨부 표시를 수정하였다.
- 사진 첨부 글의 상세 화면에 `[이미지 미리보기]` 영역을 표시한다.
- 시연용 구현임을 알 수 있도록 `시연 중에는 실제 이미지 미리보기가 제공되지 않습니다.` 안내 문구를 추가하였다.
- 실제 이미지 서버 저장 기능은 구현 범위에서 단순화하였다.

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
