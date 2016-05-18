

abstract public class WorldTile {
  /*
  Maailmas paiknevad staatilised objektid, mis ei liigu.
   */
  final Point<Integer> pos;

  public WorldTile(int x, int y) {
    this.pos = new Point<>(x,y);
  }

  public int getX() {
    return pos.x;
  }

  public int getY() {
    return pos.y;
  }
}
