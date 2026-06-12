package com.dealmate.web;

public class SettlementPage {
    public static String render() {
        return WebComponents.head("DealMate Settlement") + """
                <main class=\"phone\">
                    <div class=\"top-bar\"><a class=\"back\" href=\"/room-detail\">‹</a><div class=\"bar-title\">정산 상세</div><div></div></div>
                    <header class=\"top-header\">%s</header>
                    <section>
                        <h2 class=\"page-title\">정산 상세는 공구 상세페이지 안에서 확인합니다</h2>
                        <p class=\"subtext\">공구 상세페이지 상단의 공구 상세페이지 탭을 누르면 호스트 정산 요청과 참여자 정산 확인 화면이 바로 나옵니다.</p>
                        <a class=\"button primary\" href=\"/room-detail\">공구 상세페이지로 이동</a>
                    </section>
                    %s
                </main>
                """.formatted(WebComponents.logo(), WebComponents.bottomNav("group")) + WebComponents.end();
    }
}
