package com.codesai.auction_house.business.generic;

public class Money extends ValueObject {
    public final double amount;
    private final Currency currency;

    public static Money money(double amount) {
        return new Money(amount);
    }

    private Money(double amount) {
        this.amount = amount;
        this.currency = Currency.EUR;
    }
}
