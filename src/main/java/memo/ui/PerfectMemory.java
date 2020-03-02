package memo.ui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import memo.*;

import java.util.ArrayList;
import java.util.HashSet;

public class PerfectMemory extends Application {

    private Stage primaryStage;

    private GameMaster gameMaster;
    private Player player;
    private Game game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        this.gameMaster = new GameMaster(new ArrayList<>(), new HashSet<>());
        this.player = new Player("human");

        this.newGame();
    }

    private void newGame() throws Exception {
        this.game = this.gameMaster.newGame(DifficultyLevel.LEVEL1, this.player);

        this.renderBoard(true);

        Timeline hideAllBoardElements = new Timeline(new KeyFrame(Duration.seconds(this.game.getDifficultyLevel().getShowTime()), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                renderBoard(false);
            }
        }));
        hideAllBoardElements.setCycleCount(1);
        hideAllBoardElements.play();

        Timeline computer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            try {
                if (game.getWinner().isPresent()) {
                    return;
                }

                while(true) {
                    BoardElementPosition position = gameMaster.getComputerPlayer().getBoardElementPositionToSelect(game.getDifficultyLevel());
                    if (game.getBoard().getBoardElements()[position.getPositionX()][position.getPositionY()].getStatus() == BordElementStatus.HIDE) {
                        clickBoardElement(position, gameMaster.getComputerPlayer());
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        computer.setCycleCount(Timeline.INDEFINITE);
        computer.play();
    }

    private void renderBoard(boolean showAll) {
        Group root = new Group();
        Scene scene = new Scene(root, 1024, 720, Color.BLACK);

        Label labelActivePlayer = new Label("Player: " + this.game.getActivePlayer().getName());
        labelActivePlayer.setFont(new Font(50));
        labelActivePlayer.setTextFill(Color.RED);
        root.getChildren().add(labelActivePlayer);

        primaryStage.setTitle("PerfectMemory");
        primaryStage.setScene(scene);

        for (int x = 0; x < this.game.getDifficultyLevel().getBoardX(); x++) {
            for (int y = 0; y < this.game.getDifficultyLevel().getBoardY(); y++) {
                BoardElementPosition position = new BoardElementPosition(x, y);
                Node boardElement = renderBoardElement(position, this.game.getBoard().getBoardElements()[x][y], showAll);
                root.getChildren().add(boardElement);
            }
        }

        primaryStage.show();
    }

    private Node renderBoardElement(BoardElementPosition position, BoardElement boardElement, boolean show) {
        int size = 180;
        int paddingX = 20;
        int paddingY = 100;
        Group group = new Group();
        Rectangle r = new Rectangle(paddingX + size * position.getPositionX(), paddingY + size * position.getPositionY(), size - 10, size - 10);
        r.setFill(Color.GREEN);
        Label label = new Label(boardElement.getNumber().toString());
        label.setFont(new Font(size / 2));
        label.setTranslateX(paddingX + size * position.getPositionX());
        label.setTranslateY(paddingY + size * position.getPositionY());
        label.setTextFill(Color.BLACK);
        group.getChildren().add(r);
        if (show || boardElement.getStatus() != BordElementStatus.HIDE) {
            group.getChildren().add(label);
        }

        if (!show) {
            EventHandler<MouseEvent> eventHandler = e -> {
                try {
                    clickBoardElement(position, player);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            };
            r.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);
        }

        return group;
    }

    private void clickBoardElement(BoardElementPosition position, Player player) throws Exception {
        if (!game.getActivePlayer().getName().equals(player.getName())) {
            System.out.println("It's not your turn - " + player.getName());
            return;
        }
        System.out.println("Selecting board element " + position);
        if (game.selectBoardElement(player, position) == 1) {
            System.out.println("second false");
            game.getBoard().setElementStatus(position, BordElementStatus.SHOWED);
            renderBoard(false);
            Thread.sleep(1000);
            game.getBoard().setElementStatus(position, BordElementStatus.HIDE);
        }
        renderBoard(false);
    }
}
