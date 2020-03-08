package com.codesai;

import com.codesai.auction_house.infrastructure.AuctionHouse;
import com.codesai.reputation.infrastructure.Reputation;

public class App {

    // TODO: crear action en en el bd de reputation
    // TODO: crear test de arquitectura para asegurar que los BC's estan aislados
    public static void main(String[] args) {
        AuctionHouse.start();
        Reputation.start();
    }
}
