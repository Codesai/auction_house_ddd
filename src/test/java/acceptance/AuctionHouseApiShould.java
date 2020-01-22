package acceptance;

import com.codesai.auction_house.infrastructure.ActionFactory;
import com.codesai.auction_house.infrastructure.repository.InMemoryAuctionRepository;
import io.restassured.RestAssured;
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
       String auctionJson = new JSONObject()
               .put("item", new JSONObject()
                        .put("name", "DDD. Tackling complexity in the heart of code. Eric Evans")
                        .put("description", "An insight book to understand how to express our code near to the domain of our business")
               )
               .put("initial_bid", 10.5)
               .put("conquer_price", 50)
               .put("expiration_date", LocalDate.now().plusDays(7))
               .put("minimum_overbidding_price", 1)
               .toString();

       given().
       when().
            body(auctionJson).
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
       var auctionJson = new JSONObject()
               .put("item", new JSONObject()
                        .put("name", ANY_NAME)
                        .put("description", ANY_DESCRIPTION)
               )
               .put("initial_bid", "A invalid input")
               .put("conquer_price", ANY_PRICE)
               .put("expiration_date", ANY_EXPIRATION_DATE)
               .put("minimum_overbidding_price", ANY_MINIMUM_OVERBIDDING_PRICE)
               .toString();
       given().
       when().
            body(auctionJson).
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

       var auctionJson = new JSONObject()
               .put("item", new JSONObject()
                        .put("name", expectedAuction.item.name)
                        .put("description", expectedAuction.item.description)
               )
               .put("initial_bid", expectedAuction.initialBid.amount)
               .put("conquer_price", expectedAuction.conquerPrice.amount)
               .put("expiration_date", expectedAuction.expirationDate.toString())
               .put("minimum_overbidding_price", expectedAuction.minimumOverbiddingPrice.amount);
       given().
       when().
            body(auctionJson).
            get("auction/{id}", expectedAuction.id).
       then().
            assertThat().
            statusCode(200).
            header("Content-type", "application/json").
            body(equalTo(auctionJson.toString()));
   }
}