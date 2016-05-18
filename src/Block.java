

public class Block extends WorldTile {
  /*
  Põhiblokk, millest maailm ehitatakse.
   */
  final BoundingBox box;
  final int size;

  public Block(int x, int y, int size) {
    super(x, y);
    this.box = new BoundingBox(x,y,size,size);
    this.size = size;
  }

  public BoundingBox getBoundingBox() {
    return box;
  }
}
