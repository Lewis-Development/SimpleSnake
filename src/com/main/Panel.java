package com.main;

import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

public class Panel extends JPanel {
    @Override protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Game snake = Game.snake;

        g.setColor(Game.snake.backColor);
        g.fillRect(0, 0, 400, 500);

        g.setColor(Game.snake.bodyColor);

        for (Point point : snake.snakeParts) {
            g.fillRect(point.x * Game.SCALE, point.y * Game.SCALE, Game.SCALE, Game.SCALE);
        }
        g.fillRect(snake.head.x * Game.SCALE, snake.head.y * Game.SCALE, Game.SCALE, Game.SCALE);

        g.setColor(Game.snake.headColor);
        g.fillRect(snake.point.x * Game.SCALE, snake.point.y * Game.SCALE, Game.SCALE, Game.SCALE);

        String string = "Score: " + snake.score + " | Highscore: " + snake.highscore;
        g.setColor(Game.snake.textColor);

        g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 10);

        string = "GAME OVER";
        if (snake.over) { g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4); }

        string = "GAME PAUSED";
        if (snake.paused && !snake.over) { g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), (int) snake.dim.getHeight() / 4); }
    }
}