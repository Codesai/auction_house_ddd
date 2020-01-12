package acceptance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
            get(String.format("http://localhost:%s/status", PORT)).
        then().
            assertThat().
            statusCode(200).
            body(equalTo("OK"));
    }
}
