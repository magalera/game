package memo;

import java.util.Objects;

public class BoardElementPosition {
    private int positionX;
    private int positionY;

    public BoardElementPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardElementPosition that = (BoardElementPosition) o;
        return getPositionX() == that.getPositionX() &&
                getPositionY() == that.getPositionY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPositionX(), getPositionY());
    }

    @Override
    public String toString() {
        return "BoardElementPosition{" +
                "positionX=" + positionX +
                ", positionY=" + positionY +
                '}';
    }
}
