# DEALMATE

## 실행 환경
- JDK 17 이상
- Windows 10 이상
- Chrome 또는 Edge

## 실행 방법
1. 압축을 해제합니다.
2. 프로젝트 폴더에서 build.bat을 실행합니다다.
3. dealmate.jar 파일이 생성되면 아래 명령어를 실행합니다.
   
실행 명령어
java -jar dealmate.jar

5. 웹 브라우저에서 아래 주소로 접속합니다다.

http://localhost:8080/login

## 테스트 계정
일반 사용자: demo / demo123
일반 사용자: demo1 / demo123
관리자: admin / admin123

## 최종 구현 기준
README_STEP29_ADMIN_STATUS_FIX.md
dealmate.jar
src/com/dealmate
data

### 비고
본 프로젝트는 Java 내장 HttpServer 기반 로컬 실행 방식입니다.
외부 배포 서버가 아닌 실행 파일을 실행하면 로컬 서버가 열리는 방식으로 구현하였습니다.
관리자는 로그인 기능을 통해 접근하므로 구현상 User 계정 정보를 함께 사용하지만, 기능적으로는 일반 사용자와 구분되어 리뷰 확인 및 사용자 제재 기능만 수행합니다.
