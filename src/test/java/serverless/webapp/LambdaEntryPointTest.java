package serverless.webapp;

import org.junit.Test;
import org.springframework.http.server.reactive.HttpHandler;

import static org.junit.Assert.*;

public class LambdaEntryPointTest {

    @Test
    public void lambdaEntryPoint() {
        LambdaEntryPoint lambdaEntryPoint = new LambdaEntryPoint();
        HttpHandler httpHandler = lambdaEntryPoint.createHttpHandler(null);
    }

}