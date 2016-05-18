import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Gaem extends Application {

  final boolean[] upPressed = {false};
  final boolean[] leftPressed = {false};
  final boolean[] rightPressed = {false};

  double mouseX = 260;
  double mouseY = 160;

  Stage primaryStage;
  GraphicsContext gc;

  int blockSize = 32;

  List<ColorPoint> points = new ArrayList<>();
  double t = 0;

  public static void main(String[] args) {
    launch(args);
  }

  private void createGui(World world) {
    /*
    Loo aken koos canvasega
     */
    primaryStage.setTitle("Gaem");

    Group root = new Group();
    Scene theScene = new Scene(root);
    primaryStage.setScene(theScene);

    Canvas canvas = new Canvas(world.getWorldWidth(), world.getWorldHeight());
    root.getChildren().add(canvas);
    gc = canvas.getGraphicsContext2D();
  }

  private void createEventHandlers() {
    primaryStage.addEventFilter(MouseEvent.ANY, e -> {
      mouseX = e.getX();
      mouseY = e.getY();
    });

    primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
      if (e.getCode() == KeyCode.UP) {
        upPressed[0] = true;
      }
      if (e.getCode() == KeyCode.LEFT) {
        leftPressed[0] = true;
      }
      if (e.getCode() == KeyCode.RIGHT) {
        rightPressed[0] = true;
      }
    });

    primaryStage.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
      if (e.getCode() == KeyCode.UP) {
        upPressed[0] = false;
      }
      if (e.getCode() == KeyCode.LEFT) {
        leftPressed[0] = false;
      }
      if (e.getCode() == KeyCode.RIGHT) {
        rightPressed[0] = false;
      }
    });
  }

  private void playerStep(Player player) {
    // Mängija kiirus
    double xSpeed = player.getSpeed().x;
    double ySpeed = player.getSpeed().y;
    if (upPressed[0] && player.onGround()) {
      ySpeed -= 25;
      upPressed[0] = false;
    }
    if (leftPressed[0]) {
      xSpeed -= 5;
    }
    if (rightPressed[0]) {
      xSpeed += 5;
    }
    double maxSpeed = player.getMaxSpeed();
    xSpeed = Math.min(maxSpeed, Math.max(xSpeed, -maxSpeed));
    player.setSpeed(xSpeed, ySpeed);

    // Liiguta mängijat
    Point<Double> playerDelta = player.move();

    // Arvuta, palju mängija liikus
    Point<Double> lastPos = player.getLastPos();
    double deltaD = Math.hypot(playerDelta.x, playerDelta.y);

    // Loo värviline saba
    Color c = Color.rgb(
            (int) (255 * Math.abs(Math.cos(t))),
            (int) (220 * Math.abs(Math.cos(t * 0.6))),
            (int) (255 * Math.abs(Math.sin(t))),
            0.4
    );

    for (int i = 0; i < deltaD; i++) {
      points.add(new ColorPoint(
              lastPos.x + playerDelta.x * (i * (1 / deltaD)),
              lastPos.y + playerDelta.y * (i * (1 / deltaD)),
              c
      ));
    }

    if (deltaD > 0) {
      points.add(new ColorPoint(player.getX(), player.getY(), c));
    }
  }

  public void start(Stage primaryStage) {
    this.primaryStage = primaryStage;

    Random rand = new Random();
    int nr = rand.nextInt(new File("levels").list().length);

    World world = new World("levels/level" + nr, blockSize);
    createGui(world);
    createEventHandlers();

    Player.gc = gc;
    Player.setWorld(world);
    Player player = new Player(200, 200);

    final int maxage = 100;
    ColorPoint.maxage = maxage;

    AnimationTimer at = new AnimationTimer() {
      long last_time = System.nanoTime();

      public void handle(long now) {
        double delta_time = ((double) (now - last_time)) / (1000000000.0 / 60);
        last_time = now;
        t += 0.04 * delta_time;
        //label.setText(String.format("Delta time: %.2f", delta_time));

        // TEST LOW FPS:
        //for (int i = 0; i < 18000000; i++) { int a = i/10; }

        // Tausta joonistamine
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, world.getWorldWidth(), world.getWorldHeight());
        gc.setFill(Color.BLACK);

        // Mängija liigutamine
        // Kompenseerib vahele jäetud kaadrid
        for (int i = 0; i < delta_time; i++) {
          playerStep(player);
        }

        // Maailma joonistamine
        List<ColorPoint> toRemove = new ArrayList<>();

        for (ColorPoint p : points) {
          p.age += Math.max(1 * delta_time, 1);
          if (p.age > maxage) {
            toRemove.add(p);
          }
          p.draw(gc);
        }

        for (ColorPoint p : toRemove) {
          points.remove(p);
        }

        world.draw(gc);
        player.draw(gc, t);
      }
    };
    at.start();
    primaryStage.show();
  }
}
