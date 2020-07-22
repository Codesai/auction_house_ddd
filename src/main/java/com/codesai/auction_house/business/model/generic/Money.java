package com.codesai.auction_house.business.model.generic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.codesai.auction_house.business.model.generic.Currency.EUR;

public class Money {
    public final double amount;
    private final Currency currency;

    private Money(double amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public static Money money(double amount) {
        return new Money(amount, EUR);
    }

    public boolean isGreaterThan(Money otherMoney) {
        return this.amount > otherMoney.amount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }
}
