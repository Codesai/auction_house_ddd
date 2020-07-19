package auction_house.acceptance;

import com.google.gson.JsonPrimitive;
import io.restassured.RestAssured;
import org.json.JSONException;
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
            body(ANY_AUCTION_JSON).
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
    not_create_an_auction_when_the_input_is_incorrect() {

        given().
        when().
            body(givenAuctionWithInvalidInitialBidFormat()).
            post("auction").
        then().
            assertThat().
            statusCode(400).
            body(equalTo("The auction body is not well formed."));
    }

    private String givenAuctionWithInvalidInitialBidFormat() {
        var givenNotValidInitialBidAuction = ANY_AUCTION_JSON;
        givenNotValidInitialBidAuction.add("initial_bid",  new JsonPrimitive("an invalid value"));
        return givenNotValidInitialBidAuction.toString();
    }

    @Test public void
    cannot_create_auction_when_inital_bid_is_greater_than_conquer_price() {
        given().
                when().
                    body(givenAuctionWithInitialBidGreaterThanConquerPrice()).
                    post("auction").
                then().
                    assertThat().
                    statusCode(422).
                    body(
                            "name", equalTo("InitialBidIsGreaterThanConquerPrice"),
                            "description", equalTo("initial cannot be greater 10.00 than conquer price 5.00")
                    );
    }

    private String givenAuctionWithInitialBidGreaterThanConquerPrice() {
        var givenAuctionWithInitialBidGreaterThanConquerPrice = ANY_AUCTION_JSON;
        givenAuctionWithInitialBidGreaterThanConquerPrice.add("initial_bid",  new JsonPrimitive(10));
        givenAuctionWithInitialBidGreaterThanConquerPrice.add("conquer_price",  new JsonPrimitive(5));
        return givenAuctionWithInitialBidGreaterThanConquerPrice.toString();
    }

}
