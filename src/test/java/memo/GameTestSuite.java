package memo;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameTestSuite {

    private Game newGame() {
        DifficultyLevel difficultyLevel = DifficultyLevel.LEVEL1;
        BoardElement[][] elements = new BoardElement[difficultyLevel.getBoardX()][difficultyLevel.getBoardY()];
        Board board = new Board(elements);
        BoardElement e1 = new BoardElement(1);
        BoardElement e2 = new BoardElement(2);
        BoardElement e3 = new BoardElement(3);
        BoardElement e4 = new BoardElement(4);

        elements[0][0] = e1;
        elements[2][0] = e1;
        elements[0][1] = e2;
        elements[2][1] = e2;
        elements[1][0] = e3;
        elements[3][0] = e3;
        elements[1][1] = e4;
        elements[3][1] = e4;

        List<Player> players = new ArrayList<>();
        Player player1 = new Player("Bruno");
        Player player2 = new Player("Tomson");
        players.add(player1);
        players.add(player2);

        Game game = new Game(board, players, difficultyLevel, player1);

        return game;
    }

    @Test
    public void testSelectBoardElementValidPosition() throws Exception {
        //Given
        Game game = newGame();
        BoardElementPosition boardElementPosition = new BoardElementPosition(0, 0);

        //When
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition);

        //Then
        // no exception
    }

    @Test(expected = Exception.class)
    public void testSelectBoardElementToLargePosition() throws Exception {
        //Given
        Game game = newGame();
        BoardElementPosition boardElementPosition = new BoardElementPosition(5, 5);

        //When
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition);

        //Then
        // throw exception
    }

    @Test(expected = Exception.class)
    public void testSelectBoardElementMinusPosition() throws Exception {
        //Given
        Game game = newGame();
        BoardElementPosition boardElementPosition = new BoardElementPosition(-1, -4);

        //When
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition);

        //Then
        // throw exception
    }

    @Test(expected = Exception.class)
    public void testSelectBoardElementInvalidActivePlayer() throws Exception {
        //Given
        Game game = newGame();
        BoardElementPosition boardElementPosition = new BoardElementPosition(0, 0);
        Player player = new Player("Oscar");

        //When
        game.selectBoardElement(player, boardElementPosition);

        //Then
        // no exception
    }

    @Test
    public void testSelectOneElement() throws Exception {
        //Given
        Game game = newGame();
        BoardElementPosition boardElementPosition = new BoardElementPosition(0, 0);

        //When
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition);

        //Then
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition).getStatus(), BordElementStatus.SELECTED);
        Assert.assertEquals(game.getLastSelectedBoardElement(), Optional.of(boardElementPosition));
        Assert.assertTrue(game.getLastSelectedBoardElement().isPresent());
        Assert.assertEquals(game.getLastSelectedBoardElement().get(), boardElementPosition);
    }

    @Test
    public void testSelectPair() throws Exception {
        //Given
        Game game = newGame();
        BoardElementPosition boardElementPosition1 = new BoardElementPosition(0, 0);
        BoardElementPosition boardElementPosition2 = new BoardElementPosition(2, 0);

        //When
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition1);
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition2);

        //Then
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition1).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition2).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getMatchedPairs().get(game.getPlayers().indexOf(game.getActivePlayer())), Integer.valueOf(1));
        Assert.assertEquals(game.getActivePlayer().getName(), "Bruno");
        Assert.assertFalse(game.getLastSelectedBoardElement().isPresent());
    }

    @Test
    public void testSelectNotPair() throws Exception {
        //Given
        Game game = newGame();
        BoardElementPosition boardElementPosition1 = new BoardElementPosition(0, 0);
        BoardElementPosition boardElementPosition2 = new BoardElementPosition(1, 0);

        //When
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition1);
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition2);

        //Then
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition1).getStatus(), BordElementStatus.HIDE);
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition2).getStatus(), BordElementStatus.HIDE);
        Assert.assertEquals(game.getActivePlayer().getName(), "Tomson");
        Assert.assertFalse(game.getLastSelectedBoardElement().isPresent());
    }

    @Test
    public void testGameCompleted() throws Exception {
        //Given
        Game game = newGame();
        BoardElementPosition boardElementPosition1 = new BoardElementPosition(0, 0);
        BoardElementPosition boardElementPosition2 = new BoardElementPosition(2, 0);
        BoardElementPosition boardElementPosition3 = new BoardElementPosition(0, 1);
        BoardElementPosition boardElementPosition4 = new BoardElementPosition(2, 1);
        BoardElementPosition boardElementPosition5 = new BoardElementPosition(1, 0);
        BoardElementPosition boardElementPosition6 = new BoardElementPosition(3, 0);
        BoardElementPosition boardElementPosition7 = new BoardElementPosition(1, 1);
        BoardElementPosition boardElementPosition8 = new BoardElementPosition(3, 1);

        //When
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition1);
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition2);
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition3);
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition4);
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition5);
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition6);
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition7);
        game.selectBoardElement(game.getActivePlayer(), boardElementPosition8);

        //Then
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition1).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition2).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition3).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition4).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition5).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition6).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition7).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getBoard().getBoardElement(boardElementPosition8).getStatus(), BordElementStatus.SHOWED);
        Assert.assertEquals(game.getMatchedPairs().get(game.getPlayers().indexOf(game.getActivePlayer())), Integer.valueOf(4));
        Assert.assertEquals(game.getActivePlayer().getName(), "Bruno");
        Assert.assertFalse(game.getLastSelectedBoardElement().isPresent());
        Assert.assertEquals(game.getStatus(), GameStatus.COMPLETED);
    }
}
