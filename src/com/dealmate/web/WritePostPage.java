package com.dealmate.web;

public class WritePostPage {
    public static String render() {
        return WebComponents.head("DealMate Write Post") + """
                <main class=\"phone\">
                    <div class=\"top-bar\"><a class=\"back\" href=\"/home\">‹</a><div class=\"bar-title\">특가 정보글 작성</div><div></div></div>
                    <header class=\"top-header\">%s</header>
                    <section>
                        <h2 class=\"page-title\">특가 정보글 작성</h2>
                        <p class=\"subtext\">동네 이웃에게 공유할 할인 정보를 작성해주세요.</p>
                        <label>제목</label>
                        <input id=\"title\" placeholder=\"예: 편의점 1+1 행사 공유\">
                        <label>내용</label>
                        <textarea id=\"content\" placeholder=\"특가 위치, 가격, 기간 등을 입력하세요\"></textarea>
                        <label>사진 첨부 <span style=\"font-weight:700;color:#999;font-size:12px;\">선택사항</span></label>
                        <input id=\"image\" type=\"file\" accept=\"image/*\" style=\"display:none;\" onchange=\"markImageSelected()\">
                        <button type=\"button\" class=\"button outline\" style=\"margin-top:6px;\" onclick=\"document.getElementById('image').click()\">
                            <span id=\"imageStatus\">사진 선택하기</span>
                        </button>
                        <p class=\"subtext\" style=\"margin-top:8px;margin-bottom:0;\">사진은 첨부하지 않아도 게시글 등록이 가능합니다.</p>
                        <button class=\"button primary\" style=\"margin-top:18px;\" onclick=\"submitPost()\">게시글 등록</button>
                    </section>
                    %s
                </main>
                <div class=\"modal\" id=\"modal\"><div class=\"popup\"><h2 id=\"modalTitle\">입력 오류</h2><p id=\"modalMessage\">내용을 채워주세요.</p><button onclick=\"closeModal()\">확인</button></div></div>
                <script>
                    let nextUrl = '';
                    function markImageSelected(){
                        const imageInput = document.getElementById('image');
                        const status = document.getElementById('imageStatus');
                        status.textContent = imageInput.files && imageInput.files.length > 0 ? '이미지 첨부됨' : '사진 선택하기';
                    }
                    async function submitPost(){
                        const title = document.getElementById('title').value.trim();
                        const content = document.getElementById('content').value.trim();
                        const imageInput = document.getElementById('image');
                        const hasImage = imageInput.files && imageInput.files.length > 0 ? 'true' : 'false';
                        if(!title || !content){ showModal('입력 오류', '내용을 채워주세요.'); return; }
                        const body = new URLSearchParams({title, content, hasImage});
                        const response = await fetch('/api/write-post', {method:'POST', headers:{'Content-Type':'application/x-www-form-urlencoded'}, body});
                        const result = await response.json();
                        if(result.success){ nextUrl = result.next; showModal('등록 완료', result.message); }
                        else { showModal('입력 오류', result.message || '내용을 채워주세요.'); }
                    }
                    function showModal(title, message){ document.getElementById('modalTitle').textContent=title; document.getElementById('modalMessage').textContent=message; document.getElementById('modal').classList.add('show'); }
                    function closeModal(){ document.getElementById('modal').classList.remove('show'); if(nextUrl){ location.href=nextUrl; } }
                </script>
                """.formatted(WebComponents.logo(), WebComponents.bottomNav("write")) + WebComponents.end();
    }
}
