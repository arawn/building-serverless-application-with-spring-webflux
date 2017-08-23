package serverless.webapp.player.web;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.reactive.server.WebTestClient;
import serverless.webapp.player.Player;

public class PlayerWebConfigurationTest {

    PlayerWebConfiguration configuration;
    WebTestClient webClient;

    @Before
    public void setUp() {
        this.configuration = new PlayerWebConfiguration();
        this.webClient = WebTestClient.bindToRouterFunction(configuration.playerRouteFunctions(configuration.playerRepository()))
                                      .build();
    }

    @Test
    public void allPlayers() {
        webClient.get().uri("/player")
                 .exchange()
                 .expectStatus().isOk()
                 .expectBodyList(Player.class)
                 .hasSize(3);
    }

    @Test
    public void findPlayer() {
        Player target = configuration.playerRepository().findByName("arawn").block();

        webClient.get().uri("/player/{name}", target.getName())
                 .exchange()
                 .expectStatus().isOk()
                 .expectBody(Player.class)
                 .isEqualTo(target);
    }

}