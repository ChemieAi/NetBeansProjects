/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.muzayedesistemi;

/**
 *
 * @author burak
 */

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class AdminInterface {
    private List<AuctionSession> sessions;
    private PrintWriter writer;
    private BufferedReader reader;
    
    public AdminInterface(List<AuctionSession> sessions) {
        this.sessions = sessions;
    }

    public void showAllAuctions() {
        System.out.println("All Auction Sessions:");
        for (AuctionSession session : sessions) {
            System.out.println("Session ID: " + session.getSessionId() + ", Item: " + session.getItemName() + ", Current Price: " + session.getCurrentPrice() + ", Highest Bidder: " + session.getHighestBidder());
        }
    }
    public AdminInterface(Socket socket) throws IOException {
        writer = new PrintWriter(socket.getOutputStream(), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void setTargetPrice(int sessionId, int targetPrice) {
        writer.println("SET_TARGET_PRICE " + sessionId + " " + targetPrice);
    }

    public void sendMessageToClient(String message) {
        writer.println(message);
    }
    
    public void close() throws IOException {
        writer.close();
        reader.close();
    }
}


