package auction_house.acceptance;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static auction_house.acceptance.JSONParser.createAuctionJsonFrom;
import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class BidAuctionApiShould extends ApiTest {

    public static final String ANY_OWNER_ID = "AnyOwnerId" + UUID.randomUUID().toString();
    public static final String ANY_BIDDER_ID = "AnyUserId" + UUID.randomUUID();
    public static final double ANY_INITIAL_BIDDING_AMOUNT = 50.0;

    @Test public void
    post_to_bid_an_existing_auction() throws JSONException {
        var overBiddingAmount = ANY_INITIAL_BIDDING_AMOUNT + 10;
        String auctionId = givenExistingAuction(ANY_OWNER_ID, ANY_INITIAL_BIDDING_AMOUNT);

        given().
                when().
                body(JSONParser.createBidJsonFrom(auctionId, overBiddingAmount, ANY_BIDDER_ID)).
                post("/auction/{id}/bid", auctionId).
                then().
                assertThat().
                statusCode(201).
                body(equalTo("OK"));

        assertThat(actualBidFrom(auctionId).get("amount").getAsDouble()).isEqualTo(overBiddingAmount);
        assertThat(actualBidFrom(auctionId).get("bidder_id").getAsString()).isEqualTo(ANY_BIDDER_ID);
    }

    @Test public void
    get_an_error_response_when_try_to_create_an_invalid_bid() throws JSONException {
        var givenExistingAuctionId = givenExistingAuction(ANY_BIDDER_ID, ANY_INITIAL_BIDDING_AMOUNT);

        var underBiddenAmount = ANY_INITIAL_BIDDING_AMOUNT - 10;
        given().
            when().
                body(JSONParser.createBidJsonFrom(givenExistingAuctionId, underBiddenAmount, ANY_BIDDER_ID)).
                post("auction/{id}/bid", givenExistingAuctionId).
            then().assertThat().
                statusCode(422).
                contentType("application/json").
                body(
                        "name", equalTo("FirstBidShouldBeGreaterThanStartingPrice"),
                        "description", equalTo(String.format("Initial auction price is %s0 and bid is only %s0", ANY_INITIAL_BIDDING_AMOUNT, underBiddenAmount))
                );
    }

    private JsonObject actualBidFrom(String auctionId) {
        var bids = given().
                when().
                get("auction/{id}", auctionId).
                then().
                assertThat().
                statusCode(200).
                contentType("application/json").
                extract().
                response().
                body().
                jsonPath().
                getList("bids");
        assertThat(bids).hasSize(1);
        return new Gson().fromJson((String) bids.get(0), JsonObject.class);
    }

    private String givenExistingAuction(String ownerId, double initialBidAmount) throws JSONException {
        var location = given().
                when().
                body(createAuctionJsonFrom("AnAuctionName", "An AuctionDescription", initialBidAmount, 100.0, emptyList(), LocalDate.now().plusDays(10), ownerId).toString()).
                post("auction").
                then().
                extract().
                header("Location");

        if (location == null) {
            return "NotSuccessfully created auction";
        }
        return location.split("/")[5];
    }

}
