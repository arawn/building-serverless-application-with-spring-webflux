package serverless.webapp;

import com.amazonaws.services.lambda.runtime.Context;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.reactive.context.GenericReactiveWebApplicationContext;
import org.springframework.context.ApplicationContext;
import org.springframework.http.server.reactive.HttpHandler;
import serverless.aws.APIGatewayProxyHandler;

import java.util.Collections;

public class LambdaEntryPoint extends APIGatewayProxyHandler {

    @Override
    protected HttpHandler createHttpHandler(Context context) {
        SpringApplication application = new SpringApplication();
        application.addPrimarySources(Collections.singleton(ServerlessWebApplicationConfiguration.class));
        application.setWebApplicationType(WebApplicationType.REACTIVE);
        application.setApplicationContextClass(GenericReactiveWebApplicationContext.class);

        ApplicationContext applicationContext = application.run();

        return applicationContext.getBean(HttpHandler.class);
    }

}