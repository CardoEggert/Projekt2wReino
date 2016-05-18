import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;

public class BlockIce extends Block {

  public BlockIce(int x, int y, int size) {
    super(x, y, size);
    setFriction(0);
  }

  public void draw(GraphicsContext gc) {
    Paint prev = gc.getStroke();
    gc.setStroke(Color.AQUA);
    gc.strokeRect(getX(), getY(), size, size);
    gc.setStroke(prev);
  }
  
}
