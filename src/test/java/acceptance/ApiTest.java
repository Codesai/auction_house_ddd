package acceptance;

import com.codesai.auction_house.infrastructure.repository.InMemoryAuctionRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codesai.auction_house.infrastructure.ActionFactory.auctionRepository;
import static com.codesai.auction_house.infrastructure.Routing.PORT;
import static com.codesai.auction_house.infrastructure.Routing.Routes;
import static spark.Spark.*;

public class ApiTest {
    final InMemoryAuctionRepository auctionRepository = auctionRepository();

    @BeforeAll
    static void startServer() {
        Routes();
        RestAssured.baseURI = String.format("http://localhost:%s/api/", PORT);
        awaitInitialization();
    }

    @AfterAll
    static void stopServer() {
        stop();
        awaitStop();
    }

    @BeforeEach
    public void setUp() {
        auctionRepository.clean();
    }
}
