package com.dealmate.controller;

import com.dealmate.model.*;
import com.dealmate.service.Database;
import com.dealmate.service.Login;
import com.dealmate.service.Registration;
import com.dealmate.service.NeighborhoodCertification;
import com.dealmate.service.ScreenController;
import com.dealmate.web.HomePage;
import com.dealmate.web.LoginPage;
import com.dealmate.web.RegisterPage;
import com.dealmate.web.NeighborhoodPage;
import com.dealmate.web.MyPage;
import com.dealmate.web.GroupListPage;
import com.dealmate.web.GroupDetailPage;
import com.dealmate.web.CreateRoomPage;
import com.dealmate.web.PostDetailPage;
import com.dealmate.web.WritePostPage;
import com.dealmate.web.SettlementPage;
import com.dealmate.web.ReviewPage;
import com.dealmate.web.AdminPage;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class PageController {
    private final Database database;
    private final Login login;
    private final Registration registration;
    private final NeighborhoodCertification neighborhoodCertification;
    private final ScreenController screenController;
    private String pendingCertificationUserId = "";
    private String currentUserId = "demo";

    public PageController(Database database, Login login, Registration registration, NeighborhoodCertification neighborhoodCertification, ScreenController screenController) {
        this.database = database;
        this.login = login;
        this.registration = registration;
        this.neighborhoodCertification = neighborhoodCertification;
        this.screenController = screenController;
    }

    public void handleRoot(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        exchange.getResponseHeaders().add("Location", "/login");
        exchange.sendResponseHeaders(302, -1);
        exchange.close();
    }

    public void handleLoginPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Login");
        sendHtml(exchange, 200, LoginPage.render());
    }

    public void handleRegisterPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Register");
        sendHtml(exchange, 200, RegisterPage.render());
    }

    public void handleNeighborhoodPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Neighborhood_Auth");
        boolean changeMode = pendingCertificationUserId.isBlank() && !currentUserId.isBlank();
        sendHtml(exchange, 200, NeighborhoodPage.render(getCurrentUser(), changeMode));
    }

    public void handleHomePage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Home");
        sendHtml(exchange, 200, HomePage.render(getCurrentUser(), database.getPostTable()));
    }

    public void handleWritePostPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Write_Post");
        sendHtml(exchange, 200, WritePostPage.render());
    }

    public void handleGroupListPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Group_List");
        sendHtml(exchange, 200, GroupListPage.render(database.getRoomTable(), getCurrentUser()));
    }

    public void handleCreateRoomPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Group_Room_Creation");
        sendHtml(exchange, 200, CreateRoomPage.render());
    }

    public void handleRoomDetailPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Group_Detail");
        String query = exchange.getRequestURI().getRawQuery();
        Map<String, String> params = parseForm(query == null ? "" : query);
        String tab = params.getOrDefault("tab", "recruit");
        int roomId = 1;
        try { roomId = Integer.parseInt(params.getOrDefault("roomId", "1")); } catch (NumberFormatException ignored) { roomId = 1; }
        GroupPurchaseRoom room = database.findRoom(roomId);
        if (room == null) { room = database.findRoom(1); }
        User currentUser = getCurrentUser();
        List<GroupPurchaseRoom> relatedRooms = database.getRelatedRooms(currentUser.getUserId());
        if (("detail".equalsIgnoreCase(tab) || "settlement".equalsIgnoreCase(tab)) && !relatedRooms.isEmpty()) {
            boolean requestedRoomIsRelated = false;
            if (room != null) {
                for (GroupPurchaseRoom related : relatedRooms) {
                    if (related.getRoomId() == room.getRoomId()) {
                        requestedRoomIsRelated = true;
                        break;
                    }
                }
            }
            if (!requestedRoomIsRelated) {
                room = relatedRooms.get(0);
            }
        }
        sendHtml(exchange, 200, GroupDetailPage.render(tab, room, currentUser, database.getRoomTable(), relatedRooms));
    }

    public void handlePostDetailPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Post_Detail");
        String query = exchange.getRequestURI().getRawQuery();
        Map<String, String> params = parseForm(query == null ? "" : query);
        int postId = 1;
        try {
            postId = Integer.parseInt(params.getOrDefault("id", "1"));
        } catch (NumberFormatException ignored) {
            postId = 1;
        }
        sendHtml(exchange, 200, PostDetailPage.render(getCurrentUser(), database.findPost(postId)));
    }

    public void handleMyPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_My");
        sendHtml(exchange, 200, MyPage.render(getCurrentUser()));
    }

    public void handleSettlementPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Settlement");
        sendHtml(exchange, 200, SettlementPage.render());
    }

    public void handleReviewPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Review");
        sendHtml(exchange, 200, ReviewPage.render(getCurrentUser(), database.getRoomTable()));
    }

    public void handleAdminPage(HttpExchange exchange) throws IOException {
        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendText(exchange, 405, "Method Not Allowed");
            return;
        }
        screenController.changeScreen("DEALMATE_Admin");
        String query = exchange.getRequestURI().getRawQuery();
        Map<String, String> params = parseForm(query == null ? "" : query);
        String tab = params.getOrDefault("tab", "reviews");
        String filter = params.getOrDefault("filter", "all");
        String sort = params.getOrDefault("sort", "low");
        sendHtml(exchange, 200, AdminPage.render(getCurrentUser(), database.getUserTable(), tab, filter, sort));
    }

    public void handleLoginApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }

        String body = readBody(exchange.getRequestBody());
        Map<String, String> form = parseForm(body);
        String id = form.getOrDefault("id", "");
        String pw = form.getOrDefault("pw", "");

        boolean success = login.login(id, pw);
        if (success) {
            User foundUser = database.findUser(id);
            if (foundUser != null) {
                currentUserId = foundUser.getUserId();
            }
            pendingCertificationUserId = "";
            boolean admin = login.isAdministrator(id);
            String next = admin ? "/admin?tab=reviews" : "/home";
            sendJson(exchange, 200, "{\"success\":true,\"admin\":" + admin + ",\"next\":\"" + next + "\"}");
        } else {
            String message = screenController.showPopup("아이디 또는 비밀번호가 틀렸습니다.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
        }
    }

    public void handleRegisterApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }

        String body = readBody(exchange.getRequestBody());
        Map<String, String> form = parseForm(body);
        String userId = form.getOrDefault("userId", "").trim();
        String password = form.getOrDefault("password", "").trim();
        String passwordConfirm = form.getOrDefault("passwordConfirm", "").trim();
        String email = form.getOrDefault("email", "").trim();

        User user = new User(userId, password, email);

        if (passwordConfirm.isEmpty()) {
            String message = screenController.showPopup("미기입된 내용이 있습니다.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }

        if (!password.equals(passwordConfirm)) {
            String message = screenController.showPopup("비밀번호가 일치하지 않습니다.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }

        if (!isValidEmail(email)) {
            String message = screenController.showPopup("올바른 이메일 형식이 아닙니다.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }

        boolean success = registration.register(user);
        if (success) {
            pendingCertificationUserId = userId;
            sendJson(exchange, 200, "{\"success\":true,\"message\":\"회원가입 되었습니다.\",\"next\":\"/neighborhood\"}");
        } else {
            String errorMessage = registration.getLastErrorMessage().isBlank()
                    ? "미기입된 내용이 있습니다."
                    : registration.getLastErrorMessage();
            String message = screenController.showPopup(errorMessage);
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
        }
    }

    public void handleNeighborhoodApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }

        String body = readBody(exchange.getRequestBody());
        Map<String, String> form = parseForm(body);
        String location = form.getOrDefault("location", "").trim();

        boolean success = neighborhoodCertification.requestCertification(location);
        if (success) {
            String userId = pendingCertificationUserId.isBlank() ? currentUserId : pendingCertificationUserId;
            neighborhoodCertification.saveNeighborhood(userId, location);
            database.updateNeighborhood(userId, location);
            boolean afterRegister = !pendingCertificationUserId.isBlank();
            if (afterRegister) {
                pendingCertificationUserId = "";
                screenController.changeScreen("DEALMATE_Login");
                sendJson(exchange, 200, "{\"success\":true,\"message\":\"동네 인증이 완료되었습니다.\",\"next\":\"/login\"}");
            } else {
                screenController.changeScreen("DEALMATE_My");
                sendJson(exchange, 200, "{\"success\":true,\"message\":\"동네 인증이 완료되었습니다.\",\"next\":\"/my\"}");
            }
        } else {
            String message = screenController.showPopup("지역 인증에 실패했습니다. 다시 시도해주세요.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
        }
    }

    public void handleWritePostApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }
        String body = readBody(exchange.getRequestBody());
        Map<String, String> form = parseForm(body);
        String title = form.getOrDefault("title", "").trim();
        String content = form.getOrDefault("content", "").trim();
        boolean hasImage = "true".equalsIgnoreCase(form.getOrDefault("hasImage", "false"));
        if (title.isEmpty() || content.isEmpty()) {
            String message = screenController.showPopup("내용을 채워주세요.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }
        User user = getCurrentUser();
        user.writePost(title, content);
        Post post = new Post(database.nextPostId(), user.getUserId(), title, content, hasImage ? "이미지 첨부" : "새 글", hasImage);
        post.writePost(title, content);
        post.savePost();
        database.saveData(post);
        sendJson(exchange, 200, "{\"success\":true,\"message\":\"게시글이 등록되었습니다.\",\"next\":\"/home\"}");
    }


    public void handleCreateRoomApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }
        Map<String, String> form = parseForm(readBody(exchange.getRequestBody()));
        String productName = form.getOrDefault("productName", "").trim();
        String description = form.getOrDefault("description", "").trim();
        String maxText = form.getOrDefault("max", "").trim();
        String priceText = form.getOrDefault("price", "").trim();
        int max;
        int price;
        try {
            max = Integer.parseInt(maxText);
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            String message = screenController.showPopup("필수 정보를 모두 입력해주세요.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }
        if (productName.isEmpty() || description.isEmpty() || max <= 1 || price <= 0) {
            String message = screenController.showPopup("필수 정보를 모두 입력해주세요.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }
        GroupPurchaseRoom room = new GroupPurchaseRoom(database.nextRoomId(), getCurrentUser().getUserId(), productName, description, max, 1, price, "모집 중");
        room.createRoom(productName, max);
        room.setCurrentParticipants(1);
        room.setExpectedPrice(price);
        room.setDescription(description);
        room.setHostId(getCurrentUser().getUserId());
        database.saveData(room);
        sendJson(exchange, 200, "{\"success\":true,\"message\":\"공구 모집글이 등록되었습니다.\",\"next\":\"/group-list\"}");
    }

    public void handleJoinRoomApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }
        Map<String, String> form = parseForm(readBody(exchange.getRequestBody()));
        int roomId;
        try { roomId = Integer.parseInt(form.getOrDefault("roomId", "1")); }
        catch (NumberFormatException e) { roomId = 1; }
        boolean success = database.joinRoom(getCurrentUser().getUserId(), roomId);
        if (success) {
            GroupPurchaseRoom room = database.findRoom(roomId);
            String count = room == null ? "" : room.participantText();
            sendJson(exchange, 200, "{\"success\":true,\"message\":\"공구 참여가 완료되었습니다.\",\"count\":\"" + escapeJson(count) + "\"}");
        } else {
            String message = screenController.showPopup("모집 인원이 마감되었거나 참가할 수 없습니다.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
        }
    }

    public void handleReviewApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }
        Map<String, String> form = parseForm(readBody(exchange.getRequestBody()));
        String targetUserId = form.getOrDefault("targetUserId", "").trim();
        String content = form.getOrDefault("content", "").trim();
        int rating;
        try { rating = Integer.parseInt(form.getOrDefault("rating", "0")); }
        catch (NumberFormatException e) { rating = 0; }
        if (targetUserId.isEmpty() || content.isEmpty() || rating <= 0) {
            String message = screenController.showPopup("내용을 채워주세요.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }
        Review review = new Review(1, getCurrentUser().getUserId(), targetUserId, rating, content);
        review.writeReview(rating, content);
        review.updateAverageRating();
        database.addReviewToUser(targetUserId, rating, content);
        sendJson(exchange, 200, "{\"success\":true,\"message\":\"리뷰가 등록되었습니다.\",\"next\":\"/my\"}");
    }


    public void handleRequestSettlementApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }

        Map<String, String> form = parseForm(readBody(exchange.getRequestBody()));
        int roomId;
        int totalAmount;
        int participantCount;
        try {
            roomId = Integer.parseInt(form.getOrDefault("roomId", "0"));
            totalAmount = Integer.parseInt(form.getOrDefault("totalAmount", "0"));
            participantCount = Integer.parseInt(form.getOrDefault("participantCount", "0"));
        } catch (NumberFormatException e) {
            String message = screenController.showPopup("정산 금액 계산에 실패했습니다.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }

        GroupPurchaseRoom room = database.findRoom(roomId);
        if (room == null || totalAmount <= 0 || participantCount <= 0) {
            String message = screenController.showPopup("정산 금액 계산에 실패했습니다.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }

        String receiptImage = form.getOrDefault("receiptImage", "").trim();
        if (receiptImage.isEmpty()) {
            receiptImage = "manual-input-demo-receipt.png";
        }

        User currentUser = getCurrentUser();
        Host host = new Host(currentUser.getUserId(), currentUser.getPassword(), currentUser.getEmail());
        host.uploadPaymentReceipt(receiptImage);

        PaymentReceipt receipt = new PaymentReceipt(database.nextReceiptId(), roomId, receiptImage, totalAmount);
        receipt.uploadReceipt(receiptImage);
        int extractedAmount = receipt.extractAmountByOCR(receiptImage);
        if (extractedAmount <= 0) {
            String message = screenController.showPopup("인식에 실패했습니다. 직접 입력해주세요.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }
        database.saveData(receipt);

        host.requestSettlement(roomId);

        Settlement settlement = new Settlement(database.nextSettlementId(), roomId, totalAmount, participantCount);
        int amountPerPerson = settlement.calculateAmount(totalAmount, participantCount);
        if (amountPerPerson <= 0) {
            String message = screenController.showPopup("정산 금액 계산에 실패했습니다.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }
        settlement.updateSettlementStatus("pending");
        database.saveData(settlement);

        sendJson(exchange, 200,
                "{\"success\":true,\"message\":\"정산 요청이 완료되었습니다.\","
                        + "\"totalAmount\":" + totalAmount + ","
                        + "\"participantCount\":" + participantCount + ","
                        + "\"amountPerPerson\":" + amountPerPerson + ","
                        + "\"status\":\"pending\"}");
    }

    public void handleUploadTransferProofApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }

        Map<String, String> form = parseForm(readBody(exchange.getRequestBody()));
        String transferImage = form.getOrDefault("transferImage", "").trim();
        if (transferImage.isEmpty()) {
            String message = screenController.showPopup("재업로드해주세요.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }

        User currentUser = getCurrentUser();
        Member member = new Member(currentUser.getUserId(), currentUser.getPassword(), currentUser.getEmail());
        member.uploadTransferProof(transferImage);

        TransferProof proof = new TransferProof(database.nextTransferProofId(), currentUser.getUserId(), transferImage);
        proof.uploadTransferProof(transferImage);
        proof.updateProofStatus("confirmed");
        database.saveData(proof);

        sendJson(exchange, 200, "{\"success\":true,\"message\":\"송금 인증이 완료되었습니다.\",\"status\":\"confirmed\"}");
    }

    public void handleAdminActionApi(HttpExchange exchange) throws IOException {
        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            sendJson(exchange, 405, "{\"success\":false,\"message\":\"Method Not Allowed\"}");
            return;
        }
        Map<String, String> form = parseForm(readBody(exchange.getRequestBody()));
        String targetUserId = form.getOrDefault("targetUserId", "").trim();
        String actionType = form.getOrDefault("actionType", "").trim();
        if (targetUserId.isEmpty() || actionType.isEmpty()) {
            String message = screenController.showPopup("적용할 관리 조치를 선택하세요.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
            return;
        }
        User actor = getCurrentUser();
        if (!(actor instanceof Administrator)) {
            User adminUser = database.findUser("admin");
            if (adminUser != null) {
                actor = adminUser;
                currentUserId = adminUser.getUserId();
            }
        }
        boolean success = database.applyAdminManagementAction(actor.getUserId(), targetUserId, actionType);
        if (success) {
            String actionLabel;
            if ("restrict".equals(actionType)) {
                actionLabel = "24시간 활동 제한";
            } else if ("suspend".equals(actionType)) {
                actionLabel = "계정 정지";
            } else if ("release".equals(actionType)) {
                actionLabel = "제재 해제";
            } else {
                actionLabel = "관리";
            }
            sendJson(exchange, 200, "{\"success\":true,\"message\":\"" + escapeJson(targetUserId + " 사용자에게 " + actionLabel + " 조치를 적용했습니다.") + "\"}");
        } else {
            String message = screenController.showPopup("적용할 관리 조치를 선택하세요.");
            sendJson(exchange, 200, "{\"success\":false,\"message\":\"" + escapeJson(message) + "\"}");
        }
    }

    private User getCurrentUser() {
        User user = database.findUser(currentUserId);
        if (user == null) {
            user = database.findUser("demo");
        }
        return user;
    }

    private boolean isValidEmail(String email) {
        if (email == null || email.isBlank()) {
            return false;
        }
        return email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)+$");
    }

    private String renderSimplePage(String title, String headline, String description) {
        String safeTitle = escapeHtml(title);
        String safeHeadline = escapeHtml(headline);
        String safeDescription = escapeHtml(description);
        return """
                <!doctype html>
                <html lang=\"ko\">
                <head>
                    <meta charset=\"UTF-8\">
                    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">
                    <title>%s</title>
                    <style>
                        body { margin:0; font-family: Arial, sans-serif; background:#f2f2f7; }
                        .phone { max-width:390px; min-height:100vh; margin:0 auto; background:#fff; padding:28px 20px; box-sizing:border-box; }
                        .logo { font-size:26px; font-weight:800; text-align:center; margin-bottom:24px; }
                        .deal { color:#007AFF; } .mate { color:#34C759; }
                        .card { border:1px solid #eee; border-radius:18px; padding:24px; text-align:center; box-shadow:0 8px 20px rgba(0,0,0,.06); }
                        h1 { font-size:22px; margin:0 0 10px; }
                        p { color:#666; line-height:1.6; }
                        a { display:block; margin-top:18px; padding:14px; border-radius:10px; background:#007AFF; color:#fff; text-decoration:none; font-weight:700; }
                    </style>
                </head>
                <body>
                    <main class=\"phone\">
                        <div class=\"logo\"><span class=\"deal\">DEAL</span><span class=\"mate\">MATE</span></div>
                        <section class=\"card\">
                            <h1>%s</h1>
                            <p>%s</p>
                            <a href=\"/login\">로그인 화면으로 돌아가기</a>
                        </section>
                    </main>
                </body>
                </html>
                """.formatted(safeTitle, safeHeadline, safeDescription);
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    private String readBody(InputStream inputStream) throws IOException {
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }

    private Map<String, String> parseForm(String body) {
        Map<String, String> result = new HashMap<>();
        if (body == null || body.isBlank()) {
            return result;
        }
        String[] pairs = body.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            String key = decode(keyValue[0]);
            String value = keyValue.length > 1 ? decode(keyValue[1]) : "";
            result.put(key, value);
        }
        return result;
    }

    private String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private void sendHtml(HttpExchange exchange, int statusCode, String html) throws IOException {
        byte[] response = html.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response);
        }
    }

    private void sendJson(HttpExchange exchange, int statusCode, String json) throws IOException {
        byte[] response = json.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response);
        }
    }

    private void sendText(HttpExchange exchange, int statusCode, String text) throws IOException {
        byte[] response = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "text/plain; charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, response.length);
        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(response);
        }
    }

    private String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
