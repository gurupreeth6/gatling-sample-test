package acetoys.simulation;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.Choice;
import io.gatling.javaapi.core.ScenarioBuilder;
import org.checkerframework.checker.units.qual.C;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class TestScenario {

    private static final Duration TEST_DURATION = Duration.ofSeconds(Integer.parseInt(System.getProperty("DURATION","60")));
    public static ScenarioBuilder defaultLoadTest =
            scenario("Default Load test")
                    .during(TEST_DURATION)
                    .on(randomSwitch()
                            .on(
                                    Choice.withWeight(60,exec(UserJourney.browseStore)),
                                    Choice.withWeight(30,exec(UserJourney.abandonBasket)),
                                    Choice.withWeight(10,exec(UserJourney.completePurchase))
                            ));

    public static ScenarioBuilder highPurchaseLoadTest =
            scenario("High purchase Load test")
                    .during(TEST_DURATION)
                    .on(randomSwitch()
                            .on(
                                    Choice.withWeight(30,exec(UserJourney.browseStore)),
                                    Choice.withWeight(30,exec(UserJourney.abandonBasket)),
                                    Choice.withWeight(40,exec(UserJourney.completePurchase))
                            ));
}
