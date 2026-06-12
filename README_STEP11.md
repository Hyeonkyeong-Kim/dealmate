# DealMate Step11

## 반영 내용

1. 로그인 이후 화면 크기를 390px x 844px 기준으로 고정했다.
2. 로그인 이후 화면들의 DEALMATE 로고, 하단 탭, 카드 UI를 통일했다.
3. 메인 특가 정보 카드 클릭 시 임시 글 작성 화면이 아니라 특가 정보 상세 페이지로 이동한다.
4. 글쓰기 탭에 실제 특가 정보글 작성 UI를 추가했다.
5. 특가 글 작성 시 제목 또는 내용이 비어 있으면 Analysis 문서 문구인 "내용을 채워주세요." 팝업을 표시한다.
6. 공구 탭의 상세 보기/참여하기는 공구 상세페이지로 이동하게 정리했다.
7. 공구 상세페이지 안에서 참여와 정산 상세 확인으로 이어지게 했다.
8. settlement, review 화면을 임시 카드 화면이 아니라 같은 UI 톤의 화면으로 정리했다.
9. Design 문서의 User.writePost(title, content), Post.writePost(title, content), Post.savePost() 흐름을 코드에서 연결했다.

## 실행

```powershell
cd C:\Users\User\Desktop\dealmate
.\build.bat
java -jar dealmate.jar
```

브라우저:

```text
http://localhost:8080/login
```
