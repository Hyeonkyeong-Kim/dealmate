package com.dealmate.web;

public class CreateRoomPage {
    public static String render() {
        return WebComponents.head("DealMate Create Room") + """
                <main class=\"phone\">
                    <div class=\"top-bar\"><a class=\"back\" href=\"/group-list\">‹</a><div class=\"bar-title\">공구 글 작성</div><div></div></div>
                    <header class=\"top-header\">%s</header>
                    <section>
                        <h2 class=\"page-title\">공구 글 작성</h2>
                        <p class=\"subtext\">함께 구매할 상품 정보와 모집 조건을 입력하세요.</p>
                        <label>상품명</label><input id=\"productName\" placeholder=\"예: 생수 2L 6개 묶음\">
                        <label>설명</label><textarea id=\"description\" placeholder=\"모집 내용과 거래 방식을 입력하세요\"></textarea>
                        <label>최대 참여 인원</label>
                        <p class=\"subtext\" style=\"margin:-2px 0 7px;\">호스트를 포함한 전체 인원입니다. 예: 4명 = 호스트 1명 + 참여자 3명</p>
                        <input id=\"max\" type=\"number\" min=\"2\" placeholder=\"예: 4\" oninput=\"updatePreview()\">
                        <label>총 예상 결제 금액</label>
                        <p class=\"subtext\" style=\"margin:-2px 0 7px;\">호스트가 예상하는 전체 결제 금액입니다. 1인당 예상 금액은 자동 계산됩니다.</p>
                        <input id=\"price\" type=\"number\" min=\"1\" placeholder=\"예: 12000\" oninput=\"updatePreview()\">
                        <article class=\"mini-card\" style=\"margin-top:14px;\">
                            <h3>공구 금액 미리보기</h3>
                            <div class=\"info-row\"><span class=\"info-label\">총 예상 금액</span><span class=\"info-value\" id=\"preview-total\">0원</span></div>
                            <div class=\"info-row\"><span class=\"info-label\">최대 참여 인원</span><span class=\"info-value\" id=\"preview-max\">0명</span></div>
                            <div class=\"info-row\"><span class=\"info-label\">1인당 예상 금액</span><span class=\"info-value\" id=\"preview-per\" style=\"color:#007AFF;\">0원</span></div>
                        </article>
                        <button class=\"button primary\" style=\"margin-top:18px;\" onclick=\"submitRoom()\">공구 방 생성</button>
                    </section>
                    %s
                </main>
                <div class=\"modal\" id=\"modal\"><div class=\"popup\"><h2 id=\"modalTitle\">입력 오류</h2><p id=\"modalMessage\">필수 정보를 모두 입력해주세요.</p><button onclick=\"closeModal()\">확인</button></div></div>
                <script>
                    let nextUrl = '';
                    function formatWon(value){ return Number(value || 0).toLocaleString('ko-KR') + '원'; }
                    function updatePreview(){
                        const max = Number(document.getElementById('max').value || 0);
                        const price = Number(document.getElementById('price').value || 0);
                        document.getElementById('preview-total').textContent = formatWon(price);
                        document.getElementById('preview-max').textContent = max + '명';
                        document.getElementById('preview-per').textContent = max > 0 ? formatWon(Math.floor(price / max)) : '0원';
                    }
                    async function submitRoom(){
                        const productName = document.getElementById('productName').value.trim();
                        const description = document.getElementById('description').value.trim();
                        const max = document.getElementById('max').value.trim();
                        const price = document.getElementById('price').value.trim();
                        if(!productName || !description || !max || !price || Number(max) <= 1 || Number(price) <= 0){ showModal('입력 오류', '필수 정보를 모두 입력해주세요.'); return; }
                        const body = new URLSearchParams({productName, description, max, price});
                        const response = await fetch('/api/create-room', {method:'POST', headers:{'Content-Type':'application/x-www-form-urlencoded'}, body});
                        const result = await response.json();
                        if(result.success){ nextUrl = result.next; showModal('공구 방 생성 완료', result.message); }
                        else { showModal('입력 오류', result.message || '필수 정보를 모두 입력해주세요.'); }
                    }
                    function showModal(title, message){ document.getElementById('modalTitle').textContent=title; document.getElementById('modalMessage').textContent=message; document.getElementById('modal').classList.add('show'); }
                    function closeModal(){ document.getElementById('modal').classList.remove('show'); if(nextUrl) location.href=nextUrl; }
                    updatePreview();
                </script>
                """.formatted(WebComponents.logo(), WebComponents.bottomNav("group")) + WebComponents.end();
    }
}
