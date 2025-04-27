/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.muzayedesistemi;

/**
 *
 * @author burak
 */
public class BidHistoryEntry {
    private String bidderName;
    private int bidAmount;

    public BidHistoryEntry(String bidderName, int bidAmount) {
        this.bidderName = bidderName;
        this.bidAmount = bidAmount;
    }

    public String getBidderName() {
        return bidderName;
    }

    public int getBidAmount() {
        return bidAmount;
    }
}
