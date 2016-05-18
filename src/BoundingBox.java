

public class BoundingBox {
  int x;
  int y;
  int width;
  int height;

  public BoundingBox(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public boolean collidesWith(BoundingBox b) {
    return ((x + width) >= b.x &&
            x <= (b.x + b.width) &&
            (y + height) >= b.y &&
            y <= (b.y + b.height));
  }

  public boolean collidesWith(WorldObject o) {
    BoundingBox b = o.getBoundingBox();
    return ((x + width) >= b.x &&
            x <= (b.x + b.width) &&
            (y + height) >= b.y &&
            y <= (b.y + b.height));
  }

  public boolean collidesWith(int px, int py) {
    /*
    Testib kokkupõrget punktiga
     */
    return ((x + width) >= px &&
            x <= px &&
            (y + height) >= py &&
            y <= py );
  }
}

