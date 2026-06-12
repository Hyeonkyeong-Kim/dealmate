package com.dealmate.web;

import com.dealmate.model.GroupPurchaseRoom;
import com.dealmate.model.User;
import java.util.List;

public class ReviewPage {
    public static String render(User currentUser, List<GroupPurchaseRoom> rooms) {
        String current = currentUser == null ? "demo" : currentUser.getUserId();
        StringBuilder options = new StringBuilder();
        options.append("<option value=\"demo\">호스트 demo</option>");
        options.append("<option value=\"demo1\">참여자 demo1</option>");
        options.append("<option value=\"user123\">참여자 user123</option>");
        return WebComponents.head("DealMate Review") + """
                <main class=\"phone\">
                    <div class=\"screen-content\">
                        <div class=\"top-bar\"><a class=\"back\" href=\"/room-detail?tab=detail\">‹</a><div class=\"bar-title\">리뷰 작성</div><div></div></div>
                        <header class=\"top-header\">%s</header>
                        <section>
                            <h2 class=\"page-title\">리뷰 작성</h2>
                            <p class=\"subtext\">거래가 끝난 뒤 리뷰를 작성할 대상을 먼저 선택해주세요.</p>
                            <label>리뷰 대상</label>
                            <select id=\"targetUserId\">%s</select>
                            <label>별점</label>
                            <select id=\"rating\">
                                <option value=\"5\">5점</option>
                                <option value=\"4\">4점</option>
                                <option value=\"3\">3점</option>
                                <option value=\"2\">2점</option>
                                <option value=\"1\">1점</option>
                            </select>
                            <label>리뷰 내용</label>
                            <textarea id=\"reviewContent\" placeholder=\"거래 후기를 입력하세요\"></textarea>
                            <button class=\"button primary\" style=\"margin-top:18px;\" onclick=\"submitReview()\">리뷰 등록</button>
                        </section>
                    </div>
                    %s
                </main>
                <div class=\"modal\" id=\"modal\"><div class=\"popup\"><h2 id=\"modal-title\">리뷰 등록</h2><p id=\"modal-message\">리뷰가 등록되었습니다.</p><button onclick=\"closePopup()\">확인</button></div></div>
                <script>
                    let nextUrl = '';
                    async function submitReview(){
                        const targetUserId = document.getElementById('targetUserId').value;
                        const rating = document.getElementById('rating').value;
                        const content = document.getElementById('reviewContent').value.trim();
                        if (!targetUserId || !rating || !content) { openPopup('입력 오류', '내용을 채워주세요.'); return; }
                        const body = new URLSearchParams({targetUserId, rating, content});
                        const response = await fetch('/api/review', {method:'POST', headers:{'Content-Type':'application/x-www-form-urlencoded'}, body});
                        const result = await response.json();
                        if(result.success){ nextUrl = result.next || '/my'; openPopup('리뷰 등록 완료', targetUserId + ' 사용자에게 ' + rating + '점 리뷰가 등록되었습니다.'); }
                        else { openPopup('입력 오류', result.message || '내용을 채워주세요.'); }
                    }
                    function openPopup(title, message){
                        document.getElementById('modal-title').textContent = title;
                        document.getElementById('modal-message').textContent = message;
                        document.getElementById('modal').classList.add('show');
                    }
                    function closePopup(){ document.getElementById('modal').classList.remove('show'); if(nextUrl) location.href = nextUrl; }
                </script>
                """.formatted(WebComponents.logo(), options, WebComponents.bottomNav("my")) + WebComponents.end();
    }
}
