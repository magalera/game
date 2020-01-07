package memo;

import java.util.Objects;

public class PlayerScore {
    private Player player;
    private Integer points;

    public PlayerScore(Player player, Integer points) {
        this.player = player;
        this.points = points;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getPoints() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerScore that = (PlayerScore) o;
        return getPlayer().equals(that.getPlayer()) &&
                getPoints().equals(that.getPoints());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPlayer(), getPoints());
    }

    @Override
    public String toString() {
        return "PlayerScore{" +
                "player=" + player +
                ", points=" + points +
                '}';
    }
}
