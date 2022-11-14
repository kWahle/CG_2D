import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.Random;

public class UI extends Application {
    private Image selected;
    private Scene scene;
    private BorderPane borderPane = new BorderPane();
    private HBox hBox = new HBox();
    private int rows;
    private int cols;
    private Image[] images;
    private Image[] backgrounds;
    private int[] row = {4, 6, 8, 3};
    private int[] col = {6, 8, 12, 4};
    private Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    private Random random = new Random();

    public void start(Stage stage) {
        loadPictures();
        initialize();
        createStartScreen();

        borderPane.setCenter(hBox);
        scene = new Scene(borderPane);
        scene.setFill(Color.PALETURQUOISE);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setTitle("Puzzle");
        stage.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                stage.close();
            }
            if (KeyCode.I == event.getCode()) {
                rows = row[3];
                cols = col[3];
            }
            if (KeyCode.N == event.getCode()) {
                borderPane.setCenter(hBox);
            }
        });
        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        stage.show();
    }

    private void initialize() {
        rows = row[0];
        cols = col[0];
        setSelected(images[random.nextInt(9)]);
        borderPane.setStyle("-fx-background-color: transparent;");
    }

    private void createStartScreen() {
        GridPane startScreen = new GridPane();
        ToggleButton[] imageSelection = new ToggleButton[9];
        ToggleButton[] difficultySelection = new ToggleButton[3];
        ToggleGroup imageGroup = new ToggleGroup();
        ToggleGroup difficultyGroup = new ToggleGroup();

        for (int i = 0; i < 9; i++) {
            int finalI = i;

            imageSelection[i] = new ToggleButton();
            imageSelection[i].setPrefWidth(screenBounds.getWidth() / 5);
            imageSelection[i].setPrefHeight(imageSelection[i].getPrefWidth() / 4 * 3);
            imageSelection[i].setBackground(new Background(new BackgroundImage(backgrounds[i], BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT)));
            imageSelection[i].setStyle("-fx-border-color: transparent; -fx-opacity: 0.85");
            imageSelection[i].setOnAction(e -> {
                for (ToggleButton tb : imageSelection) {
                    tb.setStyle("-fx-border-color: transparent; -fx-opacity: 0.85;");
                }
                imageSelection[finalI].setStyle("-fx-border-color: black;-fx-opacity: 1");
                setSelected(images[finalI]);

            });

            imageGroup.getToggles().add(imageSelection[i]);

            if (i < 3)
                startScreen.add(imageSelection[i], i, 1);
            else if ( i<6)
                startScreen.add(imageSelection[i], i % 3, 2);
            else
                startScreen.add(imageSelection[i], i % 3, 3);
        }
        startScreen.setHgap(screenBounds.getWidth() / 25);
        startScreen.setVgap(screenBounds.getWidth() / 25);
        startScreen.setAlignment(Pos.CENTER);
        startScreen.setPadding(new Insets(screenBounds.getWidth() / 25));

        VBox vBox = new VBox();
        Button startButton = new Button("Start");
        startButton.setPrefWidth(screenBounds.getWidth() / 5);
        startButton.setPrefHeight(startButton.getPrefWidth() / 5);
        startButton.setStyle(" -fx-font-size: 2em");
        startButton.setOnAction(e -> {
            borderPane.setCenter(new Puzzle(selected, rows, cols).createPuzzle());
        });
        for (int i = 0; i < 3; i++) {
            int finalI = i;

            difficultySelection[i] = new ToggleButton();
            difficultySelection[i].setPrefWidth(screenBounds.getWidth() / 5);
            difficultySelection[i].setPrefHeight(difficultySelection[i].getPrefWidth() / 5);
            difficultySelection[i].setStyle(" -fx-font-size: 2em");
            difficultySelection[i].setOnAction(e -> {
                rows = row[finalI];
                cols = col[finalI];
            });

            difficultyGroup.getToggles().add(difficultySelection[i]);
        }

        difficultySelection[0].setText(" Easy");
        difficultySelection[1].setText(" Medium");
        difficultySelection[2].setText(" Hard");

        Text instruction= new Text("Please select an image and a level of difficulty.\n\n" +
                "Without a selection an easy game and a random image are set as default.\n\n" +
                "Please press Start to start the game.\n" +
                "Press Esc to end the game."
                );
        instruction.setWrappingWidth(screenBounds.getWidth() / 5);
        instruction.setStyle(" -fx-font-size: 1.5em");
        vBox.getChildren().addAll(instruction,difficultySelection[0],difficultySelection[1], difficultySelection[2], startButton);
        vBox.setPadding(new Insets(screenBounds.getWidth() / 25,screenBounds.getWidth() / 25,screenBounds.getWidth() / 25,0));
        vBox.setSpacing(startButton.getPrefHeight());
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        hBox.getChildren().addAll(startScreen, vBox);
    }

    private void loadPictures() {
        images = new Image[9];
        images[0] = new Image("Assests/Kitty.jpg", screenBounds.getWidth() / 2.5, 450, true, false);
        images[1] = new Image("Assests/Flocke.jpg", screenBounds.getWidth() / 2.5, 450, true, false);
        images[2] = new Image("Assests/Kuku.jpg", screenBounds.getWidth() / 2.5, 450, true, false);
        images[3] = new Image("Assests/Minos.jpg", screenBounds.getWidth() / 2.5, 450, true, false);
        images[4] = new Image("Assests/Chilli.jpg", screenBounds.getWidth() / 2.5, 450, true, false);
        images[5] = new Image("Assests/Hexe.jpg", screenBounds.getWidth() / 2.5, 450, true, false);
        images[6] = new Image("Assests/LakiIna.jpg", screenBounds.getWidth() / 2.5, 450, true, false);
        images[7] = new Image("Assests/Tussi.jpg", screenBounds.getWidth() / 2.5, 450, true, false);
        images[8] = new Image("Assests/Laki.jpg", screenBounds.getWidth() / 2.5, 450, true, false);

        backgrounds = new Image[9];
        backgrounds[0] = new Image("Assests/Kitty.jpg", 450, screenBounds.getWidth() / 20 * 3, true, false);
        backgrounds[1] = new Image("Assests/Flocke.jpg", 450, screenBounds.getWidth() / 20 * 3, true, false);
        backgrounds[2] = new Image("Assests/Kuku.jpg", 450, screenBounds.getWidth() / 20 * 3, true, false);
        backgrounds[3] = new Image("Assests/Minos.jpg", 450, screenBounds.getWidth() / 20 * 3, true, false);
        backgrounds[4] = new Image("Assests/Chilli.jpg", 450, screenBounds.getWidth() / 20 * 3, true, false);
        backgrounds[5] = new Image("Assests/Hexe.jpg", 450, screenBounds.getWidth() / 20 * 3, true, false);
        backgrounds[6] = new Image("Assests/LakiIna.jpg", 450, screenBounds.getWidth() / 20 * 4, true, false);
        backgrounds[7] = new Image("Assests/Tussi.jpg", 450, screenBounds.getWidth() / 20 * 3, true, false);
        backgrounds[8] = new Image("Assests/Laki.jpg", 450, screenBounds.getWidth() / 20 * 4, true, false);
    }

    public void setSelected(Image selected) {
        this.selected = selected;
    }

}