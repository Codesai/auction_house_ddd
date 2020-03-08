package com.codesai.reputation.business.actions.commands;

public class UserReputationForAuctionWinnerCommand implements Command{
    public final String userId;
    public final double winnerBid;

    public UserReputationForAuctionWinnerCommand(String userId, double winnerBid) {
        this.userId = userId;
        this.winnerBid = winnerBid;
    }
}
