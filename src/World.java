import javafx.scene.canvas.GraphicsContext;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class World {
  List<Block> blocks = new ArrayList<>();
  int x = -1;
  int lastX;
  int y = 0;

  public World(String level, int gridSize) {
    java.io.File fail = new java.io.File(level + ".txt");
    java.util.Scanner sc = null;
    try {
      sc = new java.util.Scanner(fail, "UTF-8");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }

    while (sc.hasNextLine()) {
      String rida = sc.nextLine();
      for (char c : rida.toCharArray()) {
        if (c == '#') {
          blocks.add(new Block(x * gridSize, y * gridSize, gridSize));
        }
        x++;
      }
      lastX = x;

      x = 0;
      y++;
    }

  }


  public int getY() {
    return y;
  }

  public List<Block> getBlocks() {
    return blocks;
  }

  public int getLastX() {
    return lastX;
  }

  public void draw(GraphicsContext gc) {
    gc.setLineWidth(2);
    gc.setLineDashes(4);
    gc.setLineDashOffset(2);
    for (Block b : getBlocks()) {
      gc.strokeRect(b.getX(), b.getY(), b.size, b.size);
    }
  }

}
