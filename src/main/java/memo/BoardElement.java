package memo;

import java.util.Objects;

public class BoardElement {
    private Integer number;
    private BordElementStatus status;

    public BoardElement(Integer number) {
        this.number = number;
        this.status = BordElementStatus.HIDE;
    }

    BoardElement(Integer number, BordElementStatus status) {
        this.number = number;
        this.status = status;
    }

    public Integer getNumber() {
        return number;
    }

    public BordElementStatus getStatus() {
        return status;
    }

    public void setStatus(BordElementStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardElement that = (BoardElement) o;
        return getNumber().equals(that.getNumber()) &&
                getStatus() == that.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumber(), getStatus());
    }

    @Override
    public String toString() {
        return "BoardElement{" +
                "number=" + number +
                ", status=" + status +
                '}';
    }
}
