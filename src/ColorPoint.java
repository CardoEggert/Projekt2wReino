import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ColorPoint extends WorldObject {
  Color color;
  int age = 0;
  public static int maxage;

  ColorPoint(double x, double y, Color color) {
    super(x, y);
    this.color = color;
  }

  public void draw(GraphicsContext gc) {
    gc.setFill(color.deriveColor(0, 1, 1,
            ((float) maxage - age) / (float) maxage
    ));
    gc.fillOval(getX(), getY(), 12, 12);
  }
}