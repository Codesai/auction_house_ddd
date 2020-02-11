package auction_house.acceptance;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.Bid;
import com.codesai.auction_house.business.model.auction.exceptions.FirstBidShouldBeGreaterThanStartingPrice;
import com.codesai.auction_house.business.model.bidder.BidderId;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static auction_house.helpers.builder.AuctionBuilder.anAuction;
import static auction_house.matchers.BidAssert.assertThatBid;
import static com.codesai.auction_house.business.model.generic.Money.money;
import static com.codesai.auction_house.infrastructure.ActionFactory.auctionRepository;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class BidAuctionApiShould extends ApiTest {

    @Test public void
    post_to_bid_an_existing_auction() {
        var givenAuctionId = givingAnExistingAuction().id;
        var givenBidderId = new BidderId("AnyUserId" + UUID.randomUUID());
        var expectedBid = new Bid(money(50), givenBidderId);

        given().
                when().
                body(JSONParser.createBidJsonFrom(expectedBid)).
                post("auction/{id}/bid", givenAuctionId).
                then().
                assertThat().
                statusCode(201).
                body(equalTo("OK"));

        Auction actualAuction = auctionRepository().retrieveById(givenAuctionId);
        assertThat(actualAuction.bids).hasSize(1);
        assertThatBid(actualAuction.topBid().orElseThrow()).isEqualTo(expectedBid);
    }

    @Test public void
    get_an_error_response_when_try_to_create_an_invalid_bid() {
        var auction = anAuction().withStartingPrice(money(10)).build();
        var insufficientBid = new Bid(money(5), new BidderId("AnyBidderId"));
        auctionRepository.save(auction);

        given().
            when().
                body(JSONParser.createBidJsonFrom(insufficientBid)).
                post("auction/{id}/bid", auction.id).
            then().assertThat().
                statusCode(422).
                contentType("application/json").
                body(
                        "name", equalTo(FirstBidShouldBeGreaterThanStartingPrice.class.getSimpleName()),
                        "description", equalTo("Initial auction price is 10.00 and bid is only 5.00")
                );
    }

    private Auction givingAnExistingAuction() {
        var expectedAuction = anAuction().build();
        auctionRepository.save(expectedAuction);
        return expectedAuction;
    }
}
