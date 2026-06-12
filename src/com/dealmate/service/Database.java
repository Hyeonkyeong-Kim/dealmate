package com.dealmate.service;

import com.dealmate.model.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Base64;

public class Database {
    private ArrayList<User> userTable = new ArrayList<>();
    private ArrayList<Post> postTable = new ArrayList<>();
    private ArrayList<GroupPurchaseRoom> roomTable = new ArrayList<>();
    private ArrayList<Settlement> settlementTable = new ArrayList<>();
    private final Path userDataFile = Path.of("data", "users.txt");

    public void initializeSampleData() {
        loadUsers();

        User demo = findUser("demo");
        if (demo == null) {
            demo = new User("demo", "demo123", "demo@dealmate.com");
            userTable.add(demo);
        }
        applyDemoProfile(demo, "대구광역시 남구 대명동", 4.8,
                "생필품 대용량 구매 모집|3/4|정산 전",
                "정산이 빠르고 친절했어요.|5", "약속 시간에 잘 맞춰주셨어요.|4");

        User demo1 = findUser("demo1");
        if (demo1 == null) {
            demo1 = new User("demo1", "demo123", "demo1@dealmate.com");
            userTable.add(demo1);
        }
        applyDemoProfile(demo1, "대구광역시 수성구 범어동", 4.6,
                "샐러드 공동구매 모집|2/4|참여중",
                "공동구매 진행 안내가 깔끔했어요.|5", "응답이 빠르고 친절했어요.|4");

        User admin = findUser("admin");
        if (admin == null) {
            admin = new Administrator("admin", "admin123", "admin@dealmate.com");
            userTable.add(admin);
        }
        applyDemoProfile(admin, "대구광역시 남구 대명동", 5.0,
                "관리자 확인용 공구 예시|1/4|확인용",
                "관리자 계정 시연용 리뷰입니다.|5");

        User user123 = findUser("user123");
        if (user123 == null) {
            user123 = new User("user123", "user123", "user123@dealmate.com");
            userTable.add(user123);
        }
        applyDemoProfile(user123, "대구광역시 남구 대명동", 2.1,
                "생수 2L 공동구매|4/4|정산 지연",
                "정산 약속 시간을 지키지 않았어요.|2", "응답이 늦고 송금 인증이 지연됐어요.|2");

        User user789 = findUser("user789");
        if (user789 == null) {
            user789 = new User("user789", "user789", "user789@dealmate.com");
            userTable.add(user789);
        }
        applyDemoProfile(user789, "대구광역시 수성구 범어동", 2.8,
                "휴지 대용량 할인 공구|4/5|리뷰 확인 필요",
                "거래 시간 약속이 맞지 않았어요.|3", "연락이 조금 늦었어요.|3");

        initializeSamplePosts();
        initializeSampleRooms();
        saveUsers();
    }

    private void initializeSamplePosts() {
        postTable.clear();
        postTable.add(new Post(1, "demo", "생수 2L 6개 묶음 할인", "대명동 근처 마트 특가 정보입니다. 생수 2L 6개 묶음 상품을 1인당 3,000원에 구매할 수 있어요. 공동구매를 원하면 공구 탭에서 모집글을 확인하세요.", "1인당 3,000원", true));
        postTable.add(new Post(2, "demo1", "휴지 대용량 할인", "대형마트에서 휴지 대용량 묶음 상품을 할인 중입니다. 혼자 사기 부담되는 생필품은 공구로 나누어 구매하면 좋아요.", "12,900원", true));
        postTable.add(new Post(3, "demo", "편의점 1+1 행사", "대명동 주변 편의점에서 간식류 1+1 행사를 진행 중입니다. 행사 기간 안에 필요한 상품을 확인해보세요.", "행사 중"));
    }


    private void initializeSampleRooms() {
        roomTable.clear();
        roomTable.add(new GroupPurchaseRoom(1, "demo", "생수 2L 6개 묶음 공구", "대명동 근처 마트 특가 상품을 함께 구매할 이웃을 모집합니다.", 4, 2, 12000, "모집 중"));
        roomTable.add(new GroupPurchaseRoom(2, "demo1", "휴지 대용량 할인 공구", "혼자 사기 부담되는 생필품을 함께 나눠 구매해요.", 5, 3, 12900, "모집 중"));
    }

    public Post findPost(int postId) {
        for (Post post : postTable) {
            if (post.getPostId() == postId) {
                return post;
            }
        }
        return null;
    }

    public int nextPostId() {
        int max = 0;
        for (Post post : postTable) {
            if (post.getPostId() > max) {
                max = post.getPostId();
            }
        }
        return max + 1;
    }

    private void applyDemoProfile(User user, String neighborhood, double rating, String joinedGroup, String... reviews) {
        user.setNeighborhood(neighborhood);
        user.setAverageRating(rating);
        user.getJoinedGroupSummaries().clear();
        user.getJoinedGroupSummaries().add(joinedGroup);
        user.getReceivedReviewSummaries().clear();
        for (String review : reviews) {
            user.getReceivedReviewSummaries().add(review);
        }
        if (!isValidAccountStatus(user.getAccountStatus())) {
            user.setAccountStatus("정상");
        }
    }

    public void saveData(Object object) {
        if (object instanceof User) {
            User user = (User) object;
            if (findUser(user.getUserId()) == null) {
                if (user.getNeighborhood() == null || user.getNeighborhood().isBlank()) {
                    user.setNeighborhood("대구광역시 남구 대명동");
                }
                if (user.getJoinedGroupSummaries().isEmpty()) {
                    user.getJoinedGroupSummaries().add("첫 공동구매 참여 전|0/0|참여 내역 없음");
                }
                if (user.getReceivedReviewSummaries().isEmpty()) {
                    user.getReceivedReviewSummaries().add("아직 받은 리뷰가 없습니다.|0");
                }
                userTable.add(user);
                saveUsers();
            }
        } else if (object instanceof Post) {
            postTable.add((Post) object);
        } else if (object instanceof GroupPurchaseRoom) {
            roomTable.add((GroupPurchaseRoom) object);
        } else if (object instanceof Settlement) {
            settlementTable.add((Settlement) object);
        }
    }

    public User findUser(String userId) {
        if (userId == null) {
            return null;
        }
        for (User user : userTable) {
            if (userId.equals(user.getUserId()) || userId.equals(user.getEmail())) {
                return user;
            }
        }
        return null;
    }

    public void updateNeighborhood(String userId, String neighborhood) {
        User user = findUser(userId);
        if (user != null && neighborhood != null && !neighborhood.isBlank()) {
            user.setNeighborhood(neighborhood.trim());
            saveUsers();
        }
    }

    public GroupPurchaseRoom findRoom(int roomId) {
        for (GroupPurchaseRoom room : roomTable) {
            if (room.getRoomId() == roomId) {
                return room;
            }
        }
        return null;
    }

    private void loadUsers() {
        userTable.clear();
        if (!Files.exists(userDataFile)) {
            return;
        }
        try {
            List<String> lines = Files.readAllLines(userDataFile, StandardCharsets.UTF_8);
            for (String line : lines) {
                if (line == null || line.isBlank()) {
                    continue;
                }

                User user;
                if (line.startsWith("V2\t")) {
                    String[] parts = line.split("\t", -1);
                    if (parts.length < 10) continue;
                    String type = decodeField(parts[1]);
                    String id = decodeField(parts[2]);
                    String password = decodeField(parts[3]);
                    String email = decodeField(parts[4]);
                    user = "ADMIN".equals(type) ? new Administrator(id, password, email) : new User(id, password, email);
                    user.setNeighborhood(decodeField(parts[5]));
                    try {
                        user.setAverageRating(Double.parseDouble(decodeField(parts[6])));
                    } catch (NumberFormatException ignored) {
                        user.setAverageRating(5.0);
                    }
                    user.getJoinedGroupSummaries().clear();
                    String joined = decodeField(parts[7]);
                    if (!joined.isBlank()) {
                        for (String item : joined.split(";;", -1)) {
                            if (!item.isBlank()) user.getJoinedGroupSummaries().add(item);
                        }
                    }
                    user.getReceivedReviewSummaries().clear();
                    String reviews = decodeField(parts[8]);
                    if (!reviews.isBlank()) {
                        for (String item : reviews.split(";;", -1)) {
                            if (!item.isBlank()) user.getReceivedReviewSummaries().add(item);
                        }
                    }
                    String status = decodeField(parts[9]);
                    user.setAccountStatus(isValidAccountStatus(status) ? status : "정상");
                } else {
                    // 예전 버전 파일은 joinedGroup/review 안에 | 문자가 들어가서 상태값이 깨질 수 있다.
                    // 기본 계정 정보만 최대한 복구하고, 샘플 프로필은 initializeSampleData()에서 다시 채운다.
                    String[] parts = line.split("\\|", -1);
                    if (parts.length < 4) continue;
                    String type = parts[0];
                    String id = parts[1];
                    String password = parts[2];
                    String email = parts[3];
                    user = "ADMIN".equals(type) ? new Administrator(id, password, email) : new User(id, password, email);
                    if (parts.length > 4 && !parts[4].isBlank()) user.setNeighborhood(parts[4]);
                    if (parts.length > 5 && !parts[5].isBlank()) {
                        try { user.setAverageRating(Double.parseDouble(parts[5])); }
                        catch (NumberFormatException ignored) { user.setAverageRating(5.0); }
                    }
                    String lastField = parts.length > 0 ? parts[parts.length - 1] : "";
                    user.setAccountStatus(isValidAccountStatus(lastField) ? lastField : "정상");
                }
                userTable.add(user);
            }
        } catch (IOException e) {
            System.out.println("사용자 정보를 불러오지 못했습니다: " + e.getMessage());
        }
    }

    private void saveUsers() {
        try {
            Files.createDirectories(userDataFile.getParent());
            ArrayList<String> lines = new ArrayList<>();
            for (User user : userTable) {
                String type = user instanceof Administrator ? "ADMIN" : "USER";
                String joined = String.join(";;", user.getJoinedGroupSummaries());
                String reviews = String.join(";;", user.getReceivedReviewSummaries());
                String status = isValidAccountStatus(user.getAccountStatus()) ? user.getAccountStatus() : "정상";
                lines.add("V2\t" + encodeField(type)
                        + "\t" + encodeField(user.getUserId())
                        + "\t" + encodeField(user.getPassword())
                        + "\t" + encodeField(user.getEmail())
                        + "\t" + encodeField(nullToEmpty(user.getNeighborhood()))
                        + "\t" + encodeField(String.valueOf(user.getAverageRating()))
                        + "\t" + encodeField(joined)
                        + "\t" + encodeField(reviews)
                        + "\t" + encodeField(status));
            }
            Files.write(userDataFile, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("사용자 정보를 저장하지 못했습니다: " + e.getMessage());
        }
    }

    private boolean isValidAccountStatus(String status) {
        return "정상".equals(status) || "24시간 활동 제한".equals(status) || "계정 정지".equals(status);
    }

    private String encodeField(String value) {
        return Base64.getEncoder().encodeToString(nullToEmpty(value).getBytes(StandardCharsets.UTF_8));
    }

    private String decodeField(String value) {
        if (value == null || value.isBlank()) return "";
        try {
            return new String(Base64.getDecoder().decode(value), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            return "";
        }
    }

    private String nullToEmpty(String value) {
        return value == null ? "" : value;
    }


    public int nextRoomId() {
        int max = 0;
        for (GroupPurchaseRoom room : roomTable) {
            if (room.getRoomId() > max) max = room.getRoomId();
        }
        return max + 1;
    }

    public boolean joinRoom(String userId, int roomId) {
        User user = findUser(userId);
        GroupPurchaseRoom room = findRoom(roomId);
        if (user == null || room == null || !room.hasAvailableSeat()) {
            return false;
        }

        // 내가 만든 공구에는 이미 호스트로 포함되어 있으므로 멤버로 다시 참여할 수 없다.
        if (userId != null && userId.equals(room.getHostId())) {
            return false;
        }

        String title = room.getProductName();
        for (String summary : user.getJoinedGroupSummaries()) {
            if (summary.startsWith(title + "|")) {
                return false;
            }
        }
        room.updateParticipantCount();
        int perPerson = room.getMaxParticipants() <= 0 ? 0 : room.getExpectedPrice() / room.getMaxParticipants();
        user.getJoinedGroupSummaries().add(title + "|" + room.participantText() + "|참여중 · 예상 " + String.format("%,d원", perPerson));
        saveUsers();
        return true;
    }

    public void addReviewToUser(String targetUserId, int rating, String content) {
        User target = findUser(targetUserId);
        if (target == null) return;
        target.getReceivedReviewSummaries().add(content + "|" + rating);
        int count = 0;
        int total = 0;
        for (String review : target.getReceivedReviewSummaries()) {
            String[] parts = review.split("\\|", -1);
            if (parts.length > 1) {
                try { total += Integer.parseInt(parts[1]); count++; } catch (NumberFormatException ignored) {}
            }
        }
        if (count > 0) {
            target.setAverageRating(Math.round((total / (double) count) * 10.0) / 10.0);
        }
        saveUsers();
    }


    public ArrayList<GroupPurchaseRoom> getRelatedRooms(String userId) {
        ArrayList<GroupPurchaseRoom> related = new ArrayList<>();
        User user = findUser(userId);
        if (user == null) {
            return related;
        }
        for (GroupPurchaseRoom room : roomTable) {
            boolean isHost = userId != null && userId.equals(room.getHostId());
            boolean isMember = false;
            for (String summary : user.getJoinedGroupSummaries()) {
                if (summary != null && summary.startsWith(room.getProductName() + "|")) {
                    isMember = true;
                    break;
                }
            }
            if (isHost || isMember) {
                related.add(room);
            }
        }
        return related;
    }

    public boolean applyAdminManagementAction(String adminId, String targetUserId, String actionType) {
        User admin = findUser(adminId);
        User target = findUser(targetUserId);
        if (!(admin instanceof Administrator) || target == null || target instanceof Administrator) {
            return false;
        }
        Administrator administrator = (Administrator) admin;
        AdministratorManagement management = new AdministratorManagement();
        management.applyManagementAction(actionType);
        if ("restrict".equals(actionType)) {
            administrator.restrictUser(targetUserId);
            target.setAccountStatus("24시간 활동 제한");
        } else if ("suspend".equals(actionType)) {
            administrator.suspendUser(targetUserId);
            target.setAccountStatus("계정 정지");
        } else if ("release".equals(actionType)) {
            management.applyManagementAction("release");
            target.setAccountStatus("정상");
        } else {
            return false;
        }
        saveData(management);
        saveUsers();
        return true;
    }

    public ArrayList<User> getUserTable() { return userTable; }
    public ArrayList<Post> getPostTable() { return postTable; }
    public ArrayList<GroupPurchaseRoom> getRoomTable() { return roomTable; }
    public ArrayList<Settlement> getSettlementTable() { return settlementTable; }
}
