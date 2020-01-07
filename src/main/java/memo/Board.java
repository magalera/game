package memo;

import java.util.Arrays;
import java.util.stream.Stream;

public class Board {
    private BoardElement boardElements[][];

    public Board(BoardElement[][] boardElements) {
        this.boardElements = boardElements;
    }

    public BoardElement[][] getBoardElements() {
        return boardElements;
    }

    BoardElement getBoardElement (BoardElementPosition boardElementPosition){
        return boardElements[boardElementPosition.getPositionX()][boardElementPosition.getPositionY()];
    }

    void setElement(BoardElement boardElement, int x, int y) {
        this.boardElements[x][y] = boardElement;
    }

    public void setElementStatus(BoardElementPosition position, BordElementStatus status) {
        boardElements[position.getPositionX()][position.getPositionY()].setStatus(status);
    }

    public boolean isFinished() {
        return Arrays.stream(boardElements)
                .flatMap(value -> Stream.of(value))
                .allMatch(value -> value.getStatus().equals(BordElementStatus.SHOWED));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board board = (Board) o;
        return Arrays.equals(getBoardElements(), board.getBoardElements());
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(getBoardElements());
    }

    @Override
    public String toString() {
        return "Board{" +
                "boardElements=" + Arrays.toString(boardElements) +
                '}';
    }
}
