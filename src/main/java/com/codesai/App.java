package com.codesai;

import com.codesai.auction_house.infrastructure.AuctionHouse;
import com.codesai.reputation.infrastructure.Reputation;

public class App {

    public static void main(String[] args) {
        AuctionHouse.start();
        Reputation.start();
    }
}
