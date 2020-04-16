package auction_house.acceptance;

import io.restassured.RestAssured;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import static auction_house.acceptance.JSONParser.createAuctionJsonFrom;
import static auction_house.helpers.builder.AuctionBuilder.anAuction;
import static auction_house.helpers.matchers.UrlEndsWithUUIDMatcher.urlEndsWithValidUUID;
import static io.restassured.RestAssured.given;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateAuctionApiShould extends ApiTest {

    @Test
    public void
    create_a_new_auction() throws Exception {
        given().
        when().
            body(createAuctionJsonFrom(anAuction().build().item.name, anAuction().build().item.description, anAuction().build().startingPrice.amount, anAuction().build().conquerPrice.amount, anAuction().build().bids.stream().map(bid -> JSONParser.createBidJsonFrom(bid.id, bid.money.amount, bid.bidderId.id)).collect(toList()), anAuction().build().expirationDate, anAuction().build().ownerId.id).toString()).
            post("auction").
        then().
            assertThat().
            statusCode(201).
            header("Location", allOf(
                startsWith(RestAssured.baseURI + "auction/"),
                urlEndsWithValidUUID()
            ));
    }

    @Test
    public void
    not_create_an_auction_when_the_input_is_incorrect() throws Exception {
        given().
        when().
            body(createAuctionJsonFrom(anAuction().build().item.name, anAuction().build().item.description, anAuction().build().startingPrice.amount, anAuction().build().conquerPrice.amount, anAuction().build().bids.stream().map(bid -> JSONParser.createBidJsonFrom(bid.id, bid.money.amount, bid.bidderId.id)).collect(toList()), anAuction().build().expirationDate, anAuction().build().ownerId.id)
                .put("initial_bid", "an invalid value")
                .toString()).
            post("auction").
        then().
            assertThat().
            statusCode(400).
            body(equalTo("The auction body is not well formed."));
    }

    @Test public void
    cannot_create_auction_when_inital_bid_is_greater_than_conquer_price() throws JSONException {
        given().
                when().
                    body(createAuctionJsonFrom(anAuction().build().item.name, anAuction().build().item.description, anAuction().build().startingPrice.amount, anAuction().build().conquerPrice.amount, anAuction().build().bids.stream().map(bid -> JSONParser.createBidJsonFrom(bid.id, bid.money.amount, bid.bidderId.id)).collect(toList()), anAuction().build().expirationDate, anAuction().build().ownerId.id)
                            .put("initial_bid", 10)
                            .put("conquer_price", 5)
                            .toString()).
                    post("auction").
                then().
                    assertThat().
                    statusCode(422).
                    body(
                            "name", equalTo("InitialBidIsGreaterThanConquerPrice"),
                            "description", equalTo("initial cannot be greater 10.00 than conquer price 5.00")
                    );
    }

}
