package com.dealmate.web;

import com.dealmate.model.GroupPurchaseRoom;
import com.dealmate.model.User;
import java.util.List;

public class GroupListPage {
    public static String render(List<GroupPurchaseRoom> rooms, User currentUser) {
        String currentUserId = currentUser == null ? "" : currentUser.getUserId();
        StringBuilder cards = new StringBuilder();
        if (rooms == null || rooms.isEmpty()) {
            cards.append("<article class=\"card\"><h3>등록된 공구가 없습니다.</h3><p>공구 글 작성 버튼으로 모집글을 등록해보세요.</p></article>");
        } else {
            for (GroupPurchaseRoom room : rooms) {
                boolean myRoom = room.isHost(currentUserId);
                String actionButton = myRoom
                        ? "<button class=\"button outline\" style=\"cursor:not-allowed;\" onclick=\"openPopup('참여 불가', '모집 인원이 마감되었거나 참가할 수 없습니다.')\">내가 만든 공구입니다</button>"
                        : "<a class=\"button primary\" href=\"/room-detail?tab=recruit&roomId=" + room.getRoomId() + "\">참여하기</a>";
                cards.append("""
                        <article class=\"card\">
                            <h3>%s</h3>
                            <p>%s</p>
                            <div style=\"display:grid; gap:7px; color:#667085; font-size:13px; margin-top:14px;\">
                                <div style=\"display:flex; justify-content:space-between;\"><span>참여 인원</span><strong style=\"color:#111;\">%s명</strong></div>
                                <div style=\"display:flex; justify-content:space-between;\"><span>총 예상 금액</span><strong style=\"color:#111;\">%s</strong></div>
                                <div style=\"display:flex; justify-content:space-between;\"><span>1인당 예상 금액</span><strong style=\"color:#007AFF;\">%s</strong></div>
                            </div>
                            <p class=\"subtext\" style=\"margin-top:10px; margin-bottom:0;\">최대 참여 인원은 호스트를 포함한 전체 인원입니다.</p>
                            <div class=\"button-row\"><a class=\"button secondary\" href=\"/room-detail?tab=detail&roomId=%d\">공구 상세페이지</a>%s</div>
                        </article>
                        """.formatted(WebComponents.escape(room.getProductName()), WebComponents.escape(room.getDescription()), WebComponents.escape(room.participantText()), WebComponents.formatWon(room.getExpectedPrice()), WebComponents.formatWon(room.getExpectedPerPersonAmount()), room.getRoomId(), actionButton));
            }
        }
        return WebComponents.head("DealMate Group List") + """
                <main class=\"phone\">
                    <header class=\"top-header\">%s</header>
                    <section>
                        <div style=\"display:flex; align-items:center; justify-content:space-between; gap:12px; margin-bottom:14px;\">
                            <h2 class=\"page-title\" style=\"margin:0;\">공동구매</h2>
                            <a class=\"button primary\" style=\"width:auto; padding:11px 14px; font-size:13px;\" href=\"/create-room\">공구 글 작성</a>
                        </div>
                        <div style=\"display:flex; gap:8px; margin:0 0 14px;\">
                            <a class=\"badge\" style=\"margin:0;\" href=\"/group-list\">모집 중</a>
                            <a class=\"badge\" style=\"margin:0; background:#FFFFFF; color:#666; border:1px solid #E2E6EA;\" href=\"/room-detail?tab=detail&roomId=1\">공구 상세페이지</a>
                        </div>
                        <p class=\"subtext\">모집 중인 전체 공구를 확인하고, 공구 상세페이지 탭에서 내가 관련된 공구의 정산 대상을 선택합니다.</p>
                        %s
                    </section>
                    %s
                </main>
                <div class=\"modal\" id=\"modal\"><div class=\"popup\"><h2 id=\"modalTitle\">참여 불가</h2><p id=\"modalMessage\">모집 인원이 마감되었거나 참가할 수 없습니다.</p><button onclick=\"closeModal()\">확인</button></div></div>
                <script>
                    function openPopup(title, message){ document.getElementById('modalTitle').textContent=title; document.getElementById('modalMessage').textContent=message; document.getElementById('modal').classList.add('show'); }
                    function closeModal(){ document.getElementById('modal').classList.remove('show'); }
                </script>
                """.formatted(WebComponents.logo(), cards, WebComponents.bottomNav("group")) + WebComponents.end();
    }
}
