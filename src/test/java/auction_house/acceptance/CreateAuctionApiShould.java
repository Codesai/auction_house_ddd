package auction_house.acceptance;

import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static auction_house.acceptance.JSONParser.createAuctionJsonFrom;
import static auction_house.helpers.matchers.UrlEndsWithUUIDMatcher.urlEndsWithValidUUID;
import static io.restassured.RestAssured.given;
import static java.time.LocalDate.now;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateAuctionApiShould extends ApiTest {

    public CreateAuctionApiShould() throws JSONException {
    }

    public static final String ANY_ITEM_NAME = "An Item" + UUID.randomUUID();
    public static final String ANY_DESCRIPTION = "AnyDescription" + UUID.randomUUID();
    public static final double ANY_INITIAL_BID_AMOUNT = 50.0;
    public static final double ANY_CONQUER_PRICE_AMOUNT = 100.0;
    public static final List<String> ANY_BIDS = emptyList();
    public static final LocalDate ANY_EXPIRATION_DAY = now();
    public static final String ANY_OWNER_ID = "AnyOwnerId" + UUID.randomUUID();

    private final JSONObject ANY_AUCTION_JSON = createAuctionJsonFrom(ANY_ITEM_NAME, ANY_DESCRIPTION, ANY_INITIAL_BID_AMOUNT, ANY_CONQUER_PRICE_AMOUNT, ANY_BIDS, ANY_EXPIRATION_DAY, ANY_OWNER_ID);

    @Test
    public void
    create_a_new_auction() {
        given().
        when().
            body(ANY_AUCTION_JSON.toString()).
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
            body(ANY_AUCTION_JSON
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
                    body(ANY_AUCTION_JSON
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
