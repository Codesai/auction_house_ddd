package com.codesai.reputation.business.userReputation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserReputation {
    public final String userId;
    public long reputation;

    public UserReputation(String userId, long reputation) {
        this.userId = userId;
        this.reputation = reputation;
    }

    public void accountBidWinner(double winnerBid) {
        reputation += reputationForWinAnAuction(winnerBid);
    }

    private long reputationForWinAnAuction(double winnerBid) {
        return 1 + Math.round(winnerBid) / 100;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
