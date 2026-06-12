package com.dealmate.web;

import com.dealmate.model.GroupPurchaseRoom;
import com.dealmate.model.User;
import java.util.List;

public class GroupDetailPage {
    public static String render(String initialTab, GroupPurchaseRoom room, User user, List<GroupPurchaseRoom> allRooms, List<GroupPurchaseRoom> relatedRooms) {
        if (room == null) {
            room = new GroupPurchaseRoom(1, "demo", "생수 2L 공동구매", "생수 2L 묶음 공동구매 / 공동 배송 예정", 4, 2, 32000, "모집 중");
        }

        String currentUserId = user == null ? "demo" : user.getUserId();
        boolean showSettlement = "detail".equalsIgnoreCase(initialTab) || "settlement".equalsIgnoreCase(initialTab);
        GroupPurchaseRoom settlementRoom = chooseSettlementRoom(room, relatedRooms);
        boolean hasRelatedRoom = settlementRoom != null;
        if (!hasRelatedRoom) {
            settlementRoom = room;
        }

        int recruitTotal = room.getExpectedPrice() <= 0 ? 32000 : room.getExpectedPrice();
        int recruitCurrentCount = Math.max(room.getCurrentParticipants(), 1);
        int recruitMaxCount = Math.max(room.getMaxParticipants(), 1);
        int recruitExpectedPer = recruitTotal / recruitMaxCount;
        boolean recruitIsHost = room.isHost(currentUserId);
        String joinButton = recruitIsHost
                ? "<button class=\"button outline\" onclick=\"openPopup('참여 불가', '모집 인원이 마감되었거나 참가할 수 없습니다.')\">내가 만든 공구입니다</button>"
                : "<button class=\"button primary\" onclick=\"joinRoom()\">공구 참여하기</button>";

        int total = settlementRoom.getExpectedPrice() <= 0 ? 32000 : settlementRoom.getExpectedPrice();
        int currentCount = Math.max(settlementRoom.getCurrentParticipants(), 1);
        int maxCount = Math.max(settlementRoom.getMaxParticipants(), 1);
        int actualPer = total / currentCount;
        int expectedPer = total / maxCount;
        boolean settlementIsHost = settlementRoom.isHost(currentUserId);
        String relationLabel = settlementIsHost ? "내가 호스트인 공구" : "내가 참여한 공구";
        String selector = buildRoomSelector(settlementRoom, relatedRooms, hasRelatedRoom);
        String roleSwitch = settlementIsHost
                ? "<div class=\"role-switch\"><button id=\"host-tab\" class=\"active\" onclick=\"showMode('host')\">호스트 정산 요청</button><button id=\"member-tab\" onclick=\"showMode('member')\">참여자 정산 확인</button></div>"
                : "<div class=\"role-switch\" style=\"grid-template-columns:1fr;\"><button id=\"member-tab\" class=\"active\" onclick=\"showMode('member')\">참여자 정산 확인</button></div>";
        String hostModeStyle = settlementIsHost ? "display:block;" : "display:none;";
        String memberModeStyle = settlementIsHost ? "display:none;" : "display:block;";

        return WebComponents.head("DealMate Group Detail") + """
                <main class=\"phone\">
                    <div class=\"screen-content\">
                        <div class=\"top-bar\"><a class=\"back\" href=\"/group-list\">‹</a><div class=\"bar-title\">공구 상세페이지</div><div></div></div>
                        <header class=\"top-header\">%s</header>
                        <section>
                            <div class=\"inline-tabs\">
                                <a id=\"recruit-tab\" class=\"pill-tab %s\" href=\"/group-list\">모집 중</a>
                                <a id=\"settlement-tab\" class=\"pill-tab %s\" href=\"/room-detail?tab=detail&roomId=%d\">공구 상세페이지</a>
                            </div>

                            <section id=\"recruit-view\" style=\"%s\">
                                <h2 class=\"page-title\">%s</h2>
                                <article class=\"card\">
                                    <h3>모집 현황</h3>
                                    <div class=\"info-row\"><span class=\"info-label\">호스트</span><span class=\"info-value\">%s</span></div>
                                    <div class=\"info-row\"><span class=\"info-label\">참여 인원</span><span class=\"info-value\" id=\"participant-count\">%s명</span></div>
                                    <div class=\"info-row\"><span class=\"info-label\">총 예상 금액</span><span class=\"info-value\">%s</span></div>
                                    <div class=\"info-row\"><span class=\"info-label\">1인당 예상 금액</span><span class=\"info-value\" style=\"color:#007AFF;\">%s</span></div>
                                    <div class=\"info-row\"><span class=\"info-label\">마감</span><span class=\"info-value\" style=\"color:#00A651;\">%s</span></div>
                                    <p style=\"margin-top:14px; border-top:1px solid #EEF1F4; padding-top:14px;\">%s</p>
                                </article>
                                <p class=\"subtext\">모집 중 탭을 누르면 전체 모집 중인 공구 목록으로 돌아갑니다.</p>
                                <p class=\"subtext\">최대 참여 인원은 호스트를 포함한 전체 인원입니다.</p>
                                %s
                            </section>

                            <section id=\"settlement-view\" style=\"%s\">
                                <h2 class=\"page-title\">정산 대상 공구</h2>
                                %s
                                <article class=\"mini-card\">
                                    <span class=\"price-badge\" style=\"margin-top:0; margin-bottom:10px;\">%s</span>
                                    <h3>%s</h3>
                                    <div class=\"info-row\"><span class=\"info-label\">호스트</span><span class=\"info-value\">%s</span></div>
                                    <div class=\"info-row\"><span class=\"info-label\">참여 인원</span><span class=\"info-value settlement-count-label\">%s명</span></div>
                                    <div class=\"info-row\"><span class=\"info-label\">총 예상 금액</span><span class=\"info-value settlement-total\">%s</span></div>
                                    <div class=\"info-row\"><span class=\"info-label\">1인당 예상 금액</span><span class=\"info-value\" style=\"color:#007AFF;\">%s</span></div>
                                    <div class=\"info-row\"><span class=\"info-label\">마감 상태</span><span class=\"info-value\" style=\"color:#00A651;\">%s</span></div>
                                    <p style=\"margin-top:12px; border-top:1px solid #EEF1F4; padding-top:12px;\">%s</p>
                                </article>

                                %s

                                <section id=\"host-mode\" style=\"%s\">
                                    <h2 class=\"page-title\" style=\"font-size:20px; margin-bottom:12px;\">호스트 정산 요청</h2>
                                    <article class=\"mini-card\">
                                        <div class=\"info-row\"><span class=\"info-label\">총 결제 금액</span><span class=\"info-value settlement-total\">%s</span></div>
                                        <div class=\"info-row\"><span class=\"info-label\">참여 인원</span><span class=\"info-value settlement-count-label\">%s명</span></div>
                                        <div class=\"info-row\"><span class=\"info-label\">1인당 정산 금액</span><span class=\"info-value settlement-per-person\" style=\"color:#007AFF;\">%s</span></div>
                                    </article>
                                    <label>결제 영수증 업로드</label>
                                    <div class=\"upload-box\" onclick=\"openPopup('영수증 업로드 제한', '시연 모드에서는 결제 영수증 업로드를 진행하지 않습니다. OCR 오류 시 직접 입력 칸을 사용해주세요.')\">
                                        <svg viewBox=\"0 0 24 24\"><path d=\"M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4\"></path><path d=\"M17 8 12 3 7 8\"></path><path d=\"M12 3v12\"></path></svg>
                                        결제 영수증을 업로드해주세요
                                    </div>
                                    <label>OCR 오류 시 직접 입력</label>
                                    <input type=\"number\" id=\"manual-amount\" placeholder=\"총 결제 금액 직접 입력\">
                                    <button class=\"button outline\" style=\"margin-top:10px;\" onclick=\"applyManualAmount()\">금액 변경</button>
                                    <button class=\"button primary\" style=\"margin-top:10px;\" onclick=\"requestSettlement()\">정산 요청 보내기</button>
                                </section>

                                <section id=\"member-mode\" style=\"%s\">
                                    <h2 class=\"page-title\" style=\"font-size:20px; margin-bottom:12px;\">참여자 정산 확인</h2>
                                    <label>호스트 업로드 영수증</label>
                                    <div class=\"upload-box\" style=\"color:#667085; border-color:#E2E6EA; background:#FFFFFF;\">영수증 이미지 미리보기</div>
                                    <article class=\"mini-card\" style=\"margin-top:12px;\">
                                        <div class=\"info-row\"><span class=\"info-label\">정산 대상</span><span class=\"info-value\">%s</span></div>
                                        <div class=\"info-row\"><span class=\"info-label\">총 결제 금액</span><span class=\"info-value settlement-total\">%s</span></div>
                                        <div class=\"info-row\"><span class=\"info-label\">참여 인원</span><span class=\"info-value settlement-count-label\">%s명</span></div>
                                        <div class=\"info-row\"><span class=\"info-label\">1인당 정산 금액</span><span class=\"info-value settlement-per-person\" style=\"color:#007AFF;\">%s</span></div>
                                    </article>
                                    <label>송금 인증 이미지 업로드</label>
                                    <div class=\"upload-box\" onclick=\"openPopup('송금 인증 이미지 업로드', '시연용 화면에서는 송금 인증 이미지 업로드가 완료된 것으로 처리합니다.')\">
                                        <svg viewBox=\"0 0 24 24\"><path d=\"M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4\"></path><path d=\"M17 8 12 3 7 8\"></path><path d=\"M12 3v12\"></path></svg>
                                        송금 인증 이미지를 업로드해주세요
                                    </div>
                                    <span class=\"price-badge\" style=\"margin-bottom:12px;\">정산 완료</span>
                                    <button class=\"button primary\" onclick=\"openPopup('송금 인증 완료', '송금 인증이 완료되었습니다.')\">송금 인증하기</button>
                                    <a class=\"button outline\" style=\"margin-top:10px;\" href=\"/review\">리뷰 작성하기</a>
                                </section>
                            </section>
                        </section>
                    </div>
                    %s
                </main>
                <div class=\"modal\" id=\"modal\"><div class=\"popup\"><h2 id=\"modal-title\">참여 완료</h2><p id=\"modal-message\">공구 참여가 완료되었습니다.</p><button onclick=\"closePopup()\">확인</button></div></div>
                <script>
                    const roomId = %d;
                    let participantCount = %d;
                    let currentTotalAmount = %d;
                    const isHostRoom = %s;
                    function showMode(mode){
                        const hostTab = document.getElementById('host-tab');
                        const memberTab = document.getElementById('member-tab');
                        const hostMode = document.getElementById('host-mode');
                        const memberMode = document.getElementById('member-mode');
                        const host = mode === 'host';
                        if(hostMode) hostMode.style.display = host ? 'block' : 'none';
                        if(memberMode) memberMode.style.display = host ? 'none' : 'block';
                        if(hostTab) hostTab.classList.toggle('active', host);
                        if(memberTab) memberTab.classList.toggle('active', !host);
                    }
                    async function joinRoom(){
                        if(isHostRoom){ openPopup('참여 실패', '모집 인원이 마감되었거나 참가할 수 없습니다.'); return; }
                        const body = new URLSearchParams({roomId:String(roomId)});
                        const response = await fetch('/api/join-room', {method:'POST', headers:{'Content-Type':'application/x-www-form-urlencoded'}, body});
                        const result = await response.json();
                        if(result.success){
                            if(result.count){
                                document.getElementById('participant-count').textContent = result.count + '명';
                                document.querySelectorAll('.settlement-count-label').forEach(el => el.textContent = result.count + '명');
                            }
                            if(result.currentParticipants){ participantCount = Number(result.currentParticipants); renderSettlementAmount(currentTotalAmount); }
                            openPopup('참여 완료', result.message);
                        } else { openPopup('참여 실패', result.message || '모집 인원이 마감되었거나 참가할 수 없습니다.'); }
                    }
                    function formatWon(value){ return Number(value).toLocaleString('ko-KR') + '원'; }
                    function renderSettlementAmount(total){
                        currentTotalAmount = total;
                        const perPerson = Math.floor(total / Math.max(participantCount, 1));
                        document.querySelectorAll('.settlement-total').forEach(el => el.textContent = formatWon(total));
                        document.querySelectorAll('.settlement-per-person').forEach(el => el.textContent = formatWon(perPerson));
                    }
                    function getManualAmount(){
                        const input = document.getElementById('manual-amount');
                        const raw = input ? input.value.trim() : '';
                        return raw ? Number(raw) : currentTotalAmount;
                    }
                    function applyManualAmount(){
                        const total = getManualAmount();
                        if (!Number.isFinite(total) || total <= 0 || participantCount <= 0) { openPopup('정산 실패', '정산 금액 계산에 실패했습니다.'); return false; }
                        renderSettlementAmount(total);
                        openPopup('금액 변경 완료', '입력한 총 결제 금액을 기준으로 정산 금액이 변경되었습니다.');
                        return true;
                    }
                    function openPopup(title, message){
                        document.getElementById('modal-title').textContent = title;
                        document.getElementById('modal-message').textContent = message;
                        document.getElementById('modal').classList.add('show');
                    }
                    function closePopup(){ document.getElementById('modal').classList.remove('show'); }
                    function requestSettlement(){
                        const total = getManualAmount();
                        if (!Number.isFinite(total) || total <= 0 || participantCount <= 0) { openPopup('정산 실패', '정산 금액 계산에 실패했습니다.'); return; }
                        renderSettlementAmount(total);
                        openPopup('정산 요청 완료', '현재 표시된 정산 금액으로 정산 요청이 전송되었습니다.');
                    }
                    window.addEventListener('load', function(){ renderSettlementAmount(currentTotalAmount); });
                </script>
                """.formatted(WebComponents.logo(), showSettlement ? "" : "active", showSettlement ? "active" : "", settlementRoom.getRoomId(),
                showSettlement ? "display:none;" : "display:block;",
                WebComponents.escape(room.getProductName()), WebComponents.escape(room.getHostId()), WebComponents.escape(room.participantText()), WebComponents.formatWon(recruitTotal), WebComponents.formatWon(recruitExpectedPer), WebComponents.escape(room.getStatus()), WebComponents.escape(room.getDescription()), joinButton,
                showSettlement ? "display:block;" : "display:none;",
                selector, WebComponents.escape(relationLabel), WebComponents.escape(settlementRoom.getProductName()), WebComponents.escape(settlementRoom.getHostId()), WebComponents.escape(settlementRoom.participantText()), WebComponents.formatWon(total), WebComponents.formatWon(expectedPer), WebComponents.escape(settlementRoom.getStatus()), WebComponents.escape(settlementRoom.getDescription()),
                roleSwitch,
                hostModeStyle, WebComponents.formatWon(total), WebComponents.escape(settlementRoom.participantText()), WebComponents.formatWon(actualPer),
                memberModeStyle, WebComponents.escape(settlementRoom.getProductName()), WebComponents.formatWon(total), WebComponents.escape(settlementRoom.participantText()), WebComponents.formatWon(actualPer),
                WebComponents.bottomNav("group"), room.getRoomId(), recruitCurrentCount, total, recruitIsHost ? "true" : "false") + WebComponents.end();
    }

    private static GroupPurchaseRoom chooseSettlementRoom(GroupPurchaseRoom requestedRoom, List<GroupPurchaseRoom> relatedRooms) {
        if (relatedRooms == null || relatedRooms.isEmpty()) {
            return requestedRoom;
        }
        if (requestedRoom != null) {
            for (GroupPurchaseRoom related : relatedRooms) {
                if (related.getRoomId() == requestedRoom.getRoomId()) {
                    return related;
                }
            }
        }
        return relatedRooms.get(0);
    }

    private static String buildRoomSelector(GroupPurchaseRoom currentRoom, List<GroupPurchaseRoom> relatedRooms, boolean hasRelatedRoom) {
        if (!hasRelatedRoom || relatedRooms == null || relatedRooms.isEmpty()) {
            return "<article class=\"mini-card\"><h3>관련된 공구가 없습니다.</h3><p class=\"subtext\" style=\"margin:0;\">모집 중 탭에서 공구에 참여하거나 공구 글을 작성하면 이곳에서 정산 대상을 선택할 수 있습니다.</p></article>";
        }
        int index = 0;
        for (int i = 0; i < relatedRooms.size(); i++) {
            if (relatedRooms.get(i).getRoomId() == currentRoom.getRoomId()) {
                index = i;
                break;
            }
        }
        int prev = relatedRooms.get((index - 1 + relatedRooms.size()) % relatedRooms.size()).getRoomId();
        int next = relatedRooms.get((index + 1) % relatedRooms.size()).getRoomId();
        String arrowStyle = "display:flex;align-items:center;justify-content:center;width:38px;height:38px;border-radius:999px;border:1px solid #BFDFFF;color:#007AFF;text-decoration:none;font-size:28px;font-weight:900;background:#FFFFFF;";
        return "<div style=\"display:grid;grid-template-columns:42px 1fr 42px;gap:8px;align-items:center;margin-bottom:12px;\">"
                + "<a style=\"" + arrowStyle + "\" href=\"/room-detail?tab=detail&roomId=" + prev + "\">‹</a>"
                + "<div style=\"text-align:center;font-size:13px;color:#667085;font-weight:800;line-height:1.45;\">정산 대상 선택<br><strong style=\"display:block;color:#111;font-size:16px;margin-top:3px;\">" + WebComponents.escape(currentRoom.getProductName()) + "</strong>"
                + "<span style=\"display:block;margin-top:3px;color:#999;font-size:12px;\">" + (index + 1) + " / " + relatedRooms.size() + "</span></div>"
                + "<a style=\"" + arrowStyle + "\" href=\"/room-detail?tab=detail&roomId=" + next + "\">›</a>"
                + "</div>";
    }
}
