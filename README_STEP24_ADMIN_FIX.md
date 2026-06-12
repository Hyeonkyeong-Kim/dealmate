# DealMate Step24 Admin Fix

사용자 UI는 step22_fix2 기준으로 유지하고, 관리자 UI만 수정한 버전이다.

## 수정 내용

1. admin / admin123 로그인 시 `/admin?tab=reviews` 관리자 화면으로 이동한다.
2. 관리자 하단 메뉴는 3개로 구성한다.
   - 리뷰확인
   - 유저관리
   - 마이
3. 리뷰확인 화면은 관리자 메인 역할을 함께 수행한다.
   - 낮은 별점순 표시
   - 관리 필요 사용자 수
   - 가장 낮은 별점 사용자
   - 사용자별 평균 별점
   - 사용자별 리뷰 수
   - 대표 리뷰
4. 유저관리 화면은 필터를 제공한다.
   - 낮은 별점순
   - 24시간 제한 적용 유저
   - 계정 정지 유저
5. 유저관리 화면에서 24시간 제한, 계정 정지 버튼을 누르면 상태가 반영된다.
6. 마이 화면에서 관리자 계정 정보와 로그아웃을 확인할 수 있다.

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

관리자 계정:

```text
admin / admin123
```
