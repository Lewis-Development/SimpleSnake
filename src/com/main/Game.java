package com.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.prefs.Preferences;

import javax.swing.*;

public class Game implements ActionListener, KeyListener {
    public static Game snake;
    public Panel render;
    public Dimension dim;

    public Color backColor = Color.BLACK;
    public Color bodyColor = Color.WHITE;
    public Color headColor = Color.RED;
    public Color textColor = Color.GRAY;

    public int startingSize = 3;

    public Point head, point;
    public ArrayList<Point> snakeParts = new ArrayList<Point>();
    public int ticks = 0, direction, score, highscore, tailLength, time;
    public static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, SCALE = 10;

    public Random random;
    public Timer timer = new Timer(20, this);

    public boolean over = false, paused;

    public Game() {
        dim = Toolkit.getDefaultToolkit().getScreenSize();

        JFrame frame = new JFrame("SimpleSnake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getWidth() / 2, dim.height / 2 - frame.getHeight() / 2);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.addKeyListener(this);
        frame.add(render = new Panel());

        startGame();
    }

    public void startGame() {
        over = false;
        paused = false;

        time = 0;
        score = 0;
        ticks = 0;

        direction = DOWN;
        tailLength = startingSize;

        head = new Point(0, -1);
        snakeParts.clear();

        random = new Random();
        point = new Point(random.nextInt(38), random.nextInt(46));

        timer.start();
    }

    @Override public void actionPerformed(ActionEvent arg0) {
        render.repaint();
        ticks++;

        if (ticks % 2 == 0 && head != null && !over && !paused) {
            if (score > highscore) { highscore = score; }

            snakeParts.add(new Point(head.x, head.y));

            if (direction == UP) {
                if (head.y - 1 >= 0 && noTailAt(head.x, head.y - 1)) { head = new Point(head.x, head.y - 1); }
                else { over = true; }
            }

            if (direction == DOWN) {
                if (head.y + 1 < 47 && noTailAt(head.x, head.y + 1)) { head = new Point(head.x, head.y + 1); }
                else { over = true; }
            }

            if (direction == LEFT) {
                if (head.x - 1 >= 0 && noTailAt(head.x - 1, head.y)) { head = new Point(head.x - 1, head.y); }
                else { over = true; }
            }

            if (direction == RIGHT) {
                if (head.x + 1 < 39 && noTailAt(head.x + 1, head.y)) { head = new Point(head.x + 1, head.y); }
                else { over = true; }
            }

            if (snakeParts.size() > tailLength) { snakeParts.remove(0); }

            if (point != null) {
                if (head.equals(point)) {
                    score += 1;
                    tailLength++;
                    point.setLocation(random.nextInt(38), random.nextInt(46));
                }
            }
        }
    }

    public boolean noTailAt(int x, int y) {
        for (Point point : snakeParts) { if (point.equals(new Point(x, y))) { return false; } }
        return true;
    }

    public static void main(String[] args) { snake = new Game(); }

    @Override public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();

        if ((i == KeyEvent.VK_W || i == KeyEvent.VK_UP) && direction != DOWN) { direction = UP; }
        if ((i == KeyEvent.VK_A || i == KeyEvent.VK_LEFT) && direction != RIGHT) { direction = LEFT; }
        if ((i == KeyEvent.VK_S || i == KeyEvent.VK_DOWN) && direction != UP) { direction = DOWN; }
        if ((i == KeyEvent.VK_D || i == KeyEvent.VK_RIGHT) && direction != LEFT) { direction = RIGHT; }
        if (i == KeyEvent.VK_SPACE || i == KeyEvent.VK_ENTER || i == KeyEvent.VK_P) {
            if (over) { startGame(); }
            else { paused = !paused; }
        }
    }

    @Override public void keyReleased(KeyEvent e) { }
    @Override public void keyTyped(KeyEvent e) { }
}