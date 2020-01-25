package com.codesai.auction_house.business.model.auction;

import com.codesai.auction_house.business.model.generic.ValueObject;

public final class
Item extends ValueObject {
    public final String name;
    public final String description;

    private Item(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static Item item(String name, String description) {
        return new Item(name, description);
    }
}
