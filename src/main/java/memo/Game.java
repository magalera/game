package memo;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class Game {
    private Board board;
    private List<Player> players;
    private List<Integer> matchedPairs;
    private int selectedElements[][];
    private DifficultyLevel difficultyLevel;
    private Optional<Player> winner;
    private GameStatus status;
    private Player activePlayer;
    private Optional<BoardElementPosition> lastSelectedBoardElement;

    public Game(Board board, List<Player> players, DifficultyLevel difficultyLevel, Player activePlayer) {
        this.board = board;
        this.status = GameStatus.IN_PROGRESS;
        this.players = players;
        this.difficultyLevel = difficultyLevel;
        this.activePlayer = activePlayer;
        this.winner = Optional.empty();
        this.lastSelectedBoardElement = Optional.empty();
        this.matchedPairs = players.stream().mapToInt(item -> Integer.valueOf(0)).boxed().collect(Collectors.toList());
    }

    Game (List<Player> players, List<Integer> matchedPairs, DifficultyLevel difficultyLevel, GameStatus status){
        this.players = players;
        this.matchedPairs = matchedPairs;
        this.difficultyLevel = difficultyLevel;
        this.status = status;
    }

    public Board getBoard() {
        return board;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public Optional<Player> getWinner() {
        return winner;
    }

    public GameStatus getStatus() {
        return status;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Optional<BoardElementPosition> getLastSelectedBoardElement() {
        return lastSelectedBoardElement;
    }

    public List<Integer> getMatchedPairs() {
        return matchedPairs;
    }

    public int selectBoardElement(Player player, BoardElementPosition boardElementPosition) throws Exception {
        this.throwExceptionWhenPositionIsIncorrect(boardElementPosition);
        this.throwExceptionWhenPlayerTurnIsWrong(player);
        if (!lastSelectedBoardElement.isPresent()) {
            this.selectFirstBoardElement(boardElementPosition);
            return 0;
        }

        if (this.selectSecondBoardElement(boardElementPosition)) {
            return 2;
        }
        return 1;
    }

    private void selectFirstBoardElement(BoardElementPosition boardElementPosition) {
        this.board.setElementStatus(boardElementPosition, BordElementStatus.SELECTED);
        lastSelectedBoardElement = Optional.of(boardElementPosition);
    }

    private boolean selectSecondBoardElement(BoardElementPosition boardElementPosition) {
        BoardElement first = this.board.getBoardElement(lastSelectedBoardElement.get());
        BoardElement second = this.board.getBoardElement(boardElementPosition);

        int playerIndex = players.indexOf(activePlayer);
        boolean isPair = false;
        if (isPair(first, second)) {
            this.board.setElementStatus(lastSelectedBoardElement.get(), BordElementStatus.SHOWED);
            this.board.setElementStatus(boardElementPosition, BordElementStatus.SHOWED);

            Integer matchedPairs = this.matchedPairs.get(playerIndex);
            matchedPairs++;
            this.matchedPairs.set(playerIndex, matchedPairs);

            if (this.board.isFinished()) {
                this.status = GameStatus.COMPLETED;
                this.winner = Optional.of(activePlayer);
            }
            isPair = true;
        } else {
            this.board.setElementStatus(lastSelectedBoardElement.get(), BordElementStatus.HIDE);
            playerIndex++;
            if (playerIndex >= players.size()){
                playerIndex = 0;
            }
            activePlayer = players.get(playerIndex);
        }
        lastSelectedBoardElement = Optional.empty();
        return isPair;
    }

    private boolean isPair(BoardElement first, BoardElement second) {
        return first.getNumber().equals(second.getNumber());
    }

    private void throwExceptionWhenPositionIsIncorrect(BoardElementPosition boardElementPosition) throws Exception {
        if (boardElementPosition.getPositionX() > this.getDifficultyLevel().getBoardX() || boardElementPosition.getPositionX() < 0 ||
                boardElementPosition.getPositionY() > this.getDifficultyLevel().getBoardY() || boardElementPosition.getPositionY() < 0) {
            throw new Exception("Given position is out of the board");
        }
    }

    private void throwExceptionWhenPlayerTurnIsWrong(Player player) throws Exception {
        if (!this.activePlayer.equals(player)) {
            throw new Exception("Wrong player turn");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return getBoard().equals(game.getBoard()) &&
                getPlayers().equals(game.getPlayers()) &&
                getMatchedPairs().equals(game.getMatchedPairs()) &&
                Arrays.equals(selectedElements, game.selectedElements) &&
                getDifficultyLevel() == game.getDifficultyLevel() &&
                getWinner().equals(game.getWinner()) &&
                getStatus() == game.getStatus() &&
                getActivePlayer().equals(game.getActivePlayer()) &&
                getLastSelectedBoardElement().equals(game.getLastSelectedBoardElement());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getBoard(), getPlayers(), getMatchedPairs(), getDifficultyLevel(), getWinner(), getStatus(), getActivePlayer(), getLastSelectedBoardElement());
        result = 31 * result + Arrays.hashCode(selectedElements);
        return result;
    }

    @Override
    public String toString() {
        return "Game{" +
                "board=" + board +
                ", players=" + players +
                ", matchedPairs=" + matchedPairs +
                ", selectedElements=" + Arrays.toString(selectedElements) +
                ", difficultyLevel=" + difficultyLevel +
                ", winner=" + winner +
                ", status=" + status +
                ", activePlayer=" + activePlayer +
                ", lastSelectedBoardElement=" + lastSelectedBoardElement +
                '}';
    }
}
