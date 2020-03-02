package memo;

import java.util.Random;

public class ComputerPlayer extends Player {
    private Random random = new Random();

    public ComputerPlayer(String name) {
        super(name);
    }

    public BoardElementPosition getBoardElementPositionToSelect(DifficultyLevel difficultyLevel) {
        return new BoardElementPosition(getRandomNumber(0, difficultyLevel.getBoardX()),
                getRandomNumber(0, difficultyLevel.getBoardY()));
    }

    private int getRandomNumber(int min, int max) {
        return random.ints(min, max).findFirst().getAsInt();
    }
}
