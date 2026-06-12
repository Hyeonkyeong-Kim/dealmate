package com.dealmate.web;

import com.dealmate.model.Post;
import com.dealmate.model.User;

public class PostDetailPage {
    public static String render(User user, Post post) {
        if (post == null) {
            return WebComponents.head("DealMate Post") + """
                    <main class=\"phone\">
                        <div class=\"top-bar\"><a class=\"back\" href=\"/home\">‹</a><div class=\"bar-title\">특가 정보</div><div></div></div>
                        <section class=\"card\"><h3>게시글을 찾을 수 없습니다.</h3><p>홈 화면에서 다시 선택해주세요.</p></section>
                        %s
                    </main>
                    """.formatted(WebComponents.bottomNav("home")) + WebComponents.end();
        }
        return WebComponents.head("DealMate Post Detail") + """
                <main class=\"phone\">
                    <div class=\"top-bar\"><a class=\"back\" href=\"/home\">‹</a><div class=\"bar-title\">특가 정보 상세</div><div></div></div>
                    <header class=\"top-header\">%s<a class=\"badge\" href=\"/neighborhood\">%s</a></header>
                    <section class=\"card\">
                        <h3>%s</h3>
                        <p style=\"margin-bottom:14px;\">작성자: %s</p>
                        <p>%s</p>
                        <span class=\"price-badge\">%s</span>
                        %s
                    </section>
                    <a class=\"button primary\" href=\"/group-list\">관련 공구 보러가기</a>
                    %s
                </main>
                """.formatted(
                        WebComponents.logo(),
                        WebComponents.escape(WebComponents.neighborhoodShort(user == null ? "" : user.getNeighborhood())),
                        WebComponents.escape(post.getTitle()),
                        WebComponents.escape(post.getWriterId()),
                        WebComponents.escape(post.getContent()),
                        WebComponents.escape(post.getBadgeText()),
                        post.isImageAttached() ? imagePreviewNotice() : "",
                        WebComponents.bottomNav("home")
                ) + WebComponents.end();
    }
    private static String imagePreviewNotice() {
        return """
                <div style=\"margin-top:16px;padding:26px 14px;border:1px dashed #007AFF;border-radius:14px;background:#F3F8FF;text-align:center;color:#007AFF;font-weight:800;\">
                    [이미지 미리보기]
                    <p style=\"margin:8px 0 0;font-size:12px;line-height:1.5;color:#667085;font-weight:600;\">시연 중에는 실제 이미지 미리보기가 제공되지 않습니다.</p>
                </div>
                """;
    }

}
