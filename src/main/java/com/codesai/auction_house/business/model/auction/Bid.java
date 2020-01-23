package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.generic.Money;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

public class Bid {
    public final String id;
    public Money money;

    public Bid(Money money) {
        this.id = UUID.randomUUID().toString();
        this.money = money;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
