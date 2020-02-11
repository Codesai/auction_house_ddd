package com.codesai.auction_house.business.model.owner;

import com.codesai.auction_house.business.model.OwnerId;

public interface OwnerRepository {
    Owner retrieveById(OwnerId ownerId);

    void save();
}
