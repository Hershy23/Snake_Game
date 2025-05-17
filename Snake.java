import java.awt.*;
import java.util.*;

public class Snake {
    private LinkedList<Point> body;
    private Direction direction;

    public Snake(Point start) {
        body = new LinkedList<>();
        body.add(start);
        direction = Direction.RIGHT;
    }

    public void move() {
        Point head = getHead();
        Point newHead = new Point(head);
        switch (direction) {
            case UP -> newHead.y--;
            case DOWN -> newHead.y++;
            case LEFT -> newHead.x--;
            case RIGHT -> newHead.x++;
        }
        body.addFirst(newHead);
        body.removeLast();
    }

    public void grow() {
        Point head = getHead();
        Point newHead = new Point(head);
        switch (direction) {
            case UP -> newHead.y--;
            case DOWN -> newHead.y++;
            case LEFT -> newHead.x--;
            case RIGHT -> newHead.x++;
        }
        body.addFirst(newHead); // Just add another head — don't remove tail
    }

    public boolean checkCollision() {
        Point head = getHead();
        for (int i = 1; i < body.size(); i++) {
            if (head.equals(body.get(i))) {
                return true;
            }
        }
        return false;
    }

    public Point getHead() {
        return body.getFirst();
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction d) {
        direction = d;
    }
}
