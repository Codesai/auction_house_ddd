package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.generic.Money;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

public class Bid {
    public final String id = UUID.randomUUID().toString();
    public Money money;
    public String userId;

    public Bid(Money money) {
        this.money = money;
    }

    public Bid(Money conquerPrice, String userId) {
        money = conquerPrice;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bid bid = (Bid) o;
        return new EqualsBuilder()
                .append(id, bid.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(money)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
