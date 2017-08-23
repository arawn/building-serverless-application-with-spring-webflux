package serverless.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import({ ServerlessWebApplicationConfiguration.class, ReactiveWebServerAutoConfiguration.class })
public class LocalEntryPoint {

    public static void main(String[] args) {
        SpringApplication.run(LocalEntryPoint.class, args);
    }

}