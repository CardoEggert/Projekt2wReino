abstract public class WorldObject {
  /*
  Maailmas paiknevad liikuvad objektid, mis võivad asuda suvalises kohas.
   */
  private Point<Double> pos;
  private BoundingBox box = new BoundingBox(0, 0, 0, 0);

  public WorldObject(double x, double y) {
    setPos(x, y);
  }

  public BoundingBox getBoundingBox() {
    return box;
  }

  public void setBoundingBox(BoundingBox box) {
    this.box = box;
  }

  public Point<Double> getPos() {
    return pos;
  }

  public void setPos(Point<Double> pos) {
    this.pos = pos;
    box.x = pos.x.intValue();
    box.y = pos.y.intValue();
  }

  public void setPos(double x, double y) {
    setPos(new Point<>(x, y));
  }

  public double getX() {
    return pos.x;
  }

  public double getY() {
    return pos.y;
  }
}
