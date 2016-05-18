import javafx.scene.paint.Color;

public class ColorPoint extends WorldObject {
  Color color;
  int age = 0;

  ColorPoint(double x, double y, Color color) {
    super(x, y);
    this.color = color;
  }
}