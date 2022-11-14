import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Piece {
    private ImageView piece = new ImageView();
    private double width;
    private double height;
    private int index;
    public Piece(Image image, double width, double height, int index) {

        piece.setImage(image);
        this.width = width;
        this.height = height;
        this.index = index;
    }

    public ImageView createPiece(double posX, double posY){
        piece.setViewport(new Rectangle2D(posX, posY, width, height));
        return piece;
    }

    public ImageView getPiece() {
        return piece;
    }

    public void setPiece(ImageView piece) {
        this.piece = piece;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
