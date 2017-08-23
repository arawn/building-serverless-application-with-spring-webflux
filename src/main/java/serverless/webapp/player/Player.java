package serverless.webapp.player;

import java.time.LocalDateTime;
import java.util.Objects;

public class Player {

    private String name;
    private PlayerLevel level;
    private LocalDateTime joined;

    private Player() { }

    public Player(String name, PlayerLevel level) {
        this.name = Objects.requireNonNull(name);
        this.level = Objects.requireNonNull(level);
        this.joined = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public PlayerLevel getLevel() {
        return level;
    }

    public LocalDateTime getJoined() {
        return joined;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return name.equals(player.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "Player {" +
                "name='" + name + '\'' +
                ", level=" + level +
                ", joined=" + joined +
                '}';
    }

}
