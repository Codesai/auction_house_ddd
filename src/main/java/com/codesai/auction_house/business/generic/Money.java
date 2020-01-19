package com.codesai.auction_house.business.generic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Money {
    private final double amount;
    private final Currency currency;

    public static Money money(double amount) {
        return new Money(amount);
    }

    private Money(double amount) {
        this.amount = amount;
        this.currency = Currency.EUR;
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
        return "Money{" +
                "amount=" + amount +
                ", currency=" + currency +
                '}';
    }
}
