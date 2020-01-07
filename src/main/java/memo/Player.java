package memo;

import java.util.Objects;

public class Player {
    private String name;
    private DifficultyLevel actualLevel;

    public Player(String name) {
        this.name = name;
        this.actualLevel = DifficultyLevel.LEVEL1;
    }

    public String getName() {
        return name;
    }

    public DifficultyLevel getActualLevel() {
        return actualLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return getName().equals(player.getName()) &&
                getActualLevel() == player.getActualLevel();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getActualLevel());
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", actualLevel=" + actualLevel + '}';
    }
}
