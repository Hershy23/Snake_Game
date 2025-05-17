import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Snake snake;
    private Food food;
    private boolean running;
    private final int boxSize = 20;
    private final int gridWidth = 30;
    private final int gridHeight = 30;

    public GamePanel() {
        this.setPreferredSize(new Dimension(gridWidth * boxSize, gridHeight * boxSize));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(this);
        startGame();
    }

    public void startGame() {
        snake = new Snake(new Point(5, 5));
        food = new Food(gridWidth, gridHeight);
        running = true;
        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!running) return;

        snake.move();

        if (snake.getHead().equals(food.getPosition())) {
            snake.grow();
            food.relocate(snake.getBody());
        }

        if (snake.checkCollision() || outOfBounds(snake.getHead())) {
            running = false;
            timer.stop();
            int choice = JOptionPane.showConfirmDialog(this, "Game Over! Replay?", "Game Over", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                startGame();
            } else {
                System.exit(0);
            }
        }

        repaint();
    }

    private boolean outOfBounds(Point head) {
        return head.x < 0 || head.x >= gridWidth || head.y < 0 || head.y >= gridHeight;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 0; i < snake.getBody().size(); i++) {
            Point p = snake.getBody().get(i);
            float shade = (float) i / snake.getBody().size();
           g.setColor(new Color((int)(128 + 127 * shade), 0, (int)(128 + 127 * shade)));

            g.fillRoundRect(p.x * boxSize, p.y * boxSize, boxSize, boxSize, 10, 10);
        }
        Point head = snake.getHead();
        int eyeSize = 4;
        int eyeOffsetX = 4;
        int eyeOffsetY = 4;

        g.setColor(Color.BLACK);


        switch (snake.getDirection()) {
            case UP -> {
                g.fillOval(head.x * boxSize + eyeOffsetX, head.y * boxSize + eyeOffsetY, eyeSize, eyeSize);
                g.fillOval(head.x * boxSize + boxSize - eyeOffsetX - eyeSize, head.y * boxSize + eyeOffsetY, eyeSize, eyeSize);
            }
            case DOWN -> {
                g.fillOval(head.x * boxSize + eyeOffsetX, head.y * boxSize + boxSize - eyeOffsetY - eyeSize, eyeSize, eyeSize);
                g.fillOval(head.x * boxSize + boxSize - eyeOffsetX - eyeSize, head.y * boxSize + boxSize - eyeOffsetY - eyeSize, eyeSize, eyeSize);
            }
            case LEFT -> {
                g.fillOval(head.x * boxSize + eyeOffsetY, head.y * boxSize + eyeOffsetX, eyeSize, eyeSize);
                g.fillOval(head.x * boxSize + eyeOffsetY, head.y * boxSize + boxSize - eyeOffsetX - eyeSize, eyeSize, eyeSize);
            }
            case RIGHT -> {
                g.fillOval(head.x * boxSize + boxSize - eyeOffsetY - eyeSize, head.y * boxSize + eyeOffsetX, eyeSize, eyeSize);
                g.fillOval(head.x * boxSize + boxSize - eyeOffsetY - eyeSize, head.y * boxSize + boxSize - eyeOffsetX - eyeSize, eyeSize, eyeSize);
            }
        }
        g.setColor(Color.YELLOW);
        Point f = food.getPosition();
        g.fillOval(f.x * boxSize, f.y * boxSize, boxSize, boxSize);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Direction curr = snake.getDirection();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> { if (curr != Direction.DOWN) snake.setDirection(Direction.UP); }
            case KeyEvent.VK_DOWN -> { if (curr != Direction.UP) snake.setDirection(Direction.DOWN); }
            case KeyEvent.VK_LEFT -> { if (curr != Direction.RIGHT) snake.setDirection(Direction.LEFT); }
            case KeyEvent.VK_RIGHT -> { if (curr != Direction.LEFT) snake.setDirection(Direction.RIGHT); }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
