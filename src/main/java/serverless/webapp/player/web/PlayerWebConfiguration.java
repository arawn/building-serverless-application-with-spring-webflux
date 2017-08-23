package serverless.webapp.player.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import serverless.webapp.player.Player;
import serverless.webapp.player.PlayerRepository;
import serverless.webapp.player.application.InMemoryPlayerRepository;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Configuration
public class PlayerWebConfiguration {

    @Bean
    public PlayerRepository playerRepository() {
        return new InMemoryPlayerRepository();
    }

    @Bean
    public RouterFunction<ServerResponse> playerRouteFunctions(PlayerRepository playerRepository) {
        return nest(path("/player"),
            route(GET("/"), this::allPlayers).
            andRoute(GET("/{name}"), this::findPlayer).
            andRoute(POST("/"), this::addPlayer)
        );
    }

    Mono<ServerResponse> allPlayers(ServerRequest request) {
        return ok().body(playerRepository().findAll(), Player.class);
    }

    Mono<ServerResponse> findPlayer(ServerRequest request) {
        return ok().body(playerRepository().findByName(request.pathVariable("name")), Player.class);
    }

    Mono<ServerResponse> addPlayer(ServerRequest request) {
        return request.bodyToMono(Player.class)
                      .flatMap(playerRepository()::save)
                      .flatMap(player -> ok().body(BodyInserters.fromObject(player)));
    }

}
