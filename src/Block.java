import javafx.scene.canvas.GraphicsContext;

public class Block extends WorldTile {
  /*
  Põhiblokk, millest maailm ehitatakse.
   */
  final BoundingBox box;
  final int size;
  private double friction; // Objekti takistus, 0-1 0=puudub 1=suurim

  public Block(int x, int y, int size) {
    super(x, y);
    this.box = new BoundingBox(x, y, size, size);
    this.size = size;
    setFriction(0.6);
  }

  public Block(int x, int y, int size, double friction) {
    this(x, y, size);
    setFriction(friction);
  }

  public double getFriction() {
    return friction;
  }

  public void setFriction(double friction) {
    this.friction = Math.max(0, Math.min(friction, 1));
  }

  public BoundingBox getBoundingBox() {
    return box;
  }

  public void draw(GraphicsContext gc) {
    gc.strokeRect(getX(), getY(), size, size);
  }
}
