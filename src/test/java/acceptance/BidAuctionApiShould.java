package acceptance;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.Bid;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static com.codesai.auction_house.infrastructure.ActionFactory.auctionRepository;
import static helpers.builder.AuctionBuilder.anAuction;
import static io.restassured.RestAssured.given;
import static matchers.BidAssert.assertThatBid;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class BidAuctionApiShould extends ApiTest {

    @Test
    public void
    post_to_bid_an_existing_auction() throws JSONException {
        var givenAuctionId = givingAnExistingAuction().id;
        var expectedBid = new Bid(money(50));

        given().
                when().
                body(JSONBuilder.createBidJsonFrom(expectedBid)).
                post("auction/{id}/bid", givenAuctionId).
                then().
                assertThat().
                statusCode(201).
                body(equalTo("OK"));

        Auction actualAuction = auctionRepository().retrieveById(givenAuctionId);
        assertThat(actualAuction.bids).hasSize(1);
        assertThatBid(actualAuction.topBid().orElseThrow()).isEqualTo(expectedBid);
    }

    private Auction givingAnExistingAuction() {
        var expectedAuction = anAuction().build();
        this.auctionRepository.save(expectedAuction);
        return expectedAuction;
    }
}
