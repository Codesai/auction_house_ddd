package com.codesai.auction_house.business.model.exceptions;

import com.codesai.auction_house.business.model.generic.Money;

public class InitialBidCannotBeGreaterThanConquerPriceException extends RuntimeException {

    public InitialBidCannotBeGreaterThanConquerPriceException(Money initialBidAmount, Money conquerPriceAmount) {
        super("initial cannot be greater " + initialBidAmount.amount + " than conquer price " + conquerPriceAmount.amount);
    }
}
