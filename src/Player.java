import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player extends WorldObject {
  static World world;
  static GraphicsContext gc;
  private final double g = 9.8 / 5;
  private final double airFriction = 0.01; // Objekti takistus õhus
  private Point<Double> lastPos;
  private Point<Double> speed; // Objekti kiirus
  private Point<Double> accel; // Objekti kiirendus
  private boolean onGround; // Kas objekt puudutab maapinda

  public Player(double x, double y) {
    super(x, y);
    setBoundingBox(new BoundingBox((int) x, (int) y, 12, 12));
    lastPos = getPos();
    setAccel(0, 0);
    setSpeed(0, 0);
  }

  public static void setWorld(World world) {
    Player.world = world;
  }

  public Point<Double> getLastPos() {
    return lastPos;
  }

  public void setX(double x) {
    setPos(x, getY());
  }

  public void setY(double y) {
    setPos(getX(), y);
  }

  public Point<Double> getAccel() {
    return accel;
  }

  public void setAccel(double x, double y) {
    this.accel = new Point<>(x, y + g);
  }

  public Point<Double> getSpeed() {
    return speed;
  }

  public void setSpeed(double x, double y) {
    this.speed = new Point<>(x, y);
  }

  public boolean onGround() {
    return onGround;
  }

  public int getMaxSpeed() {
    return 10;
  }

  public void draw(GraphicsContext gc, double t) {
    gc.setFill(Color.rgb(
            (int) (255 * Math.abs(Math.cos(t))),
            (int) (220 * Math.abs(Math.cos(t * 0.6))),
            (int) (255 * Math.abs(Math.sin(t))),
            1));
    gc.setLineDashes(0);
    gc.fillOval(getX(), getY(), 12, 12);
    gc.strokeOval(getX(), getY(), 12, 12);
  }

  public Point<Double> move() {
    /*
    Liiguta mängijat ühe sammu võrra kiirenduse järgi, arvestades maailmas olevaid blokke.
    Tagastab, kui palju mängija tegelikult liikus.
     */
    lastPos = getPos();
    double friction = 0;

    // Lisa kiirusele kiirendus
    setSpeed(getSpeed().x + getAccel().x, getSpeed().y + getAccel().y);
    Point<Double> speed = getSpeed();

    // Arvuta, palju peaks liikuma
    double delta = Math.round(Math.hypot(speed.x, speed.y));

    // Alusta kokkupõrgete kontrollimist
    boolean xDone = false;
    boolean yDone = false;
    double d = 1 / delta;
    onGround = false;

    for (int i = 0; i < delta; i++) {
      if (!xDone) {
        double x = getX();
        setX(x + speed.x * d);
        for (Block b : world.getBlocks())
          if (b.getBoundingBox().collidesWith(this)) {
            setX(x);
            xDone = true;
            break;
          }
      }
      if (!yDone) {
        double y = getY();
        setY(y + speed.y * d);
        for (Block b : world.getBlocks())
          if (b.getBoundingBox().collidesWith(this)) {
            setY(y);
            yDone = true;
            if (getY() < b.getY()) {
              gc.fillRect(b.getX(), b.getY(), b.size, b.size);
              friction = b.getFriction();
              onGround = true;
            }
            break;
          }
      }
      if (xDone && yDone)
        break;
    }

    double deltaX = Math.round(100 * (getX() - getLastPos().x)) / 100.0;
    double deltaY = Math.round(100 * (getY() - getLastPos().y)) / 100.0;

    Point<Double> moved = new Point<>(deltaX, deltaY);

    if (onGround) {
      setSpeed(deltaX * (1 - friction), deltaY);
    } else {
      setSpeed(deltaX * (1 - airFriction), deltaY * (1 - airFriction));
    }

    return moved;
  }
}