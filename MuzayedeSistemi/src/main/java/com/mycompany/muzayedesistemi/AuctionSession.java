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
import java.util.concurrent.atomic.AtomicInteger;

public class AuctionSession {
    private static final AtomicInteger sessionCounter = new AtomicInteger(0);

    private int sessionId;
    private String itemName;
    private int currentPrice;
    private int minBidIncrement;
    private String highestBidder;
    private int targetPrice;
    private List<BidHistoryEntry> bidHistory;
    private boolean finished;

    public AuctionSession(String itemName, int initialPrice, int minBidIncrement, int targetPrice) {
        this.sessionId = sessionCounter.incrementAndGet();
        this.itemName = itemName;
        this.currentPrice = initialPrice;
        this.minBidIncrement = minBidIncrement;
        this.targetPrice = targetPrice;
        this.bidHistory = new ArrayList<>();
        this.finished = false;
    }

    // Getters and setters
    public int getSessionId() {
        return sessionId;
    }

    public String getItemName() {
        return itemName;
    }

    public int getCurrentPrice() {
        return currentPrice;
    }

    // Bu metodları ekleyerek müzayede oturumunun durumunu güncelleyebiliriz
    public void setCurrentPrice(int currentPrice) {
        this.currentPrice = currentPrice;
    }

    public int getMinBidIncrement() {
        return minBidIncrement;
    }

    public String getHighestBidder() {
        return highestBidder;
    }

    public void setHighestBidder(String highestBidder) {
        this.highestBidder = highestBidder;
    }

    public int getTargetPrice() {
        return targetPrice;
    }
    
    public void endAuction(int targetPrice) {
    if (currentPrice >= targetPrice) {
        // Müzayede oturumunu sonlandırma işlemleri burada yapılacak
    }
}
    public List<BidHistoryEntry> getBidHistory() {
        return bidHistory;
    }
    
    public void addBid(String bidderName, int bidAmount) {
        bidHistory.add(new BidHistoryEntry(bidderName, bidAmount));
    }

    public boolean isFinished() {
        return currentPrice >= targetPrice; // Mevcut fiyat hedef fiyata ulaşırsa oturum bitmiş sayılır
    }

    public void updateCurrentPrice(int newPrice) {
        this.currentPrice = newPrice;
    }

}
