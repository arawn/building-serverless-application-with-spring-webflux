package serverless.webapp.player.application;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import serverless.webapp.player.Player;
import serverless.webapp.player.PlayerLevel;
import serverless.webapp.player.PlayerRepository;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public class InMemoryPlayerRepository implements PlayerRepository {

    private List<Player> players = new CopyOnWriteArrayList<>();

    {{
        players.add(new Player("arawn", PlayerLevel.BEGINNER));
        players.add(new Player("outsider", PlayerLevel.INTERMEDIATE));
        players.add(new Player("fupfin", PlayerLevel.EXPERT));
    }}

    @Override
    public Flux<Player> findAll() {
        return Flux.fromArray(players.stream().toArray(Player[]::new));
    }

    @Override
    public Mono<Player> findByName(String name) {
        if (Objects.isNull(name)) {
            return Mono.error(new PlayerNameNotFoundException(name));
        }

        return players.stream()
                      .filter(player -> player.getName().toLowerCase().equals(name.toLowerCase()))
                      .findAny()
                      .map(Mono::just)
                      .orElseGet(() -> Mono.error(new PlayerNameNotFoundException(name)));
    }

    @Override
    public Mono<Player> save(Player player) {
        return Mono.just(players.stream()
                                .filter(Predicate.isEqual(player))
                                .findAny()
                                .orElseGet(() -> {
                                    Player newPlayer = new Player(player.getName(), player.getLevel());
                                    players.add(newPlayer);
                                    return newPlayer;
                                }));
    }

}
