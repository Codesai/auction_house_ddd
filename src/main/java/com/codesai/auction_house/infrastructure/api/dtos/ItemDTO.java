package com.codesai.auction_house.infrastructure.api.dtos;

public class ItemDTO {
    public String name;
    public String description;

    public ItemDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
