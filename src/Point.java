public class Point<C extends Number> {
  /*
  Klass 2D punktidele/vektorile
   */
  C x;
  C y;

  public Point(C x, C y) {
    this.x = x;
    this.y = y;
  }

  @Override
  public String toString() {
    return "Point{" +
            "x=" + x +
            ", y=" + y +
            '}';
  }
}
