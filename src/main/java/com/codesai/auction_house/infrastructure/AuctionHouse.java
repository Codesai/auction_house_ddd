package com.codesai.auction_house.infrastructure;

import static com.codesai.auction_house.infrastructure.delivery_mechanism.Routing.startApi;
import static com.codesai.auction_house.infrastructure.processes.BackgroundProcesses.startBackgroundProcesses;

public class AuctionHouse {

    public static void start() {
        startApi();
        startBackgroundProcesses();
    }

}
