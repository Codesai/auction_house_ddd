package auction_house.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codesai.auction_house.infrastructure.delivery_mechanism.Routing.PORT;
import static com.codesai.auction_house.infrastructure.delivery_mechanism.Routing.startApi;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static spark.Spark.*;

public class StatusApiShould {
    @BeforeAll
    static void startServer() {
        startApi();
        RestAssured.baseURI = String.format("http://localhost:%s/", PORT);
        awaitInitialization();
    }

    @AfterAll
    static void stopServer() {
        stop();
        awaitStop();
    }

    @Test
    public void
    return_an_okay_when_is_running_on_the_status_route() {
        given().
                when().
                get("status").
                then().
                assertThat().
                statusCode(200).
                body(equalTo("OK"));
    }

}
