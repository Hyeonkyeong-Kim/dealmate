package com.dealmate.web;

public class WebComponents {
    public static String escape(String value) {
        if (value == null) return "";
        return value.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }

    public static String neighborhoodShort(String neighborhood) {
        if (neighborhood == null || neighborhood.isBlank()) return "대명동";
        String[] parts = neighborhood.trim().split(" ");
        return parts[parts.length - 1];
    }

    public static String head(String title) {
        String html = """
                <!doctype html>
                <html lang=\"ko\">
                <head>
                    <meta charset=\"UTF-8\">
                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">
                    <title>__TITLE__</title>
                    <style>
                        * { box-sizing:border-box; }
                        html, body { margin:0; padding:0; width:100%; height:100vh; overflow:hidden; background:#F2F2F7; font-family:'Noto Sans KR', Arial, sans-serif; }
                        body { display:flex; justify-content:center; align-items:center; padding:24px 12px; }
                        .phone { position:relative; display:flex; flex-direction:column; width:390px; height:760px; max-width:390px; max-height:760px; overflow:hidden; background:#FFFFFF; padding:0; border-radius:28px; box-shadow:0 18px 45px rgba(0,0,0,.12); }
                        .screen-content { flex:1; min-height:0; height:auto; overflow-y:auto; overflow-x:hidden; padding:22px 20px 20px; scrollbar-width:none; -ms-overflow-style:none; }
                        .screen-content::-webkit-scrollbar { width:0; height:0; display:none; }
                        .logo { margin:0; font-size:28px; font-weight:900; letter-spacing:-1px; line-height:1; }
                        .logo.center { text-align:center; }
                        .logo-deal { color:#007AFF; }
                        .logo-mate { color:#34C759; }
                        .top-header { margin-bottom:28px; }
                        .badge { display:inline-flex; align-items:center; gap:5px; margin-top:12px; padding:7px 10px; border-radius:999px; background:#EFF7FF; color:#007AFF; font-size:12px; font-weight:800; text-decoration:none; }
                        .badge svg { width:15px; height:15px; fill:none; stroke:currentColor; stroke-width:2.2; stroke-linecap:round; stroke-linejoin:round; }
                        .page-title { margin:0 0 18px; font-size:24px; font-weight:900; letter-spacing:-.5px; }
                        .subtext { margin:0 0 16px; color:#777; font-size:13px; line-height:1.6; }
                        .card { display:block; background:#FFFFFF; border:1px solid #E8E8EE; border-radius:16px; padding:18px; margin-bottom:14px; color:#111; text-decoration:none; box-shadow:0 8px 20px rgba(0,0,0,.035); }
                        .card h3 { margin:0 0 9px; font-size:17px; font-weight:900; }
                        .card p { margin:0; color:#58606B; font-size:13px; line-height:1.55; }
                        .price-badge { display:inline-block; margin-top:14px; padding:7px 10px; border-radius:999px; background:#EAFBF2; color:#00A651; font-size:13px; font-weight:900; }
                        .pill-tab { display:inline-flex; align-items:center; justify-content:center; min-height:34px; padding:8px 12px; border-radius:999px; border:1px solid #E2E6EA; background:#FFFFFF; color:#667085; font-size:12px; font-weight:900; font-family:inherit; text-decoration:none; cursor:pointer; }
                        .pill-tab.active { background:#EFF7FF; border-color:#BFDFFF; color:#007AFF; }
                        .inline-tabs { display:flex; gap:8px; margin:0 0 14px; }
                        .top-bar { height:48px; display:grid; grid-template-columns:38px 1fr 38px; align-items:center; margin:-12px -8px 16px; }
                        .back { color:#111; text-decoration:none; font-size:26px; font-weight:700; text-align:center; }
                        .bar-title { text-align:center; font-size:16px; font-weight:900; }
                        label { display:block; font-size:13px; font-weight:800; color:#555; margin:14px 0 6px; }
                        input, textarea, select { width:100%; border:1px solid #E2E6EA; border-radius:12px; background:#F8F8FA; padding:13px 14px; font-size:14px; outline:none; font-family:inherit; }
                        textarea { min-height:130px; resize:none; line-height:1.55; }
                        input:focus, textarea:focus, select:focus { border-color:#007AFF; background:#FFFFFF; box-shadow:0 0 0 3px rgba(0,122,255,.12); }
                        .button { display:block; width:100%; border:0; border-radius:12px; padding:15px 12px; text-align:center; text-decoration:none; font-size:15px; font-weight:900; font-family:inherit; cursor:pointer; }
                        .button.primary { background:#007AFF; color:#FFFFFF; }
                        .button.secondary { background:#F2F8FF; color:#007AFF; }
                        .button.outline { background:#FFFFFF; color:#007AFF; border:1px solid #BFDFFF; }
                        .button-row { display:grid; grid-template-columns:1fr 1fr; gap:10px; margin-top:16px; }
                        .mini-card { background:#FFFFFF; border:1px solid #E8E8EE; border-radius:16px; padding:14px; margin-bottom:12px; }
                        .mini-card h3 { margin:0 0 8px; font-size:16px; font-weight:900; }
                        .info-row { display:flex; justify-content:space-between; gap:12px; padding:7px 0; border-bottom:1px solid #F0F1F4; font-size:13px; }
                        .info-row:last-child { border-bottom:0; }
                        .info-label { color:#667085; font-weight:800; }
                        .info-value { color:#111; font-weight:900; text-align:right; }
                        .role-switch { display:grid; grid-template-columns:1fr 1fr; gap:8px; margin:12px 0 14px; }
                        .role-switch button { border:1px solid #DCE7F5; border-radius:999px; background:#fff; color:#667085; padding:10px 8px; font-weight:900; font-family:inherit; }
                        .role-switch button.active { background:#EFF7FF; color:#007AFF; border-color:#BFDFFF; }
                        .upload-box { display:flex; flex-direction:column; align-items:center; justify-content:center; min-height:82px; border:1.5px dashed #B8D9FF; border-radius:14px; background:#FAFCFF; color:#007AFF; font-size:13px; font-weight:900; text-align:center; }
                        .upload-box svg { width:28px; height:28px; fill:none; stroke:currentColor; stroke-width:2; stroke-linecap:round; stroke-linejoin:round; margin-bottom:8px; }
                        .notice-card { display:block; background:rgba(255,149,0,.13); border:1px solid rgba(255,149,0,.38); border-radius:16px; padding:16px 18px; margin-bottom:14px; color:#111; text-decoration:none; box-shadow:0 8px 20px rgba(255,149,0,.06); }
                        .notice-card.active { background:rgba(255,149,0,.22); border-color:#FF9500; box-shadow:0 0 0 3px rgba(255,149,0,.12), 0 8px 20px rgba(255,149,0,.08); }
                        .notice-title { display:flex; justify-content:space-between; align-items:center; gap:8px; margin-bottom:8px; }
                        .notice-title h3 { margin:0; color:#C45C00; font-size:17px; font-weight:900; }
                        .notice-title span { flex-shrink:0; border-radius:999px; padding:5px 8px; background:#FFF; color:#C45C00; border:1px solid rgba(255,149,0,.38); font-size:11px; font-weight:900; }
                        .notice-guide { margin:10px 0 0; color:#9A4A00; font-size:12px; line-height:1.55; }
                        .review-compact { padding:14px 16px; margin-bottom:10px; }
                        .review-head { display:flex; justify-content:space-between; align-items:center; gap:10px; margin-bottom:8px; }
                        .review-head h3 { margin:0; font-size:16px; }
                        .review-head strong { font-size:14px; color:#111; white-space:nowrap; }
                        .compact-row { display:flex; justify-content:space-between; gap:10px; padding:5px 0; border-bottom:1px solid #F0F1F4; font-size:13px; }
                        .compact-row span { color:#667085; font-weight:800; }
                        .compact-row b { color:#111; font-weight:900; text-align:right; }
                        .review-text { margin:8px 0 0 !important; color:#58606B; font-size:12px !important; line-height:1.45 !important; }
                        .admin-filter-tabs { gap:8px; flex-wrap:nowrap; }
                        .admin-filter-tabs .pill-tab { flex:1; min-width:0; padding:8px 6px; white-space:nowrap; }
                        .modal { display:none; position:fixed; inset:0; background:rgba(0,0,0,.38); align-items:center; justify-content:center; padding:20px; z-index:20; }
                        .modal.show { display:flex; }
                        .popup { width:280px; background:#fff; border-radius:16px; padding:22px; text-align:center; box-shadow:0 8px 24px rgba(0,0,0,.16); }
                        .popup h2 { margin:0 0 8px; font-size:16px; }
                        .popup p { margin:0 0 18px; color:#666; font-size:14px; line-height:1.45; }
                        .popup button { width:100%; border:0; border-radius:8px; padding:11px; color:#fff; background:#007AFF; font-weight:800; }
                        .bottom-nav { height:66px; width:100%; background:#FFFFFF; border-top:1px solid #E8E8E8; display:grid; grid-template-columns:repeat(4,1fr); padding:8px 4px 10px; flex-shrink:0; }
                        .bottom-nav.admin-nav { grid-template-columns:repeat(3,1fr); }
                        .nav-item { display:flex; flex-direction:column; align-items:center; gap:4px; text-decoration:none; color:#777; font-size:11px; padding:5px 0; border-radius:12px; }
                        .nav-item svg { width:23px; height:23px; fill:none; stroke:currentColor; stroke-width:2; stroke-linecap:round; stroke-linejoin:round; }
                        .nav-item.active { color:#007AFF; font-weight:900; }
                    </style>
                </head>
                <body>
                """;
        return html.replace("__TITLE__", escape(title));
    }


    public static String logo() {
        return "<h1 class=\"logo\"><span class=\"logo-deal\">DEAL</span><span class=\"logo-mate\">MATE</span></h1>";
    }

    public static String bottomNav(String active) {
        return """
                        <nav class=\"bottom-nav\">
                            <a class=\"nav-item %s\" href=\"/home\"><svg viewBox=\"0 0 24 24\"><path d=\"M3 10.5 12 3l9 7.5\"></path><path d=\"M5 10v10h14V10\"></path><path d=\"M9 20v-6h6v6\"></path></svg><span>홈</span></a>
                            <a class=\"nav-item %s\" href=\"/write-post\"><svg viewBox=\"0 0 24 24\"><path d=\"M12 20h9\"></path><path d=\"M16.5 3.5a2.1 2.1 0 0 1 3 3L8 18l-4 1 1-4Z\"></path></svg><span>글쓰기</span></a>
                            <a class=\"nav-item %s\" href=\"/group-list\"><svg viewBox=\"0 0 24 24\"><path d=\"M17 21v-2a4 4 0 0 0-4-4H6a4 4 0 0 0-4 4v2\"></path><circle cx=\"9.5\" cy=\"7\" r=\"4\"></circle><path d=\"M22 21v-2a4 4 0 0 0-3-3.8\"></path><path d=\"M16.5 3.2a4 4 0 0 1 0 7.6\"></path></svg><span>공구</span></a>
                            <a class=\"nav-item %s\" href=\"/my\"><svg viewBox=\"0 0 24 24\"><path d=\"M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2\"></path><circle cx=\"12\" cy=\"7\" r=\"4\"></circle></svg><span>마이</span></a>
                        </nav>
                """.formatted(active.equals("home") ? "active" : "", active.equals("write") ? "active" : "", active.equals("group") ? "active" : "", active.equals("my") ? "active" : "");
    }

    public static String end() {
        return """
                <script>
                    (function(){
                        function wrapPhone(){
                            document.querySelectorAll('.phone').forEach(function(phone){
                                if (phone.querySelector(':scope > .screen-content')) return;
                                const nav = phone.querySelector(':scope > .bottom-nav');
                                const content = document.createElement('div');
                                content.className = 'screen-content';
                                Array.from(phone.childNodes).forEach(function(node){
                                    if (node !== nav) content.appendChild(node);
                                });
                                phone.insertBefore(content, nav || null);
                            });
                        }
                        if (document.readyState === 'loading') document.addEventListener('DOMContentLoaded', wrapPhone); else wrapPhone();
                    })();
                </script>
                </body></html>
                """;
    }

    public static String formatWon(int value) {
        return String.format("%,d원", value);
    }

}
