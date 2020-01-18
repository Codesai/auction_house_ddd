package acceptance;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static matchers.UrlEndsWithUUIDMatcher.urlEndsWithValidUUID;
import static com.codesai.auction_house.infrastructure.Routing.PORT;
import static com.codesai.auction_house.infrastructure.Routing.Routes;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static spark.Spark.awaitInitialization;
import static spark.Spark.awaitStop;

public class AuctionHouseApiShould {
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

    @Test
    public void return_an_okay_when_is_running_on_the_status_route() {
        given().
        when().
            get("status").
        then().
            assertThat().
            statusCode(200).
            body(equalTo("OK"));
    }

   @Test
   public void create_a_new_auction() throws Exception {
       String auctionJson = new JSONObject()
               .put("item", new JSONObject()
                        .put("name", "DDD. Tackling complexity in the heart of code. Eric Evans")
                        .put("description", "An insight book to understand how to express our code near to the domain of our business")
               )
               .put("initial_bid", 10.5)
               .put("conquer_price", 50)
               .put("end_date", "2020/06/24")
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
}


