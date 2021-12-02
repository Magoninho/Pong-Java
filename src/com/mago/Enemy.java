package com.mago;

import org.w3c.dom.css.Rect;

import java.awt.*;

public class Enemy {

	public double x, y;
	public int width, height;

	public Enemy(int x, int y) {
		this.x = x;
		this.y = y;
		this.width = 60;
		this.height = 10;
	}

	public void tick(double deltaTime) {
//		x = (Game.ball.x - this.width/2.0) * 0.7;

		if (Game.ball.y < Game.HEIGHT / 2) {
			if (Game.ball.x - width/2 > x) {
				x += 4 * deltaTime; // TODO: random
			}
			if (Game.ball.x - width/2 < x) {
				x -= 4 * deltaTime;
			}
		}

		if (x+width > Game.WIDTH) {
			x = Game.WIDTH - width;
		}
		if (x < 0) {
			x = 0;
		}
	}

	public void resetPosition() {
		x = (Game.WIDTH/2) - 30;
		y = 10;
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, width, height);
	}
}
