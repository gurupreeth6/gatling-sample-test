package acetoys.pageobejcts;

import io.gatling.javaapi.core.ChainBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
public class StaticPages {
    public static ChainBuilder homepage =
            exec(
                    http("Load Home page")
                            .get("/")
                            .check(status().is(200), status().not(400),status().not(404))
                            .check(substring("<title>Ace Toys Online Shop</title>"))
                            .check(css("#_csrf","content").saveAs("csrfToken"))
            );

    public static ChainBuilder ourStory =
            exec(
                    http("Load our story page")
                            .get("/our-story")
                            .check(regex("was founded online in \\d{4}"))
            );

    public static ChainBuilder getInTouch =
            exec(
                    http("Load Get in touch page")
                            .get("/get-in-touch")
                            .check(substring("as we are not actually a real store!"))
            );

}
