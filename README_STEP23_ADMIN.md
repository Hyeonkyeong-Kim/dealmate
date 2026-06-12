# DealMate Step23 Admin

사용자 UI는 step22_fix2 기준으로 유지하고, 관리자 기능만 같은 디자인 톤으로 추가한 버전입니다.

## 실행

```powershell
cd C:\Users\User\Desktop\dealmate
.\build.bat
java -jar dealmate.jar
```

브라우저 접속:

```text
http://localhost:8080/login
```

## 테스트 계정

```text
일반 사용자: demo / demo123
일반 사용자: demo1 / demo123
관리자: admin / admin123
```

## 관리자 기능

- admin / admin123 로그인 시 /admin 관리자 홈으로 이동
- 사용자 UI와 동일한 모바일 프레임, 로고, 색상, 하단 메뉴 스타일 사용
- 관리자 홈: 저평점 사용자 현황 확인
- 유저 관리: 평균 별점 낮은 순으로 사용자 표시
- 유저 관리 조치: 24시간 활동 제한, 계정 정지
- 리뷰 확인: 사용자별 리뷰와 평균 별점 확인
- 관리자 마이: 관리자 정보 및 로그아웃

## 관리자 검증

- 대상 사용자 또는 조치가 비정상인 경우: "적용할 관리 조치를 선택하세요."
- 관리 조치 성공 시 사용자 계정 상태가 "24시간 활동 제한" 또는 "계정 정지"로 변경됨
