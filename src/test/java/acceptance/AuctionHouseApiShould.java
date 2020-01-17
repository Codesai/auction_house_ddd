package acceptance;

import io.restassured.RestAssured;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.codesai.auction_house.infrastructure.Routing.PORT;
import static com.codesai.auction_house.infrastructure.Routing.Routes;
import static io.restassured.RestAssured.given;
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
                        .put("name", "anyItem")
                        .put("description", "anyDescription")
               )
               .put("price", new JSONObject()
                       .put("init", 19.99)
                       .put("buy", 99.99)
               )
               .put("endDate", LocalDate.now().plusDays(10))
               .toString();

       given().
       when().
            body(auctionJson).
            post("auction").
       then().
            assertThat().
            statusCode(201).
            header("Location", RestAssured.baseURI + "auction/anyItem" );
   }


}
