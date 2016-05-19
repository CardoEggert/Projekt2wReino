import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class World {
  List<Block> blocks = new ArrayList<>();
  Point<Integer> worldSize = new Point<>(0,0);
  Point<Integer> playerPos = new Point<>(0,0);
  int gridSize;

  public World(String level, int gridSize) {
    this.gridSize = gridSize;

    File fail = new java.io.File(level + ".txt");
    try (Scanner sc = new Scanner(fail, "UTF-8")) {
      int x = -1;
      int y = 0;
      while (sc.hasNextLine()) {
        for (char c : sc.nextLine().toCharArray()) {
          if (c == '#') {
            blocks.add(new Block(x * gridSize, y * gridSize, gridSize));
          } else if (c == '@') {
            blocks.add(new BlockIce(x * gridSize, y * gridSize, gridSize));
          } else if (c == 'P') {
            playerPos.x = x * gridSize;
            playerPos.y = y * gridSize;
          }
          x++;
        }
        worldSize.x = x*gridSize;
        x = 0;
        y++;
      }
      worldSize.y = y*gridSize;
    } catch (FileNotFoundException e) {
      System.out.println("Faili ei leitud! (" + level + ")");
      Platform.exit();
    }

  }

  public Point<Integer> getWorldSize() {
    return worldSize;
  }

  public int getWorldWidth() {
    return worldSize.x;
  }

  public int getWorldHeight() {
    return worldSize.y;
  }

  public int getGridSize() {
    return gridSize;
  }

  public List<Block> getBlocks() {
    return blocks;
  }

  public void draw(GraphicsContext gc) {
    gc.setLineWidth(2);
    gc.setLineDashes(4);
    gc.setLineDashOffset(2);
    for (Block b : getBlocks()) {
      b.draw(gc);
    }
  }

}
