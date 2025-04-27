/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.muzayedesistemi;

/**
 *
 * @author burak
 */
import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private List<AuctionSession> sessions;
    private List<ClientHandler> clients; // İstemci bağlantılarını saklamak için
    private AdminInterface adminInterface;
    private BufferedReader reader;
    private PrintWriter writer;

    public ClientHandler(Socket clientSocket, List<AuctionSession> sessions, List<ClientHandler> clients,AdminInterface adminInterface) {
        this.clientSocket = clientSocket;
        this.sessions = sessions;
        this.clients = clients; // clients listesini tanımla
        this.adminInterface = adminInterface;
        try {
            reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            writer = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String clientName = reader.readLine();
            writer.println("Hello, " + clientName + "! Welcome to the Auction House.");
            String itemName = reader.readLine();
            int initialPrice = Integer.parseInt(reader.readLine());
            int minBidIncrement = Integer.parseInt(reader.readLine());
            int targetPrice = Integer.parseInt(reader.readLine());

            AuctionSession session = new AuctionSession(itemName, initialPrice, minBidIncrement, targetPrice);
            sessions.add(session);

            writer.println("Auction session started for: " + itemName);
            writer.println("Initial Price: " + initialPrice);
            writer.println("Minimum Bid Increment: " + minBidIncrement);
            writer.println("Target Price: " + targetPrice);

            String line;
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String input;
            while ((input = reader.readLine()) != null) {
                // Gelen mesajı işleyin ve uygun eylemi gerçekleştirin
                String[] parts = input.split(" ");
                String command = parts[0];
 
                
                switch (command) {
                    case "START":
                        startAuctionSession(parts, writer);
                        break;
                    case "BID":
                        makeBid(parts, writer);
                        break;
                    // Diğer komutlar için case'ler eklenebilir
                    default:
                        writer.println("Invalid command");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void startAuctionSession(String[] parts, PrintWriter writer) {
        if (parts.length < 4) {
            writer.println("START command requires item name, initial price, and min bid increment");
            return;
        }

        String itemName = parts[1];
        int initialPrice = Integer.parseInt(parts[2]);
        int minBidIncrement = Integer.parseInt(parts[3]);
        int targetPrice = Integer.parseInt(parts[4]);
        
        AuctionSession session = new AuctionSession(itemName, initialPrice, minBidIncrement, targetPrice);
        sessions.add(session);

        writer.println("Auction session started for item: " + itemName);
    }

    private void broadcastCurrentPrice(AuctionSession session) {
    String message = "CURRENT_PRICE " + session.getSessionId() + " " + session.getCurrentPrice();
    for (ClientHandler handler : clients) {
        handler.sendMessageToClient(message);
        }
    }
  
    private void makeBid(String[] parts, PrintWriter writer) {
    if (parts.length < 3) {
        writer.println("BID command requires session ID and bid amount");
        return;
        }

    int sessionId = Integer.parseInt(parts[1]);
    int bidAmount = Integer.parseInt(parts[2]);

    AuctionSession session = findSessionById(sessionId);
    if (session == null) {
        writer.println("Auction session with ID " + sessionId + " not found");
        return;
        }

    synchronized (session) {
        int currentPrice = session.getCurrentPrice();
        int minBidIncrement = session.getMinBidIncrement();
        if (bidAmount < currentPrice + minBidIncrement) {
            writer.println("Bid amount must be at least " + minBidIncrement + " more than current price");
            return;
        }

        session.setCurrentPrice(bidAmount);
        session.setHighestBidder(Thread.currentThread().getName());
        writer.println("Bid successful for item: " + session.getItemName() + ", current price: " + bidAmount);

        // Güncel fiyatı istemcilere gönder
        broadcastCurrentPrice(session);
        // Müzayedeyi sonlandırma kontrolü
        session.endAuction(session.getTargetPrice()); // Burada targetPrice hedef fiyat düzeyini temsil eder
        }
    }


    private void sendMessageToClient(String message) {
        try {
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);
            writer.println(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // İlgili oturumu kimliğine göre bulan yardımcı metot
    private AuctionSession findSessionById(int sessionId) {
        for (AuctionSession session : sessions) {
            if (session.getSessionId() == sessionId) {
                return session;
            }
        }
        return null;
    }
}
