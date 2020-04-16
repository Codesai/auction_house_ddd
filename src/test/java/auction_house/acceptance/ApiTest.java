package auction_house.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import static com.codesai.auction_house.infrastructure.delivery_mechanism.Routing.PORT;
import static com.codesai.auction_house.infrastructure.delivery_mechanism.Routing.startApi;
import static spark.Spark.*;

public class ApiTest {
    @BeforeAll
    static void startServer() {
        startApi();
        RestAssured.baseURI = String.format("http://localhost:%s/api/", PORT);
        awaitInitialization();
    }

    @AfterAll
    static void stopServer() {
        stop();
        awaitStop();
    }
}
