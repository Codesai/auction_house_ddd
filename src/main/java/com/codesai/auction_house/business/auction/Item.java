package com.codesai.auction_house.business.auction;

import com.codesai.auction_house.business.generic.ValueObject;

public final class Item extends ValueObject {
    public final String name;
    public final String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
