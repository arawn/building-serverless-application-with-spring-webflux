package serverless.webapp;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.autoconfigure.reactor.core.ReactorCoreAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.accept.RequestedContentTypeResolverBuilder;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import serverless.webapp.player.web.PlayerWebConfiguration;

import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@SpringBootConfiguration
@Import({ JacksonAutoConfiguration.class
        , HttpMessageConvertersAutoConfiguration.class
        , CodecsAutoConfiguration.class
        , ReactorCoreAutoConfiguration.class
        , WebFluxAutoConfiguration.class
        , HttpHandlerAutoConfiguration.class
        , PlayerWebConfiguration.class })
public class ServerlessWebApplicationConfiguration {

    @Bean
    public WebFluxConfigurer webFluxConfigurer() {
        return new WebFluxConfigurer() {
            @Override
            public void configureContentTypeResolver(RequestedContentTypeResolverBuilder builder) {
                builder.fixedResolver(MediaType.APPLICATION_JSON);
            }
        };
    }

    @Bean
    public RouterFunction<ServerResponse> rootHandler() {
        return route(path("/"), request -> ok().syncBody("Hello, WebFlux!!!"));
    }

}