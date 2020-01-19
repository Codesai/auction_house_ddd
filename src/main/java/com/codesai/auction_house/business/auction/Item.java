package com.codesai.auction_house.business.auction;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Item {
    public final String name;
    public final String description;

    public Item(String name, String description) {
        this.name = name;
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
                "item='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
