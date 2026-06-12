package com.dealmate.web;

import com.dealmate.model.User;

public class NeighborhoodPage {
    public static String render(User user, boolean changeMode) {
        String currentLocation = user == null || user.getNeighborhood() == null || user.getNeighborhood().isBlank()
                ? "대구광역시 남구 대명동"
                : user.getNeighborhood();
        String backUrl = changeMode ? "/my" : "/register";
        String description = changeMode
                ? "마이페이지에서 동네 인증 정보를 변경하는 화면입니다."
                : "회원가입 후 우리 동네 기반 정보를 보기 위해 동네 인증을 진행해주세요.";

        return WebComponents.head("DealMate Neighborhood Certification") + """
                <main class=\"phone\">
                    <div class=\"screen-content\">
                        <div class=\"top-bar\"><a class=\"back\" href=\"%s\">‹</a><div class=\"bar-title\">동네 인증</div><div></div></div>
                        <header class=\"top-header\">%s</header>
                        <section style=\"text-align:center;\">
                            <div style=\"width:76px;height:76px;border-radius:26px;background:#EAF3FF;color:#007AFF;display:flex;align-items:center;justify-content:center;font-size:42px;margin:4px auto 16px;\">⌖</div>
                            <h2 class=\"page-title\" id=\"main-location-text\" style=\"font-size:22px;\">현재 위치를 확인해주세요</h2>
                            <p class=\"subtext\" id=\"description-text\" style=\"text-align:center;\">%s</p>
                            <div class=\"card\" style=\"background:linear-gradient(135deg,#EAF3FF,#ECFFF3); text-align:center; margin-top:18px;\">
                                <h3 style=\"color:#007AFF;\">지도 미리보기 영역</h3>
                                <p id=\"map-help\">현재 위치 확인 또는 직접 동네 선택을 눌러주세요.</p>
                            </div>
                            <div class=\"mini-card\" id=\"confirm-card\" style=\"display:none; text-align:left; border-color:#D6E9FF; background:#F5FAFF;\">
                                <p style=\"margin:0 0 6px;color:#007AFF;font-size:12px;font-weight:900;\">확인된 위치</p>
                                <p id=\"confirm-location\" style=\"margin:0;font-size:17px;font-weight:900;color:#222;\">%s</p>
                                <p style=\"margin:8px 0 0;color:#777;font-size:12px;line-height:1.5;\">이 위치가 맞으면 아래의 “이 위치로 인증하기”를 눌러주세요.</p>
                            </div>
                            <div id=\"manual-box\" style=\"display:none; text-align:left;\">
                                <label>직접 동네 입력</label>
                                <input id=\"location-input\" value=\"%s\" placeholder=\"동네를 입력하세요\">
                            </div>
                            <div style=\"display:flex; flex-direction:column; gap:10px; margin-top:18px;\">
                                <button class=\"button primary\" id=\"check-current\">현재 위치 확인하기</button>
                                <button class=\"button outline\" id=\"show-manual\">직접 동네 선택</button>
                                <button class=\"button primary\" id=\"confirm-certification\" style=\"display:none;\">이 위치로 인증하기</button>
                                <button class=\"button secondary\" id=\"reset-location\" style=\"display:none;\">다시 확인하기</button>
                            </div>
                        </section>
                    </div>
                </main>
                <div class=\"modal\" id=\"message-modal\"><div class=\"popup\"><h2 id=\"modal-title\">동네 인증</h2><p id=\"modal-message\">지역 인증에 실패했습니다. 다시 시도해주세요.</p><button id=\"modal-confirm\">확인</button></div></div>
                <script>
                    const sampleLocation = '%s';
                    const defaultDescription = '%s';
                    const locationInput = document.getElementById('location-input');
                    const mainLocationText = document.getElementById('main-location-text');
                    const descriptionText = document.getElementById('description-text');
                    const mapHelp = document.getElementById('map-help');
                    const confirmCard = document.getElementById('confirm-card');
                    const confirmLocation = document.getElementById('confirm-location');
                    const manualBox = document.getElementById('manual-box');
                    const checkCurrentBtn = document.getElementById('check-current');
                    const showManualBtn = document.getElementById('show-manual');
                    const confirmBtn = document.getElementById('confirm-certification');
                    const resetBtn = document.getElementById('reset-location');
                    const modal = document.getElementById('message-modal');
                    const modalTitle = document.getElementById('modal-title');
                    const modalMessage = document.getElementById('modal-message');
                    const modalConfirm = document.getElementById('modal-confirm');
                    let selectedLocation = '';
                    let nextUrl = null;

                    checkCurrentBtn.addEventListener('click', function () { selectedLocation = sampleLocation; showConfirmStep(selectedLocation, false); });
                    showManualBtn.addEventListener('click', function () { selectedLocation = locationInput.value.trim(); showConfirmStep(selectedLocation, true); });
                    locationInput.addEventListener('input', function () { selectedLocation = locationInput.value.trim(); confirmLocation.textContent = selectedLocation || '동네를 입력해주세요'; });
                    confirmBtn.addEventListener('click', function () { certify(selectedLocation); });
                    resetBtn.addEventListener('click', function () { resetStep(); });

                    function showConfirmStep(location, manual) {
                        if (manual) manualBox.style.display = 'block';
                        selectedLocation = location || '';
                        if (!selectedLocation) { showModal('인증 실패', '지역 인증에 실패했습니다. 다시 시도해주세요.', null); return; }
                        mainLocationText.textContent = '위치가 맞는지 확인해주세요';
                        descriptionText.textContent = '아래에 표시된 동네가 실제 활동 지역과 맞는지 확인한 뒤 인증을 완료해주세요.';
                        mapHelp.textContent = '확인된 위치를 사용자에게 먼저 보여주는 단계입니다.';
                        confirmLocation.textContent = selectedLocation;
                        confirmCard.style.display = 'block';
                        checkCurrentBtn.style.display = 'none';
                        showManualBtn.style.display = 'none';
                        confirmBtn.style.display = 'block';
                        resetBtn.style.display = 'block';
                    }
                    function resetStep() {
                        selectedLocation = '';
                        mainLocationText.textContent = '현재 위치를 확인해주세요';
                        descriptionText.textContent = defaultDescription;
                        mapHelp.textContent = '현재 위치 확인 또는 직접 동네 선택을 눌러주세요.';
                        confirmCard.style.display = 'none';
                        manualBox.style.display = 'none';
                        checkCurrentBtn.style.display = 'block';
                        showManualBtn.style.display = 'block';
                        confirmBtn.style.display = 'none';
                        resetBtn.style.display = 'none';
                    }
                    async function certify(location) {
                        if (!location || location.length < 2) { showModal('인증 실패', '지역 인증에 실패했습니다. 다시 시도해주세요.', null); return; }
                        const response = await fetch('/api/neighborhood', { method: 'POST', headers: { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }, body: new URLSearchParams({ location }) });
                        const result = await response.json();
                        if (result.success) { showModal('동네 인증 완료', result.message || '동네 인증이 완료되었습니다.', result.next || '/login'); }
                        else { showModal('인증 실패', result.message || '지역 인증에 실패했습니다. 다시 시도해주세요.', null); }
                    }
                    function showModal(title, message, next) { modalTitle.textContent = title; modalMessage.textContent = message; nextUrl = next; modal.classList.add('show'); }
                    modalConfirm.addEventListener('click', function () { modal.classList.remove('show'); if (nextUrl) location.href = nextUrl; });
                </script>
                """.formatted(
                WebComponents.escape(backUrl),
                WebComponents.logo(),
                WebComponents.escape(description),
                WebComponents.escape(currentLocation),
                WebComponents.escape(currentLocation),
                escapeJs(currentLocation),
                escapeJs(description)
        ) + WebComponents.end();
    }

    private static String escapeJs(String value) {
        if (value == null) return "";
        return value.replace("\\", "\\\\").replace("'", "\\'");
    }
}
