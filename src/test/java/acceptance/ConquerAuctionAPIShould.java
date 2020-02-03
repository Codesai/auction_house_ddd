package acceptance;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.business.model.auction.Bid;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import static com.codesai.auction_house.business.model.generic.Money.money;
import static com.codesai.auction_house.infrastructure.ActionFactory.auctionRepository;
import static helpers.builder.AuctionBuilder.anAuction;
import static io.restassured.RestAssured.given;
import static matchers.AuctionConqueredMatcher.anAuctionConqueredBy;
import static matchers.BidAssert.assertThatBid;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class ConquerAuctionAPIShould extends ApiTest {

    @Test
    public void
    conquer_an_existing_auction() throws JSONException {
        var givenAuctionId = givingAnExistingAuction().id;

        given().
        when().
            body(new JSONObject()
                .put("user_id", "userThatConquerAuction")
                .put("auction_id", givenAuctionId).toString()).
            post("auction/{id}/conquer", givenAuctionId).
        then().
            assertThat().
            statusCode(200).
            body(equalTo("OK"));

        assertThat(
                auctionRepository().retrieveById(givenAuctionId),
                anAuctionConqueredBy("userThatConquerAuction")
        );
    }

    private Auction givingAnExistingAuction() {
        var expectedAuction = anAuction().build();
        auctionRepository.save(expectedAuction);
        return expectedAuction;
    }
}
