package serverless.webapp;

import com.amazonaws.services.lambda.runtime.Context;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.web.reactive.function.server.RouterFunctions;
import serverless.aws.APIGatewayProxyHandler;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

public class SimpleLambdaEntryPoint extends APIGatewayProxyHandler {

    @Override
    protected HttpHandler createHttpHandler(Context context) {
        return RouterFunctions.toHttpHandler(
            route(path("/"), request -> ok().syncBody("Hello, WebFlux!!!"))
        );
    }

}