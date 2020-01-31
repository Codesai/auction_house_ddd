package acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static acceptance.JSONBuilder.createJsonFrom;
import static helpers.builder.AuctionBuilder.anAuction;
import static io.restassured.RestAssured.given;
import static matchers.UrlEndsWithUUIDMatcher.urlEndsWithValidUUID;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;

public class CreateAuctionApiShould extends ApiTest {

    @Test
    public void
    create_a_new_auction() throws Exception {
        given().
                when().
                body(createJsonFrom(anAuction().build()).toString()).
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
                body(createJsonFrom(anAuction().build())
                        .put("initial_bid", "an invalid value")
                        .toString()).
                post("auction").
                then().
                assertThat().
                statusCode(422).
                body(equalTo("The auction body is not well formed."));
    }

}
