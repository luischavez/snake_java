package com.luischavezb.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main implements KeyListener {
    public static final int WIDTH = 500;
    public static final int HEIGHT = 500;

    private Direction direction = Direction.RIGHT;
    private World world;
    private Snake snake;
    private GUI gui;

    public Main() {
        gui = new GUI();

        snake = new Snake(Direction.RIGHT);

        world = new World(60, WIDTH, HEIGHT, delta -> {
            gui.drawPanel.repaint();
            snake.direction(direction);
        });

        world.set(snake, new Point(0, 0));
        world.set(new Apple(), new Point(100, 100));
    }

    public static void main(String... args) {
        new Main();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                direction = Direction.UP;
                break;
            case KeyEvent.VK_DOWN:
                direction = Direction.DOWN;
                break;
            case KeyEvent.VK_LEFT:
                direction = Direction.LEFT;
                break;
            case KeyEvent.VK_RIGHT:
                direction = Direction.RIGHT;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private class GUI {
        private JFrame mainFrame;
        private DrawPanel drawPanel;

        public GUI() {
            Dimension dimension = new Dimension(WIDTH, HEIGHT);

            mainFrame = new JFrame("Snake");
            mainFrame.setSize(dimension);
            mainFrame.setMinimumSize(dimension);
            mainFrame.setMaximumSize(dimension);
            mainFrame.setPreferredSize(dimension);
            mainFrame.setResizable(false);

            drawPanel = new DrawPanel();
            drawPanel.setSize(dimension);
            drawPanel.setMinimumSize(dimension);
            drawPanel.setMaximumSize(dimension);
            drawPanel.setPreferredSize(dimension);

            mainFrame.add(drawPanel);
            mainFrame.pack();

            mainFrame.addKeyListener(Main.this);

            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            mainFrame.setVisible(true);
        }
    }

    private class DrawPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            final Graphics2D graphics2D = Graphics2D.class.cast(g);

            graphics2D.setColor(Color.BLACK);

            world.entities().forEach(entity -> {
                graphics2D.fillRect((int) entity.x(), (int) entity.y(), entity.width(), entity.height());
            });
        }
    }
}
