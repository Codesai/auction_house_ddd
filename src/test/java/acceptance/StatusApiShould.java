package acceptance;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class StatusApiShould extends ApiTest {

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
