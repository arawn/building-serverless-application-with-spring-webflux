package serverless.webapp.player;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PlayerRepository {

    Flux<Player> findAll();
    Mono<Player> findByName(String name);

    Mono<Player> save(Player player);

}
