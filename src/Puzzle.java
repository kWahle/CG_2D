import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;

import java.util.Arrays;
import java.util.Collections;

public class Puzzle {
    private Image image;
    private int col;
    private int row;
    private Piece[] puzzlePieces;
    private ImageView[] solutionPieces;
    private GridPane solution = new GridPane();
    private Rectangle2D screenBounds = Screen.getPrimary().getBounds();
    private HBox hBox = new HBox();
    private StackPane right = new StackPane();
    private StackPane left = new StackPane();
    private double width;
    private double height;
    private int piecesLeft;
    Text instruction= new Text("Press N to start a new game. \n"+
            "Press Esc to end the game."
    );
    public Puzzle(Image image, int col, int row) {
        this.image = image;
        this.col = row;
        this.row = col;
        this.piecesLeft= col*row;
        puzzlePieces = new Piece[col * row];
        solutionPieces = new ImageView[col * row];
    }

    public HBox createPuzzle() {
        width = image.getWidth() / col;
        height = image.getHeight() / row;
        double posX = 0;
        double posY = 0;
        int nr = 0;
        GridPane puzzle = new GridPane();

        for (int i = 0; i < row; i++) { //height
            for (int j = 0; j < col; j++) {//width
                solutionPieces[nr] = new Piece(image, width, height, nr).createPiece(posX, posY);
                puzzlePieces[nr] = new Piece(image, width, height, nr);
                puzzlePieces[nr].createPiece(posX, posY);
                posX += width;
                nr++;
            }
            posY += height;
            posX = 0;
        }
        Collections.shuffle(Arrays.asList(puzzlePieces));

        nr = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                makeDraggable(puzzlePieces[nr].getPiece(), puzzlePieces[nr].getIndex());
                puzzle.add(puzzlePieces[nr].getPiece(), j, i);
                solution.add(solutionPieces[nr], j, i);
                solutionPieces[nr++].setVisible(false);
            }
        }

        puzzle.setAlignment(Pos.CENTER);
        puzzle.setGridLinesVisible(false);
        solution.setAlignment(Pos.CENTER);
        solution.setGridLinesVisible(false);
        solution.setStyle("-fx-background-color: grey; ");
        solution.setMaxSize(image.getWidth(), image.getHeight());

        right.getChildren().add(puzzle);
        right.setPrefWidth(screenBounds.getWidth() / 2);
        left.setPrefWidth(screenBounds.getWidth() / 2);
        left.getChildren().add(solution);

        hBox.getChildren().addAll(left, right);

        return hBox;
    }

    private double startX;
    private double startY;
    private boolean first = true;
    private Point2D[] coords;

    private void makeDraggable(Node node, int index) {
        //based on https://www.youtube.com/watch?v=YaDkj-bqcj8
        node.setOnMousePressed(e -> {
            startX = e.getSceneX() - node.getTranslateX();
            startY = e.getSceneY() - node.getTranslateY();
            if (first) {
                first = false;
                coords = new Point2D[col * row];
                for (int i = 0; i < row * col; i++) {
                    coords[i] = puzzlePieces[i].getPiece().localToParent(0, 0).add(width / 2, height / 2);
                }
            }
        });
        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startX);
            node.setTranslateY(e.getSceneY() - startY);
            node.toFront();
        });
        node.setOnMouseReleased(e -> {
            if (node.contains(node.sceneToLocal(coords[index]))) {
                notDraggable(node);
                node.setVisible(false);
                solutionPieces[index].setVisible(true);
                piecesLeft--;
                if (piecesLeft ==0){
                    right.getChildren().removeAll();
                    instruction.setStyle(" -fx-font-size: 1.5em");
                    right.getChildren().add(instruction);
                }
            }
        });
    }

    private void notDraggable(Node node) {
        node.setOnMousePressed(e -> {
        });
        node.setOnMouseDragged(e -> {
        });
    }
}