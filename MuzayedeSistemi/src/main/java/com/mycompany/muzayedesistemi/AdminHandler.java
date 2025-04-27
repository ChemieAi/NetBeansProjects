/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.muzayedesistemi;

/**
 *
 * @author burak
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class AdminHandler implements Runnable {
     private Socket socket;
    private List<AuctionSession> sessions;
    private AdminInterface adminInterface;

    public AdminHandler(Socket socket, List<AuctionSession> sessions, AdminInterface adminInterface) {
        this.socket = socket;
        this.sessions = sessions;
        this.adminInterface = adminInterface;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Gelen komutu işle
                processCommand(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processCommand(String command) {
        String[] parts = command.split(" ");
        String action = parts[0];

        switch (action) {
            case "SHOW_HISTORY":
                if (parts.length < 2) {
                    adminInterface.sendMessageToClient("Usage: SHOW_HISTORY [session_id]");
                    return;
                }
                int sessionId = Integer.parseInt(parts[1]);
                showAuctionHistory(sessionId);
                break;
            case "SHOW_ONGOING_AUCTIONS":
                showOngoingAuctions();
                break;
            case "SHOW_FINISHED_AUCTIONS":
                showFinishedAuctions();
                break;
            // Diğer case'ler buraya eklenecek
            default:
                adminInterface.sendMessageToClient("Unknown command: " + action);
        }
    }

    private void showOngoingAuctions() {
        StringBuilder message = new StringBuilder();
        message.append("Ongoing Auctions:\n");
        for (int i = 0; i < sessions.size(); i++) {
            AuctionSession session = sessions.get(i);
            if (!session.isFinished()) {
                message.append("Session ").append(i).append(": ").append(session.getItemName()).append("\n");
            }
        }
        adminInterface.sendMessageToClient(message.toString());
    }

    private void showFinishedAuctions() {
        StringBuilder message = new StringBuilder();
        message.append("Finished Auctions:\n");
        for (int i = 0; i < sessions.size(); i++) {
            AuctionSession session = sessions.get(i);
            if (session.isFinished()) {
                message.append("Session ").append(i).append(": ").append(session.getItemName()).append("\n");
            }
        }
        adminInterface.sendMessageToClient(message.toString());
    }

    private void showAuctionHistory(int sessionId) {
        // İstenen müzayede oturumunun işlem geçmişini al ve yöneticiye gönder
        if (sessionId >= 0 && sessionId < sessions.size()) {
            AuctionSession session = sessions.get(sessionId);
            StringBuilder historyMessage = new StringBuilder();
            historyMessage.append("Auction History for session ").append(sessionId).append(":").append("\n");
            List<BidHistoryEntry> bidHistory = session.getBidHistory();
            for (BidHistoryEntry entry : bidHistory) {
                historyMessage.append(entry.getBidderName()).append(" bid ").append(entry.getBidAmount()).append("\n");
            }
            adminInterface.sendMessageToClient(historyMessage.toString());
        } else {
            adminInterface.sendMessageToClient("Invalid session ID");
        }
    }
}


