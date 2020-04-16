package auction_house.acceptance;

import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static auction_house.acceptance.JSONParser.createAuctionJsonFrom;
import static auction_house.helpers.matchers.UrlEndsWithUUIDMatcher.urlEndsWithValidUUID;
import static io.restassured.RestAssured.given;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;

public class ConquerAuctionAPIShould extends ApiTest {
    String ANY_OWNER_ID = "AnyOwnerId" + UUID.randomUUID().toString();
    double ANY_INITIAL_BIDDING_AMOUNT = 50.0;

    @Test
    public void
    conquer_an_existing_auction() throws JSONException {
        var givenAuctionId = givenExistingAuction(ANY_OWNER_ID, ANY_INITIAL_BIDDING_AMOUNT);

        given().
        when().
            body(new JSONObject()
                .put("conqueror_id", "userThatConquerAuction").toString()).
            post("auction/{id}/conquer", givenAuctionId).
        then().
            assertThat().
            statusCode(200).
            body(equalTo("OK"));
    }

    @Test public void
    cannot_conquer_an_already_closed_auction() throws Exception {
        var givenAuctionId = givenExistingAuction(ANY_OWNER_ID, ANY_INITIAL_BIDDING_AMOUNT);

        given().
                when().
                body(new JSONObject()
                        .put("conqueror_id", "userThatConquerAuction").toString()).
                post("auction/{id}/conquer", givenAuctionId).
                then().
                assertThat().
                statusCode(200).
                body(equalTo("OK"));

        given().
        when().
            body(new JSONObject().put("conqueror_id", "userThatConquerAuction").toString()).
            post("auction/{auction_id}/conquer", givenAuctionId).
        then().
            assertThat().
            statusCode(422).
            body(
                    "name",equalTo("CannotConquerAClosedAuctionException"),
                    "description", equalTo("Cannot conquer a closed auction")
            );
    }

    private String givenExistingAuction(String ownerId, double initialBidAmount) throws JSONException {
        var location = given().
                when().
                body(createAuctionJsonFrom("AnAuctionName", "An AuctionDescription", initialBidAmount, 100.0, emptyList(), LocalDate.now().plusDays(10), ownerId).toString()).
                post("auction").
                then().
                assertThat().
                statusCode(201).
                header("Location", allOf(
                        startsWith(RestAssured.baseURI + "auction/"),
                        urlEndsWithValidUUID()
                )).
                extract().
                header("Location");

        return location.split("/")[5];
    }

}
