package serverless.aws;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import serverless.aws.model.APIGatewayProxyRequest;
import serverless.aws.model.APIGatewayProxyResponse;
import serverless.aws.springframework.http.server.reactive.SimpleAPIGatewayProxyServerHttpRequest;
import serverless.aws.springframework.http.server.reactive.SimpleAPIGatewayProxyServerHttpResponse;

public abstract class APIGatewayProxyHandler implements RequestHandler<APIGatewayProxyRequest, APIGatewayProxyResponse> {

    private Logger logger = LoggerFactory.getLogger(APIGatewayProxyHandler.class);

    @Override
    public APIGatewayProxyResponse handleRequest(APIGatewayProxyRequest proxyRequest, Context context) {
        logger.info("proxyRequest: {}, context: {}", proxyRequest, context);

        APIGatewayProxyResponse proxyResponse = new APIGatewayProxyResponse();

        HttpHandler httpHandler = createHttpHandler(context);
        logger.info("HttpHandler created: {}", httpHandler);

        ServerHttpRequest request = SimpleAPIGatewayProxyServerHttpRequest.of(proxyRequest);
        ServerHttpResponse response = SimpleAPIGatewayProxyServerHttpResponse.of(proxyResponse);

        httpHandler.handle(request, response).block();
        logger.info("proxyResponse: {}", proxyResponse);

        return proxyResponse;
    }


    protected abstract HttpHandler createHttpHandler(Context context);

}
