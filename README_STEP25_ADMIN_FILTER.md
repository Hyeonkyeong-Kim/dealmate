# DealMate Step25 Admin Filter Update

## 변경 내용

1. 관리자 리뷰확인 화면
   - 낮은 별점순 정렬
   - 높은 별점순 정렬
   - 평균 별점, 리뷰 수, 대표 리뷰 표시 유지

2. 관리자 유저관리 화면
   - 낮은 별점순 정렬
   - 높은 별점순 정렬
   - 24시간 제한 유저 모아보기
   - 계정 정지 유저 모아보기

3. 필터 상태별 관리 버튼 변경
   - 낮은 별점순 / 높은 별점순: [24시간 제한] [계정 정지]
   - 24시간 제한 필터: [계정 정지] [해제하기]
   - 계정 정지 필터: [24시간 제한] [해제하기]

4. 제재 해제 기능 추가
   - 해제하기를 누르면 계정 상태가 정상으로 변경됨

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

관리자 계정:

```text
admin / admin123
```
