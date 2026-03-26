<h1 align="center">DEALMATE</h1>

<h3 align="center">Community co-purchases</h3>

<p align="center">
<img width="300" height="300" alt="스크린샷 2026-03-27 004402" src="https://github.com/user-attachments/assets/43ec66a6-59cd-40d5-897b-f18f5c28ea2e" />
</p>
<p align="center">
22413554 <br/>  김현경 <br/> 2120khk@naver.com(
rhdiddl7899@yu.ac.kr)</p>

---
<br/><br/><br/>
---
<h2 align="center">Revision History</h2>

| Revision date | Version # | Description | Author |
|:-------------:|:--------:|:-----------:|:------:|
|             |   1.0    | First Draft   | 김현경 |
|             |          |             |        |
|             |          |             |        |
|             |          |             |        |
|             |          |             |        |
|             |          |             |        |

---
<br/><br/><br/>
---
<h2 align="center">= Contents =</h2>

Business purpose ..................................................................................  3

System context diagram .......................................................................  4

Use case list .........................................................................................  6

Concept of operation ............................................................................  8

Problem statement ................................................................................  9

Glossary .................................................................................................  10

References .................................................................................................  10

---
<br/><br/><br/>
---
<h2 align="center">1. Business purpose</h2>


<h3>Project background</h3>

<img width="200" height="250" alt="image" src="https://github.com/user-attachments/assets/f22ecdca-d252-4fb1-b52e-1ea2146381bc" /> <img width="250" height="200" alt="스크린샷 2026-03-27 022027" src="https://github.com/user-attachments/assets/76771d8e-b1ce-4580-9f9b-2ec3100b976b" /> <img width="200" height="250" alt="image" src="https://github.com/user-attachments/assets/feb27899-d70d-445a-9962-8a1804c4f771" />


 2026년 기준 전쟁 및 다양한 이유로 인해 인플레이션(물가상승)을 겪고 있다. 물가상승은 식료품, 생필품 구매와 같은 기본적인 소비에도 영향을 끼친다. 이러한 영향은 1인가구에게 더 큰 문제점으로 다가온다. 2024년 기준 1인가구는 전체 가구 중 36.1%를 차지하는데 혼자 거주하는 경우에 가성비 구매와 배달비 분담이 필수적이지만 현실적으로는 어렵다. 특히 식료품과 생필품 경우에는 가성비 구매를 위해 대용량 구매가 필요한데 이 경우에는 소비기한 내에 사용이 어려워 효율이 떨어진다. 또한 배달 최소 주문금액을 충족하기 위해 불필요하게 많은 양의 음식을 주문하게 된다. 심지어 SNS 공구 (공동구매)에서도 3+1 세트와 같이 여러 개를 구매했을 때 할인이 크게 적용되는 경우가 대부분이다. 앞서 말한 공동구매가 생겨난 계기도 이러한 이유에서 비롯된다. 공동 구매는 수요가 많은 제품을 여러 명이서 구매하는 방식으로 판매자가 원 가격에서 저렴하게 판매하여 소비자는 배송비 무료와 저렴한 가격으로 구매하는 방식이다. 대부분 학생 때 교복 공동구매를 생각하면 이해가 쉬울 것이다. 이처럼 할인 상품 정보를 공유하고 그 정보를 통해 구매하고자 하는 상품이 같은 사람들끼리 모여 가성비 있는 소비를 할 수 없을까? 라는 고민이 들어 이 프로젝트를 구상하게 되었다. 
 
 조금 다른 예시로 몇 년 전까지만 해도 OTT 구독료 할인을 위해 가족계정 공유를 통해 친구 혹은 모르는 사람들과 구독료를 나눠서 결제하는 방식이 유행했었다. 그러나 장기구독 특성상 구독 멤버 중 한 명이라도 이탈 시 새로운 사람을 찾아야 한다는 점, 모르는 사람과 개인정보 유출의 위험성이 발생했다. 그러나 DEALMATE 프로젝트는 같은 동네를 기반으로 이루어지기 때문에 1인 소비의 한계를 보완하면서 신뢰 기반 환경 구축에 도움이 된다.
 
<h3>Goal</h3>
⦁ 동네 이웃간의 신뢰 기반의 할인구매

⦁ 특가 정보 공유

⦁ 더 나아가 이웃 커뮤니티로 발전 가능

<h3>Target market</h3>
⦁ 1인 가구 

⦁ 합리적인 소비를 원하는 지역 주민 

---
<br/><br/><br/>
---
<h2 align="center">2. System context diagram</h2>
<img width="904" height="1009" alt="image" src="https://github.com/user-attachments/assets/cb385707-2217-4893-a561-cec25f9d0803" />


⦁join 회원가입
⦁login  로그인
⦁logout 로그아웃
⦁information upload 특가정보 입력
⦁select region 지역 설정
⦁interest filter 관심사 필터 지정
⦁ocr 가격 영수증 인식 및 링크 정보인식
⦁create group 그룹생성
⦁auto calculation 자동 정산금액안내
⦁reveiw 별점 등록


---
<br/><br/><br/>
---
<h2 align="center">3. Use case list</h2>


Find use cases in your project.

Make your short description for each use case (table type).

12pt, 160%.

ex)

Login
<div style="overflow-x:auto"><table cellspacing="0" class="borderfill-2" style=" width: 167.34mm; border-collapse: collapse; "><tr><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 35.75mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">Actor</p></td><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 131.59mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">Customer, Manager</p></td></tr><tr><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 35.75mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">Description</p></td><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 131.59mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">고객과 매니저가 각자 자신의 아이디로 로그인한다.</p></td></tr></table></div>

---
<br/><br/><br/>
---
<h2 align="center">4. Concept of operation</h2>

Describe how to operate the use cases (table type).
12pt, 160%.

ex)

Login
<div style="overflow-x:auto"><table cellspacing="0" class="borderfill-2" style=" width: 167.34mm; border-collapse: collapse; "><tr><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 35.75mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">Purpose</p></td><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 131.59mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">앱을 사용하기 위해 등록된 사용자인지 확인</p></td></tr><tr><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 35.75mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">Approach</p></td><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 131.59mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">사용자가 앱을 실행 후 로그인 시,ID, PW를 입력 후 로그인을 요청하면 서버에서 회원 정보를 조회 후 로그인 성공/실패 여부 확인한다.</p></td></tr><tr><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 35.75mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">Dynamics</p></td><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 131.59mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">앱 실행 시 로그인할 경우</p></td></tr><tr><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 35.75mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">Goals</p></td><td class="borderfill-2" colspan="1" rowspan="1" style=" width: 131.59mm; height: 6.23mm; padding: 0.5mm 1.8mm 0.5mm 1.8mm; "><p class="Normal">로그인 기능을 구현한다.</p></td></tr></table></div>
---
<br/><br/><br/>
---
<h2 align="center">5. Problem statement</h2>
Describe the problems the project should be considered (including technical difficulties).
Describe the Non-Functional Requirements (NFRs).


---
<br/><br/><br/>
---
<h2 align="center">6. Glossary</h2>

Specifically describe all of the terms used in this documents.

---
<br/><br/><br/>
---
<h2 align="center">7. References</h2>
2025 통계로 보는 1인가구 - https://www.kostat.go.kr/board.es?mid=a10301010000&bid=10820&act=view&list_no=442130&tag=&nPage=1&ref_bid=203,204,205,206,207,210,211,11109,11113,11814,213,215,214,11860,11695,216,218,219,220,10820,11815,11895,11816,208,245,222,223,225,226,227,228,229,230,11321,232,233,234,12029,10920,11469,11470,11817,236,237,11471,238,240,241,11865,243,244,11893,11898,12031,11825,

https://developers.google.com/ml-kit/vision/text-recognition/v2/android?hl=ko
