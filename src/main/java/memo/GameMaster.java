package memo;

import java.util.*;
import java.util.stream.Collectors;

public class GameMaster {
    private List<Game> games;
    private Set<Player> players;
    private Random generator;
    private ComputerPlayer computerPlayer;

    public GameMaster(List<Game> games, Set<Player> players) {
        this.games = games;
        this.players = players;
        this.generator = new Random();
        this.computerPlayer = new ComputerPlayer("computer");
    }

    public Game newGame(DifficultyLevel difficultyLevel, Player player) throws Exception {
        player = addOrSelectPlayer(player.getName());
        if (difficultyLevel.getLevel() > player.getActualLevel().getLevel()) {
            throw new Exception((player + ", this level is unable. Please, select access level, not higher than " + player.getActualLevel().getLevel()));
        }

        List<Player> gamePlayers = new ArrayList<>();
        gamePlayers.add(player);
        gamePlayers.add(computerPlayer);

        Game game = new Game(newBoard(difficultyLevel), gamePlayers, difficultyLevel, player);
        this.games.add(game);
        return game;
    }

    public Board newBoard(DifficultyLevel difficultyLevel) {
        BoardElement[][] elements;
        int pairAmount = (difficultyLevel.getBoardX() * difficultyLevel.getBoardY()) / 2;
        Board board = new Board(new BoardElement[difficultyLevel.getBoardX()][difficultyLevel.getBoardY()]);

        for (int pairNumber = 1; pairNumber <= pairAmount; pairNumber++) {
            insertBoardElement(board, pairNumber, difficultyLevel);
            insertBoardElement(board, pairNumber, difficultyLevel);
        }
        return board;
    }

    private void insertBoardElement(Board board, int pairNumber, DifficultyLevel difficultyLevel) {
        while (true) {
            int x = generator.nextInt(difficultyLevel.getBoardX());
            int y = generator.nextInt(difficultyLevel.getBoardY());
            if (board.getBoardElements()[x][y] == null) {
                board.setElement(new BoardElement(pairNumber), x, y);
                return;
            }
        }
    }

    public Player addOrSelectPlayer(String name) {
        Optional<Player> player = selectPlayer(name);
        if (player.isPresent()) {
            return player.get();
        }
        return this.addPlayer(name);
    }

    public Optional<Player> selectPlayer(String name) {
        return players.stream()
                .filter(element -> element.getName().equals(name))
                .findFirst();
    }

    public Player addPlayer(String name) {
        Player player = new Player(name);
        players.add(player);
        return player;
    }

    ComputerPlayer getComputerPlayer() {
        return computerPlayer;
    }

    public List<PlayerScore> getRanking() {
        List<Game> completedGames = this.games.stream()
                .filter(game -> game.getStatus() == GameStatus.COMPLETED)
                .collect(Collectors.toList());

        List<PlayerScore> playerScores = new ArrayList<>(players.size());

        for (Player player : players) {
            int sum = completedGames.stream()
                    .filter(game -> game.getPlayers().contains(player))
                    .mapToInt(game -> game.getMatchedPairs().get(game.getPlayers().indexOf(player)) * game.getDifficultyLevel().getPointsMultiplier())
                    .sum();

            PlayerScore playerScore = new PlayerScore(player, sum);
            playerScores.add(playerScore);
        }
        return playerScores.stream()
                .sorted((o1, o2) -> {
                    if (o1.getPoints().equals(o2.getPoints())) {
                        return 0;
                    }
                    if (o1.getPoints() > o2.getPoints()) {
                        return -1;
                    }
                    return 1;
                }).collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameMaster that = (GameMaster) o;
        return games.equals(that.games) &&
                players.equals(that.players) &&
                generator.equals(that.generator) &&
                getComputerPlayer().equals(that.getComputerPlayer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(games, players, generator, getComputerPlayer());
    }

    @Override
    public String toString() {
        return "GameMaster{" +
                "games=" + games +
                ", players=" + players +
                ", generator=" + generator +
                ", computerPlayer=" + computerPlayer +
                '}';
    }
}
