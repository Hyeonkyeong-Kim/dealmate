# DEALMATE

지역 기반 공동구매와 특가 정보 공유를 지원하는 Java 기반 시연용 웹 애플리케이션입니다.

## 실행 환경

- JDK 17 이상
- Windows 10 이상
- Chrome 또는 Edge 브라우저

## 빌드 방법

PowerShell 또는 CMD에서 프로젝트 폴더로 이동한 뒤 아래 명령어를 실행합니다.

```bat
build.bat
```

Mac/Linux 환경에서는 아래 명령어를 실행합니다.

```bash
bash build.sh
```

## 실행 방법

빌드가 완료되면 아래 명령어를 실행합니다.

```bat
java -jar dealmate.jar
```

브라우저에서 아래 주소로 접속합니다.

```text
http://localhost:8080/login
```

## 시연 계정

일반 사용자:

```text
ID: demo
PW: demo123
```

관리자:

```text
ID: admin
PW: admin123
```

## 주요 구현 기능

- 회원가입
- 로그인
- 동네 인증
- 특가 정보글 작성
- 공동구매 방 생성
- 공동구매 참여
- 정산 요청
- 결제 영수증 인증 흐름
- 정산 금액 계산
- 송금 인증 업로드
- 리뷰 작성
- 관리자 리뷰 확인 및 사용자 제재

## 정산 구현 보강 내용

정산 요청은 `/api/request-settlement` API를 통해 처리합니다.

흐름은 다음과 같습니다.

```text
Host.uploadPaymentReceipt(receiptImage)
→ PaymentReceipt.uploadReceipt(receiptImage)
→ PaymentReceipt.extractAmountByOCR(receiptImage)
→ Host.requestSettlement(roomId)
→ Settlement.calculateAmount(totalAmount, count)
→ Settlement.updateSettlementStatus("pending")
→ Database.saveData(settlement)
```

송금 인증은 `/api/upload-transfer-proof` API를 통해 처리합니다.

```text
Member.uploadTransferProof(transferImage)
→ TransferProof.uploadTransferProof(transferImage)
→ TransferProof.updateProofStatus("confirmed")
→ Database.saveData(transferProof)
```

## 단순화한 기능

- 실제 GPS 인증은 지역 선택 방식으로 단순화했습니다.
- 실제 OCR은 시연용으로 단순화하고, 총 결제 금액 직접 입력 방식을 함께 제공합니다.
- 실제 결제 시스템과 실시간 채팅은 구현 범위에서 제외했습니다.
- 데이터는 실제 DB 대신 Java 내부 리스트와 일부 텍스트 파일로 관리합니다.

## 관리자 구현 설명

관리자는 로그인 기능을 통해 접근하므로 구현상 User 계정 정보를 함께 사용하지만, 기능적으로는 일반 사용자와 구분되어 리뷰 확인 및 사용자 제재 기능만 수행합니다.
