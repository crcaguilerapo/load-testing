package org.crcaguilerapo;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;


import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.core.CoreDsl.*;

public class LoadTest extends Simulation {

    public LoadTest() {
        HttpProtocolBuilder httpProtocol = http
                .baseUrl("http://localhost:8080");

        ScenarioBuilder getScenario = scenario("Basic Simulation")
                .exec(http("get_messages")
                        .get("/messages"));

        setUp(
                getScenario.injectOpen(atOnceUsers(10)).protocols(httpProtocol),
        );
    }

}