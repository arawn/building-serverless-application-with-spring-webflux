package serverless;

import org.junit.Test;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.function.server.RouterFunctions;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class WebFluxHttpHandlerTest {

    @Test
    public void createHttpHandler() {
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(
            route(path("/"), request -> ok().syncBody("Hello, WebFlux!!!"))
        );
    }

}
