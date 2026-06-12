package com.dealmate.web;

import com.dealmate.model.User;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AdminPage {
    public static String render(User admin, List<User> users, String tab, String filter, String sort) {
        String safeTab = tab == null || tab.isBlank() ? "reviews" : tab;
        String safeFilter = filter == null || filter.isBlank() ? "all" : filter;
        String safeSort = sort == null || sort.isBlank() ? "low" : sort;

        ArrayList<User> normalUsers = new ArrayList<>();
        if (users != null) {
            for (User user : users) {
                if (user != null && !("admin".equals(user.getUserId()))) {
                    normalUsers.add(user);
                }
            }
        }
        normalUsers.sort(Comparator.comparingDouble(User::getAverageRating));

        String content;
        if ("users".equals(safeTab)) {
            content = renderUserManagement(normalUsers, safeFilter, safeSort);
        } else if ("my".equals(safeTab)) {
            content = renderAdminMy(admin);
        } else {
            content = renderReviewCheck(normalUsers, safeFilter, safeSort);
            safeTab = "reviews";
        }

        return WebComponents.head("DealMate Admin") + """
                <main class=\"phone\">
                    <div class=\"screen-content\">
                        <header class=\"top-header\">
                            %s
                            <span class=\"badge\">관리자 모드</span>
                        </header>
                        %s
                    </div>
                    %s
                </main>
                <div class=\"modal\" id=\"modal\"><div class=\"popup\"><h2 id=\"modalTitle\">알림</h2><p id=\"modalMessage\">처리되었습니다.</p><button onclick=\"closeModal()\">확인</button></div></div>
                <script>
                    function openPopup(title, message){ document.getElementById('modalTitle').textContent=title; document.getElementById('modalMessage').textContent=message; document.getElementById('modal').classList.add('show'); }
                    function closeModal(){ document.getElementById('modal').classList.remove('show'); }
                    async function applyAdminAction(targetUserId, actionType){
                        const response = await fetch('/api/admin-action', {
                            method:'POST',
                            headers:{'Content-Type':'application/x-www-form-urlencoded; charset=UTF-8'},
                            body:new URLSearchParams({targetUserId, actionType})
                        });
                        const result = await response.json();
                        openPopup(result.success ? '관리 조치 완료' : '관리 조치 실패', result.message || '처리되었습니다.');
                        if(result.success){ setTimeout(function(){ location.href='/admin?tab=users&filter=%s&sort=%s'; }, 700); }
                    }
                </script>
                """.formatted(WebComponents.logo(), content, adminBottomNav(safeTab), safeFilter, safeSort) + WebComponents.end();
    }

    private static String renderReviewCheck(List<User> users, String filter, String sort) {
        String safeFilter = filter == null || filter.isBlank() ? "all" : filter;
        String safeSort = sort == null || sort.isBlank() ? "low" : sort;
        if (!("need".equals(safeFilter))) {
            safeFilter = "all";
        }
        if (!"high".equals(safeSort)) {
            safeSort = "low";
        }

        ArrayList<User> sortedUsers = new ArrayList<>();
        for (User user : users) {
            if ("need".equals(safeFilter)) {
                if (user.getAverageRating() <= 3.0) sortedUsers.add(user);
            } else {
                sortedUsers.add(user);
            }
        }
        if ("high".equals(safeSort)) {
            sortedUsers.sort(Comparator.comparingDouble(User::getAverageRating).reversed());
        } else {
            sortedUsers.sort(Comparator.comparingDouble(User::getAverageRating));
        }

        int lowCount = 0;
        User lowest = null;
        for (User user : users) {
            if (user.getAverageRating() <= 3.0) lowCount++;
            if (lowest == null || user.getAverageRating() < lowest.getAverageRating()) lowest = user;
        }
        String lowestText = lowest == null ? "관리 대상 없음" : WebComponents.escape(lowest.getUserId()) + " · 평균 별점 " + String.format("%.1f", lowest.getAverageRating());
        String titleText = "need".equals(safeFilter) ? "제재 필요 사용자 목록" : "사용자 리뷰 목록";
        String sortUrlPrefix = "/admin?tab=reviews&filter=" + safeFilter + "&sort=";
        String noticeHref = "need".equals(safeFilter) ? "/admin?tab=reviews&filter=all&sort=" + safeSort : "/admin?tab=reviews&filter=need&sort=" + safeSort;
        String noticeClass = "need".equals(safeFilter) ? "notice-card active" : "notice-card";
        String noticeGuide = "need".equals(safeFilter) ? "현재 제재가 필요한 저평점 사용자만 모아보는 중입니다. 다시 누르면 전체 리뷰 목록으로 돌아갑니다." : "이 카드를 누르면 제재가 필요한 저평점 사용자만 모아볼 수 있습니다.";

        StringBuilder cards = new StringBuilder();
        for (User user : sortedUsers) {
            int reviewCount = reviewCount(user);
            cards.append("""
                    <article class=\"card review-compact\">
                        <div class=\"review-head\"><h3>%s</h3><strong>%.1f / 5.0</strong></div>
                        <div class=\"compact-row\"><span>리뷰 수</span><b>%d개</b></div>
                        <div class=\"compact-row\"><span>계정 상태</span><b>%s</b></div>
                        <p class=\"review-text\">대표 리뷰: %s</p>
                    </article>
                    """.formatted(
                    WebComponents.escape(user.getUserId()),
                    user.getAverageRating(),
                    reviewCount,
                    WebComponents.escape(statusText(user)),
                    WebComponents.escape(firstReview(user))
            ));
        }
        if (cards.length() == 0) {
            cards.append("<article class=\"card review-compact\"><h3>해당 조건의 사용자가 없습니다.</h3><p>다른 정렬 또는 필터를 선택해 확인해주세요.</p></article>");
        }
        return """
                <section>
                    <h2 class=\"page-title\">리뷰 확인</h2>
                    <p class=\"subtext\">사용자 리뷰 현황을 평균 별점 기준으로 확인합니다.</p>
                    <label style=\"margin-top:0;\">별점 정렬</label>
                    <select onchange=\"location.href=this.value\" style=\"margin-bottom:14px;\">
                        <option value=\"%slow\" %s>낮은 별점순</option>
                        <option value=\"%shigh\" %s>높은 별점순</option>
                    </select>
                    <a class=\"%s\" href=\"%s\">
                        <div class=\"notice-title\"><h3>저평점 사용자 현황</h3><span>%s</span></div>
                        <div class=\"info-row\"><span class=\"info-label\">관리 필요 사용자</span><span class=\"info-value\">%d명</span></div>
                        <div class=\"info-row\"><span class=\"info-label\">가장 낮은 별점</span><span class=\"info-value\">%s</span></div>
                        <p class=\"notice-guide\">%s</p>
                    </a>
                    <h3 style=\"margin:18px 0 10px; font-size:18px;\">%s</h3>
                    %s
                </section>
                """.formatted(
                sortUrlPrefix,
                "low".equals(safeSort) ? "selected" : "",
                sortUrlPrefix,
                "high".equals(safeSort) ? "selected" : "",
                noticeClass,
                noticeHref,
                "need".equals(safeFilter) ? "모아보기 중" : "확인 필요",
                lowCount,
                lowestText,
                noticeGuide,
                titleText,
                cards
        );
    }

    private static String renderUserManagement(List<User> users, String filter, String sort) {
        String safeFilter = filter == null || filter.isBlank() ? "all" : filter;
        String safeSort = sort == null || sort.isBlank() ? "low" : sort;
        if (!("restricted".equals(safeFilter) || "suspended".equals(safeFilter))) {
            safeFilter = "all";
        }
        if (!"high".equals(safeSort)) {
            safeSort = "low";
        }

        ArrayList<User> filtered = new ArrayList<>();
        for (User user : users) {
            String status = statusText(user);
            if ("restricted".equals(safeFilter)) {
                if (status.contains("24시간")) filtered.add(user);
            } else if ("suspended".equals(safeFilter)) {
                if (status.contains("정지")) filtered.add(user);
            } else {
                filtered.add(user);
            }
        }
        if ("high".equals(safeSort)) {
            filtered.sort(Comparator.comparingDouble(User::getAverageRating).reversed());
        } else {
            filtered.sort(Comparator.comparingDouble(User::getAverageRating));
        }

        StringBuilder cards = new StringBuilder();
        for (User user : filtered) {
            String status = statusText(user);
            String statusColor = "정상".equals(status) ? "#00A651" : (status.contains("정지") ? "#FF3B30" : "#FF9500");
            String actionButtons = actionButtonsForFilter(user.getUserId(), safeFilter);
            cards.append("""
                    <article class=\"card\">
                        <h3>%s</h3>
                        <div class=\"info-row\"><span class=\"info-label\">이메일</span><span class=\"info-value\">%s</span></div>
                        <div class=\"info-row\"><span class=\"info-label\">동네</span><span class=\"info-value\">%s</span></div>
                        <div class=\"info-row\"><span class=\"info-label\">평균 별점</span><span class=\"info-value\">%.1f / 5.0</span></div>
                        <div class=\"info-row\"><span class=\"info-label\">리뷰 수</span><span class=\"info-value\">%d개</span></div>
                        <div class=\"info-row\"><span class=\"info-label\">계정 상태</span><span class=\"info-value\" style=\"color:%s;\">%s</span></div>
                        <p class=\"subtext\" style=\"margin-top:10px;\">대표 리뷰: %s</p>
                        %s
                    </article>
                    """.formatted(
                    WebComponents.escape(user.getUserId()),
                    WebComponents.escape(user.getEmail()),
                    WebComponents.escape(user.getNeighborhood()),
                    user.getAverageRating(),
                    reviewCount(user),
                    statusColor,
                    WebComponents.escape(status),
                    WebComponents.escape(firstReview(user)),
                    actionButtons
            ));
        }
        if (cards.length() == 0) {
            cards.append("<article class=\"card\"><h3>해당 조건의 사용자가 없습니다.</h3><p>다른 필터를 선택해 확인해주세요.</p></article>");
        }
        String allChip = pill("전체", "/admin?tab=users&filter=all&sort=" + safeSort, "all".equals(safeFilter));
        String restrictedChip = pill("24시간 제한", "/admin?tab=users&filter=restricted&sort=" + safeSort, "restricted".equals(safeFilter));
        String suspendedChip = pill("계정 정지", "/admin?tab=users&filter=suspended&sort=" + safeSort, "suspended".equals(safeFilter));
        String sortUrlPrefix = "/admin?tab=users&filter=" + safeFilter + "&sort=";
        return """
                <section>
                    <h2 class=\"page-title\">유저 관리</h2>
                    <p class=\"subtext\">별점순 또는 제재 상태별로 사용자를 확인하고 관리 조치를 적용합니다.</p>
                    <label style=\"margin-top:0;\">별점 정렬</label>
                    <select onchange=\"location.href=this.value\" style=\"margin-bottom:10px;\">
                        <option value=\"%slow\" %s>낮은 별점순</option>
                        <option value=\"%shigh\" %s>높은 별점순</option>
                    </select>
                    <label style=\"margin-top:8px;\">제재 상태 필터</label>
                    <div class=\"inline-tabs admin-filter-tabs\" style=\"margin-bottom:14px;\">%s%s%s</div>
                    %s
                </section>
                """.formatted(
                sortUrlPrefix,
                "low".equals(safeSort) ? "selected" : "",
                sortUrlPrefix,
                "high".equals(safeSort) ? "selected" : "",
                allChip,
                restrictedChip,
                suspendedChip,
                cards
        );
    }

    private static String pill(String label, String href, boolean active) {
        return "<a class=\"pill-tab " + (active ? "active" : "") + "\" href=\"" + href + "\">" + label + "</a>";
    }

    private static String actionButtonsForFilter(String userId, String filter) {
        String safeUserId = WebComponents.escape(userId);
        if ("restricted".equals(filter)) {
            return """
                    <div class=\"button-row\"><button class=\"button primary\" onclick=\"applyAdminAction('%s','suspend')\">계정 정지</button><button class=\"button outline\" onclick=\"applyAdminAction('%s','release')\">해제하기</button></div>
                    """.formatted(safeUserId, safeUserId);
        }
        if ("suspended".equals(filter)) {
            return """
                    <div class=\"button-row\"><button class=\"button outline\" onclick=\"applyAdminAction('%s','restrict')\">24시간 제한</button><button class=\"button primary\" onclick=\"applyAdminAction('%s','release')\">해제하기</button></div>
                    """.formatted(safeUserId, safeUserId);
        }
        return """
                <div class=\"button-row\"><button class=\"button outline\" onclick=\"applyAdminAction('%s','restrict')\">24시간 제한</button><button class=\"button primary\" onclick=\"applyAdminAction('%s','suspend')\">계정 정지</button></div>
                """.formatted(safeUserId, safeUserId);
    }

    private static String renderAdminMy(User admin) {
        return """
                <section>
                    <h2 class=\"page-title\">마이</h2>
                    <article class=\"card\">
                        <h3>관리자 계정 정보</h3>
                        <p>아이디: %s<br>이메일: %s<br>권한: Administrator</p>
                    </article>
                    <a class=\"button outline\" href=\"/login\">로그아웃</a>
                </section>
                """.formatted(WebComponents.escape(admin.getUserId()), WebComponents.escape(admin.getEmail()));
    }

    private static String firstReview(User user) {
        if (user.getReceivedReviewSummaries().isEmpty()) return "리뷰 내역 없음";
        String[] parts = user.getReceivedReviewSummaries().get(0).split("\\|", -1);
        return parts.length > 0 ? parts[0] : "리뷰 내역 없음";
    }

    private static int reviewCount(User user) {
        int count = 0;
        for (String review : user.getReceivedReviewSummaries()) {
            if (review == null || review.isBlank()) continue;
            String[] parts = review.split("\\|", -1);
            if (parts.length > 1) {
                try {
                    int rating = Integer.parseInt(parts[1]);
                    if (rating > 0) count++;
                } catch (NumberFormatException ignored) {}
            }
        }
        return count;
    }

    private static String statusText(User user) {
        String status = user.getAccountStatus();
        if (status == null || status.isBlank()) return "정상";
        if ("24시간 활동 제한".equals(status) || "계정 정지".equals(status) || "정상".equals(status)) return status;
        return "정상";
    }

    private static String adminBottomNav(String active) {
        return """
                <nav class=\"bottom-nav admin-nav\">
                    <a class=\"nav-item %s\" href=\"/admin?tab=reviews\"><svg viewBox=\"0 0 24 24\"><path d=\"M12 17.3 18.2 21l-1.7-7.1L22 9.2l-7.2-.6L12 2 9.2 8.6 2 9.2l5.5 4.7L5.8 21z\"></path></svg><span>리뷰확인</span></a>
                    <a class=\"nav-item %s\" href=\"/admin?tab=users&filter=all&sort=low\"><svg viewBox=\"0 0 24 24\"><path d=\"M17 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2\"></path><circle cx=\"9.5\" cy=\"7\" r=\"4\"></circle><path d=\"M22 21v-2a4 4 0 0 0-3-3.8\"></path><path d=\"M16.5 3.2a4 4 0 0 1 0 7.6\"></path></svg><span>유저관리</span></a>
                    <a class=\"nav-item %s\" href=\"/admin?tab=my\"><svg viewBox=\"0 0 24 24\"><path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path><circle cx=\"12\" cy=\"7\" r=\"4\"></circle></svg><span>마이</span></a>
                </nav>
                """.formatted(active.equals("reviews") ? "active" : "", active.equals("users") ? "active" : "", active.equals("my") ? "active" : "");
    }
}
