package com.codesai.auction_house.business.model.generic;

public final class Money extends ValueObject {
    public final double amount;
    private final Currency currency;

    public static Money money(double amount) {
        return new Money(amount);
    }

    private Money(double amount) {
        this.amount = amount;
        this.currency = Currency.EUR;
    }

    public boolean isLessThan(Money money) {
        return this.amount < money.amount;
    }

    public boolean isGreaterThan(Money money) {
        return this.amount > money.amount;
    }
}
