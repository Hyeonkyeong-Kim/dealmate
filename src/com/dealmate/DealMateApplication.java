package com.dealmate;

import com.dealmate.controller.PageController;
import com.dealmate.service.Database;
import com.dealmate.service.Login;
import com.dealmate.service.Registration;
import com.dealmate.service.NeighborhoodCertification;
import com.dealmate.service.ScreenController;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class DealMateApplication {
    public static void main(String[] args) throws IOException {
        int port = 8080;

        Database database = new Database();
        database.initializeSampleData();

        Login login = new Login(database);
        Registration registration = new Registration(database);
        NeighborhoodCertification neighborhoodCertification = new NeighborhoodCertification();
        ScreenController screenController = new ScreenController();
        PageController pageController = new PageController(database, login, registration, neighborhoodCertification, screenController);

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/", pageController::handleRoot);
        server.createContext("/login", pageController::handleLoginPage);
        server.createContext("/register", pageController::handleRegisterPage);
        server.createContext("/neighborhood", pageController::handleNeighborhoodPage);
        server.createContext("/home", pageController::handleHomePage);
        server.createContext("/my", pageController::handleMyPage);
        server.createContext("/admin", pageController::handleAdminPage);
        server.createContext("/write-post", pageController::handleWritePostPage);
        server.createContext("/post-detail", pageController::handlePostDetailPage);
        server.createContext("/group-list", pageController::handleGroupListPage);
        server.createContext("/create-room", pageController::handleCreateRoomPage);
        server.createContext("/room-detail", pageController::handleRoomDetailPage);
        server.createContext("/settlement", pageController::handleSettlementPage);
        server.createContext("/review", pageController::handleReviewPage);
        server.createContext("/api/login", pageController::handleLoginApi);
        server.createContext("/api/register", pageController::handleRegisterApi);
        server.createContext("/api/neighborhood", pageController::handleNeighborhoodApi);
        server.createContext("/api/write-post", pageController::handleWritePostApi);
        server.createContext("/api/create-room", pageController::handleCreateRoomApi);
        server.createContext("/api/join-room", pageController::handleJoinRoomApi);
        server.createContext("/api/review", pageController::handleReviewApi);
        server.createContext("/api/admin-action", pageController::handleAdminActionApi);
        server.setExecutor(null);
        server.start();

        System.out.println("DealMate server started.");
        System.out.println("Open: http://localhost:" + port + "/login");
    }
}
