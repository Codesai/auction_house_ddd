package auction_house.helpers.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;

public class UrlEndsWithUUIDMatcher extends UUIDMatcher {
    @Override
    protected boolean matchesSafely(String s) {
        return super.matchesSafely(s.substring(s.lastIndexOf('/') + 1));
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("url that ends with a valid UUID");
    }

    @Factory
    public static Matcher<String> urlEndsWithValidUUID() {
        return new UrlEndsWithUUIDMatcher();
    }
}
