package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.bidder.BidderId;
import com.codesai.auction_house.business.model.generic.Money;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

public class Bid {
    public final String id = UUID.randomUUID().toString();
    public final Money money;
    public final BidderId bidderId;

    public Bid(Money money, BidderId bidderId) {
        this.money = money;
        this.bidderId = bidderId;
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
