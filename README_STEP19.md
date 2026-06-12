# DealMate Step19

사용자 UI 누락 기능 통합 수정 버전입니다.

## 반영 내용

1. 특가 정보 상세 페이지 연결 유지
2. 특가 정보글 작성 후 홈 목록에 추가
3. 공구 방 생성 후 공구 목록에 추가
4. 공구 참여 시 참여 인원 증가
5. 공구 참여 시 마이페이지의 내가 참여한 공구에 반영
6. 모집 인원 초과 또는 중복 참여 시 팝업 출력
   - 모집 인원이 마감되었거나 참가할 수 없습니다.
7. OCR 직접 입력 금액 변경 버튼 반영 유지
8. 리뷰 등록 시 대상 선택 후 저장
9. 리뷰 등록 후 마이페이지 리뷰/별점 데이터 반영
10. 앱 화면 프레임과 하단 메뉴 위치 통일 유지

## 실행

```powershell
cd C:\Users\User\Desktop\dealmate
.\build.bat
java -jar dealmate.jar
```

접속:

```text
http://localhost:8080/login
```

## 테스트 계정

```text
demo / demo123
demo1 / demo123
admin / admin123
```
