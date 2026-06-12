package com.dealmate.web;

import com.dealmate.model.Post;
import com.dealmate.model.User;
import java.util.List;

public class HomePage {
    public static String render(User user, List<Post> posts) {
        String neighborhood = WebComponents.neighborhoodShort(user == null ? "대구광역시 남구 대명동" : user.getNeighborhood());
        StringBuilder cards = new StringBuilder();
        for (Post post : posts) {
            cards.append("""
                    <a class=\"card\" href=\"/post-detail?id=%d\">
                        <h3>%s</h3>
                        <p>%s</p>
                        <span class=\"price-badge\">%s</span>
                    </a>
                    """.formatted(post.getPostId(), WebComponents.escape(post.getTitle()), WebComponents.escape(shortText(post.getContent())), WebComponents.escape(post.getBadgeText())));
        }
        return WebComponents.head("DealMate Home") + """
                <main class=\"phone\">
                    <header class=\"top-header\">
                        %s
                        <a class=\"badge\" href=\"/neighborhood\"><svg viewBox=\"0 0 24 24\"><path d=\"M21 10c0 7-9 12-9 12S3 17 3 10a9 9 0 1 1 18 0Z\"></path><circle cx=\"12\" cy=\"10\" r=\"3\"></circle></svg>%s</a>
                    </header>
                    <section>
                        <h2 class=\"page-title\">우리 동네 특가 정보</h2>
                        <p class=\"subtext\">특가 정보글은 하단의 글쓰기 탭에서 작성합니다.</p>
                        %s
                    </section>
                    %s
                </main>
                """.formatted(WebComponents.logo(), WebComponents.escape(neighborhood), cards, WebComponents.bottomNav("home")) + WebComponents.end();
    }

    private static String shortText(String text) {
        if (text == null) return "";
        return text.length() > 38 ? text.substring(0, 38) + "..." : text;
    }
}
