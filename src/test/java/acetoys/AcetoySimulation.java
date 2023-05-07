package acetoys;


import acetoys.simulation.TestPopulation;
import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class AcetoySimulation extends Simulation {

    private static final String TEST_TYPE = System.getenv("TEST_TYPE");

  private static final String DOMAIN="acetoys.uk";

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://"+DOMAIN)
    .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-GB,en-US;q=0.9,en;q=0.8");


  {
      if (TEST_TYPE.equals("INSTANT_USERS")){
          setUp(TestPopulation.instantUsers).protocols(httpProtocol);
      }

      else if (TEST_TYPE.equals("RAMP_USERS")){
          setUp(TestPopulation.rampUsers).protocols(httpProtocol);
      }

      else if (TEST_TYPE.equals("COMPLEX_INJECTION")){
          setUp(TestPopulation.complexInjection).protocols(httpProtocol)
                  .assertions(
                          global().responseTime().mean().lt(3),
                          global().successfulRequests().percent().gt(99.0),
                          forAll().responseTime().max().lt(5)
                  );
      }

      else if (TEST_TYPE.equals("CLOSED_MODEL")){
          setUp(TestPopulation.closedModel).protocols(httpProtocol);
      }

      else {
          setUp(TestPopulation.instantUsers).protocols(httpProtocol)
                  .assertions(
                          global().responseTime().mean().lt(3),
                          global().successfulRequests().percent().gt(99.0),
                          forAll().responseTime().max().lt(5)
                  );
      }

  }
}
