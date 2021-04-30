package com.codesai.auction_house.business.model.generic;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class Money {
    private final double amount;

    public Money(double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
