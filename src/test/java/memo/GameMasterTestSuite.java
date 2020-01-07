package memo;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

public class GameMasterTestSuite {

    @Test
    public void testNewGame() throws Exception {
        //Given
        List<Game> games = new LinkedList<>();
        Set<Player> players = new HashSet<Player>();
        GameMaster gameMaster = new GameMaster(games, players);
        Player player = new Player("Tommy");
        DifficultyLevel difficultyLevel = DifficultyLevel.LEVEL1;

        //When
        Game actualGame = gameMaster.newGame(difficultyLevel, player);

        //Then
        Assert.assertTrue(actualGame.getDifficultyLevel().getLevel() <= player.getActualLevel().getLevel());
        Assert.assertEquals(difficultyLevel, actualGame.getDifficultyLevel());
        Assert.assertEquals(player, actualGame.getActivePlayer());
        Assert.assertTrue(actualGame.getPlayers().contains(player));
        Assert.assertTrue(actualGame.getPlayers().contains(gameMaster.getComputerPlayer()));
        Assert.assertEquals(2, actualGame.getPlayers().size());
        Assert.assertEquals(GameStatus.IN_PROGRESS, actualGame.getStatus());
        Assert.assertEquals(Optional.empty(), actualGame.getWinner());
    }

    @Test
    public void testNewBoard() {
        //Given
        List<Game> games = new LinkedList<>();
        Set<Player> players = new HashSet<Player>();
        GameMaster gameMaster = new GameMaster(games, players);

        for (DifficultyLevel level : DifficultyLevel.values()) {
            String message = "Difficulty level = " + level;

            //When
            Board actualBoard = gameMaster.newBoard(level);

            //Then
            Assert.assertEquals(message, level.getBoardX().intValue(), actualBoard.getBoardElements().length);
            Assert.assertEquals(message, level.getBoardY().intValue(), actualBoard.getBoardElements()[0].length);

            int pairAmount = (level.getBoardX() * level.getBoardY()) / 2;
            for (int pairNumber = 1; pairNumber <= pairAmount; pairNumber++) {
                int finalPairNumber = pairNumber;
                long count = Arrays.stream(actualBoard.getBoardElements())
                        .flatMap(value -> Stream.of(value))
                        .filter(value -> value.getNumber().equals(finalPairNumber))
                        .count();
                Assert.assertEquals(message, 2l, count);
            }
        }

    }

    @Test
    public void testAddPlayer() {
        //Given
        List<Game> games = new LinkedList<>();
        Set<Player> players = new HashSet<Player>();
        GameMaster gameMaster = new GameMaster(games, players);

        //When
        Player actualPlayer = gameMaster.addPlayer("player1");

        //Then
        Assert.assertTrue(players.contains(actualPlayer));
        Assert.assertEquals(1, players.size());
    }

    @Test
    public void testSelectPlayerIsPresent() {
        //Given
        List<Game> games = new LinkedList<>();
        Set<Player> players = new HashSet<Player>();
        GameMaster gameMaster = new GameMaster(games, players);
        Player player = new Player("player1");
        players.add(player);

        //When
        Optional<Player> actualPlayer = gameMaster.selectPlayer("player1");

        //Then
        Assert.assertTrue(actualPlayer.isPresent());
        Assert.assertEquals(player, actualPlayer.get());
    }

    @Test
    public void testSelectPlayerIsEmpty() {
        //Given
        List<Game> games = new LinkedList<>();
        Set<Player> players = new HashSet<Player>();
        GameMaster gameMaster = new GameMaster(games, players);


        //When
        Optional<Player> actualPlayer = gameMaster.selectPlayer("player1");

        //Then
        Assert.assertFalse(actualPlayer.isPresent());
    }

    @Test
    public void testGetRanking() {
        //Given
        List<Game> games = new LinkedList<>();
        Set<Player> playersAll = new HashSet<Player>();
        GameMaster gameMaster = new GameMaster(games, playersAll);
        List<Player> game1Players = new ArrayList<>();
        List<Player> game2Players = new ArrayList<Player>();
        List<Player> game3Players = new ArrayList<Player>();
        List<Integer> matchedPairs1 = new ArrayList<>();
        List<Integer> matchedPairs2 = new ArrayList<>();
        List<Integer> matchedPairs3 = new ArrayList<>();

        Player player1 = new Player("Leonard");
        Player player2 = new Player("Sheldon");
        Player player3 = new Player("Raj");

        matchedPairs1.add(0, 3);
        matchedPairs1.add(1, 1);

        matchedPairs2.add(0, 4);
        matchedPairs2.add(1, 2);

        playersAll.add(player1);
        playersAll.add(player2);
        playersAll.add(player3);

        game1Players.add(player1);
        game1Players.add(player2);

        game2Players.add(player1);
        game2Players.add(player2);

        game3Players.add(player1);
        game3Players.add(player2);
        game3Players.add(player3);

        Game game1 = new Game(game1Players, matchedPairs1, DifficultyLevel.LEVEL1, GameStatus.COMPLETED);
        Game game2 = new Game(game2Players, matchedPairs2, DifficultyLevel.LEVEL2, GameStatus.COMPLETED);
        Game game3 = new Game(game3Players, matchedPairs3, DifficultyLevel.LEVEL3, GameStatus.IN_PROGRESS);

        games.add(game1);
        games.add(game2);
        games.add(game3);

        //When
        List<PlayerScore> actual = gameMaster.getRanking();

        //Then
        Assert.assertEquals(Integer.valueOf(11), actual.get(0).getPoints());
        Assert.assertEquals(Integer.valueOf(5), actual.get(1).getPoints());
        Assert.assertEquals(Integer.valueOf(0), actual.get(2).getPoints());
        Assert.assertEquals(player1, actual.get(0).getPlayer());
        Assert.assertEquals(player2, actual.get(1).getPlayer());
        Assert.assertEquals(player3, actual.get(2).getPlayer());
    }
}
