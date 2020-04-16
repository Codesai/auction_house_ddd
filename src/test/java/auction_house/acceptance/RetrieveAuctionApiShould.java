package auction_house.acceptance;

import io.restassured.RestAssured;
import org.json.JSONException;
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

public class RetrieveAuctionApiShould extends ApiTest {
    String ANY_OWNER_ID = "AnyOwnerId" + UUID.randomUUID().toString();
    double ANY_INITIAL_BIDDING_AMOUNT = 50.0;

    private String givenExistingAuction(String body) throws JSONException {
        var location = given().
                when().
                body(body).
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

    @Test
    public void
    get_an_auction_by_its_id() throws Exception {
        var expectedAuctionBody = createAuctionJsonFrom("AnAuctionName", "An AuctionDescription", ANY_INITIAL_BIDDING_AMOUNT, 100.0, emptyList(), LocalDate.now().plusDays(10), ANY_OWNER_ID).toString();
        var givenAuctionId = givenExistingAuction(expectedAuctionBody);

        given().
                when().
                    get("auction/{id}", givenAuctionId).
                then().
                    assertThat().
                    statusCode(200).
                    header("Content-type", "application/json").
                    body(equalTo(expectedAuctionBody));
    }

    @Test
    public void
    get_a_404_when_the_auction_does_not_exists() {
        given().
                when().
                    get("auction/{id}", "non-existing-auction-id").
                then().
                    assertThat().
                    statusCode(404).
                    body(equalTo("An auction with that id does not exists."));
    }

}