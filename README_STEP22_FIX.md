# DealMate Step22 Fix

- 특가 정보글 작성 화면에서 사진 첨부 선택사항 UI가 보이지 않던 문제 수정
- 제목/내용은 필수, 사진은 선택사항
- 사진 선택 시 '이미지 첨부됨' 표시
- 상세 페이지에서는 기존 step22와 같이 이미지 미리보기 영역 표시

실행:
```powershell
cd C:\Users\User\Desktop\dealmate
.\build.bat
java -jar dealmate.jar
```

접속:
```text
http://localhost:8080/login
```
