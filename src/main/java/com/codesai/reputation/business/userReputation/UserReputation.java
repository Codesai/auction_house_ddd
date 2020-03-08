package com.codesai.reputation.business.userReputation;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class UserReputation {
    private final String anyUserId;
    private long reputation;

    public UserReputation(String anyUserId, long reputation) {
        this.anyUserId = anyUserId;
        this.reputation = reputation;
    }

    public void accountBidWinner(double winnerBid) {
        reputation = reputation + reputationForWinAnAuction(winnerBid);
    }

    private long reputationForWinAnAuction(double winnerBid) {
        return Math.round(winnerBid) / 100;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
