# DealMate STEP10

## 수정 내용

1. 메인 화면에서 동작하지 않는 검색 입력창 제거
2. 메인 화면 우측 하단 + 글쓰기 버튼 제거
3. 특가 정보글 작성은 하단 `글쓰기` 탭에서만 이동하도록 정리
4. 공구 탭의 `참여 가능` 탭 제거
5. 공구 탭의 `정산 진행` 문구를 `공구 상세페이지`로 변경
6. 공구 탭의 `공구 글 작성`, `상세 보기`, `참여하기` 버튼이 실제 화면으로 이동하도록 수정
7. `/create-room` 공구 글 작성 화면 추가
8. `/room-detail` 공구 상세페이지 추가
9. 공구 방 생성 필수 정보 미입력 시 Analysis 문서 오류 문구인 `필수 정보를 모두 입력해주세요.` 팝업 출력

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

## 테스트 계정

```text
demo / demo123
demo1 / demo123
admin / admin123
```
