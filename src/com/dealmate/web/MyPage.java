package com.dealmate.web;

import com.dealmate.model.User;
import java.util.ArrayList;

public class MyPage {
    public static String render(User user) {
        String userId = user == null ? "demo" : user.getUserId();
        String email = user == null ? "demo@dealmate.com" : user.getEmail();
        String neighborhood = user == null || user.getNeighborhood() == null || user.getNeighborhood().isBlank() ? "대구광역시 남구 대명동" : user.getNeighborhood();
        String shortNeighborhood = WebComponents.neighborhoodShort(neighborhood);
        double rating = user == null ? 4.8 : user.getAverageRating();
        String groups = buildGroupHtml(user == null ? new ArrayList<>() : user.getJoinedGroupSummaries());
        String reviews = buildReviewHtml(user == null ? new ArrayList<>() : user.getReceivedReviewSummaries());
        return WebComponents.head("DealMate My") + """
                <main class=\"phone\">
                    <header class=\"top-header\">%s</header>
                    <section>
                        <h2 class=\"page-title\">마이페이지</h2>
                        <article class=\"card\">
                            <h3>%s 이웃</h3>
                            <p>아이디: %s<br>이메일: %s<br>동네 인증: %s</p>
                            <span class=\"price-badge\" style=\"background:#FFF7E6; color:#D98300;\">평균 별점 %.1f / 5.0</span>
                        </article>
                        <a class=\"card\" href=\"/neighborhood\"><h3>동네 인증 정보</h3><p>%s · 눌러서 동네를 변경할 수 있습니다.</p></a>
                        <article class=\"card\"><h3>내가 참여한 공구</h3>%s</article>
                        <article class=\"card\"><h3>내 리뷰/별점</h3>%s</article>
                        <button class=\"button outline\" onclick=\"openLogoutModal()\">로그아웃</button>
                    </section>
                    %s
                </main>
                <div class=\"modal\" id=\"logout-modal\"><div class=\"popup\"><h2>로그아웃</h2><p>정말로 로그아웃 하시겠습니까?</p><div class=\"button-row\"><button class=\"button secondary\" onclick=\"closeLogoutModal()\">취소</button><button class=\"button primary\" onclick=\"location.href='/login'\">로그아웃</button></div></div></div>
                <script>function openLogoutModal(){document.getElementById('logout-modal').classList.add('show');} function closeLogoutModal(){document.getElementById('logout-modal').classList.remove('show');}</script>
                """.formatted(WebComponents.logo(), WebComponents.escape(shortNeighborhood), WebComponents.escape(userId), WebComponents.escape(email), WebComponents.escape(neighborhood), rating, WebComponents.escape(shortNeighborhood), groups, reviews, WebComponents.bottomNav("my")) + WebComponents.end();
    }

    private static String buildGroupHtml(ArrayList<String> groups) {
        if (groups == null || groups.isEmpty()) return "<p>참여한 공구가 없습니다.</p>";
        StringBuilder builder = new StringBuilder();
        for (String group : groups) {
            String[] parts = group.split("\\|", -1);
            String title = parts.length > 0 ? parts[0] : "공구 내역";
            String count = parts.length > 1 ? parts[1] : "-";
            String status = parts.length > 2 ? parts[2] : "확인";
            builder.append("<p style=\"margin-top:10px;\"><strong>").append(WebComponents.escape(title)).append("</strong><br>참여 인원: ").append(WebComponents.escape(count)).append(" · 상태: ").append(WebComponents.escape(status)).append("</p>");
        }
        return builder.toString();
    }

    private static String buildReviewHtml(ArrayList<String> reviews) {
        if (reviews == null || reviews.isEmpty()) return "<p>받은 리뷰가 없습니다.</p>";
        StringBuilder builder = new StringBuilder();
        for (String review : reviews) {
            String[] parts = review.split("\\|", -1);
            String content = parts.length > 0 ? parts[0] : "리뷰 내용";
            int rating = 0;
            if (parts.length > 1) {
                try { rating = Integer.parseInt(parts[1]); } catch (NumberFormatException ignored) { rating = 0; }
            }
            builder.append("<p style=\"margin-top:10px;\"><strong style=\"color:#FFB300;\">").append(stars(rating)).append("</strong><br>").append(WebComponents.escape(content)).append("</p>");
        }
        return builder.toString();
    }

    private static String stars(int rating) {
        if (rating <= 0) return "리뷰 대기";
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= 5; i++) result.append(i <= rating ? "★" : "☆");
        return result.toString();
    }
}
