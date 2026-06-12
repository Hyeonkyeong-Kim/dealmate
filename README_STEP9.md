# DealMate Step 9

## 이번 단계 목표

- demo 계정별 동네 정보 다르게 저장
- 마이페이지에서 동네 인증 정보를 변경하면 홈 화면 배지에 반영
- demo/demo1 계정별 참여 공구, 리뷰, 평균 별점 예시 표시
- 관리자 계정도 당장은 일반 사용자와 같은 UI로 이동

## 테스트 계정

- demo / demo123 : 대구광역시 남구 대명동
- demo1 / demo123 : 대구광역시 수성구 범어동
- admin / admin123 : 관리자 계정이지만 현재 단계에서는 일반 사용자와 같은 UI로 표시

## 테스트 방법

1. http://localhost:8080/login 접속
2. demo1 / demo123 로그인
3. 홈 화면 배지가 범어동으로 뜨는지 확인
4. 마이페이지에서 참여 공구와 리뷰/평균 별점 확인
5. 동네 인증 정보 클릭
6. 직접 동네 선택에서 다른 동네 입력 후 인증
7. 마이페이지와 홈 화면 배지가 변경되는지 확인

## 실행

```powershell
cd C:\Users\User\Desktop\dealmate
.\build.bat
java -jar dealmate.jar
```
