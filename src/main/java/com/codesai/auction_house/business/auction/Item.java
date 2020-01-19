package com.codesai.auction_house.business.auction;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Item {
    private final String item;
    private final String description;

    public Item(String item, String description) {
        this.item = item;
        this.description = description;
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
        return "Item{" +
                "item='" + item + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
