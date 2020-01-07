package memo;

import org.junit.Assert;
import org.junit.Test;

public class BoardTestSuite {
    @Test

    public void testIsFinishedFalse() {
        //Given
        BoardElement boardElement1 = new BoardElement(1);
        BoardElement boardElement2 = new BoardElement(2);
        BoardElement boardElement3 = new BoardElement(1);
        BoardElement boardElement4 = new BoardElement(2);

        BoardElement[][] boardElements = new BoardElement[2][2];
        boardElements[0][0] = boardElement1;
        boardElements[0][1] = boardElement2;
        boardElements[1][0] = boardElement3;
        boardElements[1][1] = boardElement4;

        Board board = new Board(boardElements);

        //When
        Boolean actualBoard = board.isFinished();

        //Then
        Assert.assertFalse(actualBoard);
    }

    public void testIsFinishedTrue() {
        //Given
        BoardElement boardElement1 = new BoardElement(1, BordElementStatus.SHOWED);
        BoardElement boardElement2 = new BoardElement(2, BordElementStatus.SHOWED);
        BoardElement boardElement3 = new BoardElement(1, BordElementStatus.SHOWED);
        BoardElement boardElement4 = new BoardElement(2, BordElementStatus.SHOWED);

        BoardElement[][] boardElements = new BoardElement[2][2];
        boardElements[0][0] = boardElement1;
        boardElements[0][1] = boardElement2;
        boardElements[1][0] = boardElement3;
        boardElements[1][1] = boardElement4;

        Board board = new Board(boardElements);

        //When
        Boolean actualBoard = board.isFinished();

        //Then
        Assert.assertTrue(actualBoard);
    }
}
