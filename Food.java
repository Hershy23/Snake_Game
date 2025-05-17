import java.awt.*;
import java.util.*;

public class Food {
    private Point position;
    private int gridWidth, gridHeight;
    private Random rand = new Random();

    public Food(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        relocate(new LinkedList<>());
    }

    public void relocate(LinkedList<Point> snakeBody) {
        while (true) {
            int x = rand.nextInt(gridWidth);
            int y = rand.nextInt(gridHeight);
            Point p = new Point(x, y);
            if (!snakeBody.contains(p)) {
                position = p;
                return;
            }
        }
    }

    public Point getPosition() {
        return position;
    }
}
