package acceptance;

import com.codesai.auction_house.business.model.auction.Auction;
import com.codesai.auction_house.infrastructure.ActionFactory;
import com.codesai.auction_house.infrastructure.repository.InMemoryAuctionRepository;
import io.restassured.RestAssured;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.codesai.auction_house.infrastructure.Routing.PORT;
import static com.codesai.auction_house.infrastructure.Routing.Routes;
import static helpers.builder.AuctionBuilder.anAuction;
import static io.restassured.RestAssured.given;
import static matchers.UrlEndsWithUUIDMatcher.urlEndsWithValidUUID;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.IsEqual.equalTo;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;

public class AuctionHouseApiShould {
    private static final String ANY_NAME = "Any name";
    private static final String ANY_DESCRIPTION = "Any description";
    private static final double ANY_PRICE = 10.5;
    private static final LocalDate ANY_EXPIRATION_DATE = LocalDate.now().plusDays(7);
    private static final double ANY_MINIMUM_OVERBIDDING_PRICE = 1;
    private final InMemoryAuctionRepository auctionRepository = ActionFactory.auctionRepository();

    @BeforeEach
    public void setUp() {
        auctionRepository.clean();
    }

    @BeforeAll
    static void startServer() {
        Routes();
        RestAssured.baseURI = String.format("http://localhost:%s/api/", PORT);
        awaitInitialization();
    }

    @AfterAll
    static void stopServer() {
        awaitStop();
    }

    @Test public void
    return_an_okay_when_is_running_on_the_status_route() {
        given().
        when().
            get("status").
        then().
            assertThat().
            statusCode(200).
            body(equalTo("OK"));
    }

   @Test public void
   create_a_new_auction() throws Exception {
       given().
       when().
            body(createJsonFrom(anAuction().build()).toString()).
            post("auction").
       then().
            assertThat().
            statusCode(201).
            contentType("application/json").
            header("Location", allOf(
                    startsWith(RestAssured.baseURI + "auction/"),
                    urlEndsWithValidUUID()
            ));
   }

   @Test public void
   should_not_create_an_auction_when_the_input_is_incorrect() throws Exception {

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

   @Test public void
   should_get_an_auction_by_its_id() throws Exception {
       var expectedAuction = anAuction().build();
       this.auctionRepository.save(expectedAuction);
       var auctionJson = createJsonFrom(expectedAuction);

       given().
       when().
            get("auction/{id}", expectedAuction.id).
       then().
            assertThat().
            statusCode(200).
            header("Content-type", "application/json").
            body(equalTo(auctionJson.toString()));
   }

   @Test public void
   should_get_a_404_when_the_auction_does_not_exists() {
       given().
       when().
            get("auction/{id}", "non-existing-auction-id").
       then().
            assertThat().
            statusCode(404).
            body(equalTo("An auction with that id does not exists."));
   }

    private JSONObject createJsonFrom(Auction expectedAuction) throws JSONException {
        return new JSONObject()
                .put("item", new JSONObject()
                         .put("name", expectedAuction.item.name)
                         .put("description", expectedAuction.item.description)
                )
                .put("initial_bid", expectedAuction.initialBid.amount)
                .put("conquer_price", expectedAuction.conquerPrice.amount)
                .put("expiration_date", expectedAuction.expirationDate.toString())
                .put("minimum_overbidding_price", expectedAuction.minimumOverbiddingPrice.amount);
    }
}