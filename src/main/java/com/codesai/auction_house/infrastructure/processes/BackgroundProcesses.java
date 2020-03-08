package com.codesai.auction_house.infrastructure.processes;

import com.codesai.auction_house.infrastructure.ActionFactory;

import java.util.Timer;
import java.util.TimerTask;

public class BackgroundProcesses {

    public static void startBackgroundProcesses() {
        System.out.println("starting auction house background processes");
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Execute declare winner action");
                ActionFactory.declareAuctionWinnerAuction().execute();
            }
        }, 0, 5000);
    }
}
